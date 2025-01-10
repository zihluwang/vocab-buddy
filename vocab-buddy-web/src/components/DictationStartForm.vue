<script setup lang="ts">
import { onMounted, computed, reactive, ref } from "vue"
import type { SelectOption } from "naive-ui"
import { NForm, NFormItem, NInputNumber, NSelect } from "naive-ui"
import { useStartDictationStore } from "@/store"
import { getAllTags } from "@/apis"
import { message } from "@/utils/NaiveUtil"
import { QuestionBankItemInterface } from "@/models/biz/StartDictation"

const startDictationStore = useStartDictationStore()

// 选择模式加载状态
const isWrongLoading = ref(false)
// 选择模式是否可用
const isWrongDisabled = ref(false)
// 词库列表下拉框的加载条
const questionBankLoading = ref(false)
// 词库下拉框是否可以可用
const isQuestionBankDisable = ref(false)
// 限制最大值
const wordCount = ref(0)
// 数量输入框是否可用
const isTotalSelectDisable = ref(false)
// 方案下拉框加载
const isProgramSelectloading = ref(false)
// 方案下拉框是否可用
const isProgramSelectDisable = ref(false)

const generateDictationFormModel = reactive({
  // 是否是错题模式
  isWrongMode: 1,
  // 题库选择
  chooseQuestionBank: "",
  // 本次训练的单词总个数
  totalQuestionsNumber: 1,
  // 本次选择所使用的训练方案
  chooseTrainingProgram: "",
})

const questionBankOptions = ref<QuestionBankItemInterface[]>([])
const trainingProgramOptions: SelectOption[] = [
  { label: "选择题", value: "choose" },
  { label: "填空题", value: "fill" },
  { label: "默写题", value: "dictation" },
]
const isWrongModeOptions: SelectOption[] = [
  { label: "默认模式", value: 1 },
  { label: "错题模式", value: 2 },
]

const labelName = computed(() => (wordCount.value ? `单词数量(最多${wordCount.value})` : "单词数量"))

// 获取词库列表
async function getQuestionBankListApi() {
  try {
    const { code, data } = await getAllTags()
    if (code === 200) {
      if (data) {
        const filterArr = data.filter((item) => {
          return item.name !== "错题专用"
        })
        questionBankOptions.value = filterArr.map((item) => {
          return {
            id: item.id,
            label: item.name,
            value: item.code,
          }
        })
      }
    } else {
      message.error("初始化失败，请刷新重试！")
    }
  } catch (e) {
    if (e instanceof Error) {
      console.error(e.message)
    } else {
      console.error(e)
    }
  }
}

function init() {
  const chooseQuestionBank = questionBankOptions.value[0].value as string
  const chooseTrainingProgram = trainingProgramOptions[0].value as string
  const isWrongMode = 1

  // 单词数初始化(默认1)
  // wordCount.value = questionBankOptions.value[0].wordCount as number
  const totalQuestionsNumber = 1

  // 初始化本地数据
  generateDictationFormModel.isWrongMode = isWrongMode
  generateDictationFormModel.chooseQuestionBank = chooseQuestionBank
  generateDictationFormModel.totalQuestionsNumber = totalQuestionsNumber
  generateDictationFormModel.chooseTrainingProgram = chooseTrainingProgram

  // 初始化pinia数据
  startDictationStore.updateAllData({
    isWrongMode: isWrongMode,
    chooseQuestionBank,
    chooseTrainingProgram,
    totalQuestionsNumber,
    questionBankList: questionBankOptions.value,
  })
}

// 表单进入loading状态
function loadingForm() {
  isWrongLoading.value = true
  isWrongDisabled.value = true
  questionBankLoading.value = true
  isQuestionBankDisable.value = true
  isTotalSelectDisable.value = true
  isProgramSelectDisable.value = true
  isProgramSelectloading.value = true
}

// 表单退出loading状态
function clearLoadingForm() {
  isWrongLoading.value = false
  isWrongDisabled.value = false
  questionBankLoading.value = false
  isQuestionBankDisable.value = false
  isTotalSelectDisable.value = false
  isProgramSelectDisable.value = false
  isProgramSelectloading.value = false
}

/**
 * 更新词库选项
 * @param value 选中的value
 * @param option 选中项的本地
 */
function updateQuestionChoose(value: string) {
  // 更新本地
  generateDictationFormModel.chooseQuestionBank = value
  // 更新 store
  startDictationStore.updateChooseQuestionBank(value)
}

/**
 * 更新单词总数
 * @param value
 */
function updateTotalQuestionNumber(value: number) {
  // 更新本地
  generateDictationFormModel.totalQuestionsNumber = value
  // 更新 store
  startDictationStore.updateTotalQuestionsNumber(value)
}

/**
 * 更新方案
 * @param value 选中项的value
 * @param option 选中项本体
 */
function updateTrainProgram(value: string) {
  // 更新本地
  generateDictationFormModel.chooseTrainingProgram = value
  // 更新 store
  startDictationStore.updateChooseTrainingProgram(value)
}

/**
 * 更新模式
 * @param value 选中项的value
 * @param option 选中项本体
 */
function updateIsWrongMode(value: 1 | 2) {
  // 更新本地
  generateDictationFormModel.isWrongMode = value
  // 更新 store
  startDictationStore.updateIsWrongMode(value)
}

onMounted(async () => {
  loadingForm()
  // 获取词库列表
  await getQuestionBankListApi()
  // 初始化form表单
  init()
  clearLoadingForm()
})
</script>

<template>
  <n-form ref="formRef" :model="generateDictationFormModel" size="large" class="dictation-form">
    <n-form-item label="模式选择" path="isWrongMode">
      <n-select
        filterable
        :options="isWrongModeOptions"
        v-model:value="generateDictationFormModel.isWrongMode"
        :loading="isWrongLoading"
        :disabled="isWrongDisabled"
        placeholder="选择模式"
        :on-update:value="updateIsWrongMode"
      />
    </n-form-item>
    <n-form-item v-if="generateDictationFormModel.isWrongMode === 1" label="词库" path="chooseQuestionBank">
      <n-select
        filterable
        :options="questionBankOptions"
        v-model:value="generateDictationFormModel.chooseQuestionBank"
        :loading="questionBankLoading"
        :disabled="isQuestionBankDisable"
        placeholder="选择词库"
        :on-update:value="updateQuestionChoose"
      />
    </n-form-item>
    <n-form-item v-if="generateDictationFormModel.isWrongMode === 1" :label="labelName" path="totalQuestionsNumber">
      <n-input-number
        v-model:value="generateDictationFormModel.totalQuestionsNumber"
        clearable
        :min="1"
        style="width: 100%"
        placeholder="输入数量"
        :disabled="isTotalSelectDisable"
        :on-update:value="updateTotalQuestionNumber"
      />
    </n-form-item>
    <n-form-item label="训练方案" path="chooseTrainingProgram">
      <n-select
        filterable
        :options="trainingProgramOptions"
        v-model:value="generateDictationFormModel.chooseTrainingProgram"
        placeholder="选择方案"
        :disabled="isProgramSelectDisable"
        :loading="isProgramSelectloading"
        :on-update:value="updateTrainProgram"
      />
    </n-form-item>
  </n-form>
</template>

<style scoped lang="less">
.dictation-form {
  margin: 20px 0;
}
</style>
