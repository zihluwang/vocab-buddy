<template>
  <n-card class="container">
    <template #header>开始新的听写</template>
    <n-spin :show="showSpin">
      <n-form ref="generateDictationForm" :model="generateDictationParams" :rules="formRules">
        <n-form-item label="词库" path="tagId">
          <n-select v-model:value="generateDictationParams.tagId" :options="tags" />
        </n-form-item>
        <n-form-item label="单词数" path="maxWords">
          <n-input-number v-model:value="generateDictationParams.maxWords" clearable min="0" :max="wordsLimit" style="width: 100%" />
        </n-form-item>
      </n-form>
    </n-spin>
    <template #footer>
      <n-grid :x-gap="12" :cols="2">
        <n-gi>
          <n-button type="primary" class="btn" @click="startDictation" :disabled="showSpin">
            开始听写
            <template #icon>
              <n-icon>
                <material-start-sharp />
              </n-icon>
            </template>
          </n-button>
        </n-gi>
        <n-gi>
          <n-button type="warning" class="btn" @click="cancel">
            取消
            <template #icon>
              <n-icon>
                <material-cancel-filled />
              </n-icon>
            </template>
          </n-button>
        </n-gi>
      </n-grid>
    </template>
  </n-card>
</template>

<script lang="ts" setup>
import { computed, onMounted, reactive, ref } from "vue"
import { getAllTags, getDictationExercise } from "@/apis"
import { useDictationStore } from "@/store"
import MaterialStartSharp from "@/assets/icon/material/MaterialStartSharp.vue"
import MaterialCancelFilled from "@/assets/icon/material/MaterialCancelFilled.vue"
import { useRouter } from "vue-router"
import { FormInst, FormRules, FormItemRule } from "naive-ui"
import { message } from "@/utils/NaiveUtil"
import moment from "moment"

const router = useRouter()
const dictationStore = useDictationStore()

const showSpin = ref(false)

const tagsInStore = dictationStore.tags

const wordsLimit = computed(() => {
  return (
    tagsInStore.find((item) => item.id == generateDictationParams.tagId) || {
      wordCount: 0,
    }
  ).wordCount
})

const generateDictationForm = ref<FormInst | null>(null)
const formRules: FormRules = {
  tagId: [{ required: true, message: "您还没有选择词库！" }],
  maxWords: [
    {
      validator: (rule: FormItemRule, value: number) => {
        const selectedTagId = generateDictationParams.tagId
        const selectedTag = tagsInStore.find((item) => item.id == selectedTagId)

        return new Promise((resolve, reject) => {
          if (selectedTag) {
            if (value > 0 && value < selectedTag.wordCount) {
              resolve()
            } else {
              reject("听写数量不得小于或等于0或大于词库最大单词数量！")
            }
          } else {
            reject("听写单词数量错误")
          }
        })
      },
    },
  ],
}

const tags = computed(() =>
  dictationStore.tags.map((item) => {
    return { label: `${item.name} (${item.wordCount})`, value: item.id }
  })
)

const generateDictationParams = reactive({
  tagId: "",
  // 设置初始单词数量为20
  maxWords: 20,
})

async function startDictation(e: MouseEvent) {
  e.preventDefault()
  showSpin.value = true
  await generateDictationForm.value?.validate(async (errors) => {
    if (!errors) {
      const response = await getDictationExercise(generateDictationParams.tagId, generateDictationParams.maxWords)
      if (response.code == 200 && response.data) {
        dictationStore.updateDictationExercise(response.data)
        await router.push({ name: "DictationExercise" })
      } else if (response.code == 400) {
        message.error(response.message, {
          duration: moment.duration({ seconds: 3 }).asMilliseconds(),
          closable: true,
        })
      }
    }
    showSpin.value = false
  })
}

function cancel() {
  // do something
}

onMounted(async () => {
  // 获取单词的标签
  if (dictationStore.tags.length == 0) {
    const response = await getAllTags()
    // console.log(response);
    if (response.code == 200 && response.data) {
      dictationStore.putAllTags(response.data)
    }
  }
})
</script>

<style scoped>
.container {
  width: 440px;
  /* border: 1px solid black; */
  padding: 20px;

  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  border: 1px blanchedalmond solid;
  border-radius: 20px;
}

.btn {
  width: 100%;
}
</style>