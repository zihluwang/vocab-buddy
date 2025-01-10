<template>
  <n-breadcrumb class="breadcrumb">
    <n-breadcrumb-item @click="onBreadcrumbClick">
      <n-icon :component="AntdHistoryOutlined" />
      听写历史记录
    </n-breadcrumb-item>
    <n-breadcrumb-item :clickable="false">
      <n-icon :component="AntdDetailsOutlined" />
      {{ dictationId }}
    </n-breadcrumb-item>
  </n-breadcrumb>

  <n-grid :cols="2" :x-gap="8" :y-gap="8">
    <n-gi>
      <div>
        <n-descriptions label-placement="top" :columns="2">
          <template #header> 听写详情</template>
          <n-descriptions-item label="听写ID" label-style="font-weight: bold" :span="1">
            {{ dictation.id }}
          </n-descriptions-item>
          <n-descriptions-item label="单词类型" label-style="font-weight: bold" :span="1">
            {{ dictation.tag?.name || "" }}
          </n-descriptions-item>

          <n-descriptions-item label="正确单词数" label-style="font-weight: bold">
            <n-statistic :tabular-nums="true">
              <span class="correct-words-count">{{ dictation.correctWordsCount }}</span>
              <template #suffix>/ {{ dictation.wordsCount }}</template>
            </n-statistic>
          </n-descriptions-item>

          <n-descriptions-item label="错误单词数" label-style="font-weight: bold">
            <n-statistic :tabular-nums="true">
              <span class="incorrect-words-count">{{ dictation.incorrectWordsCount }}</span>
              <template #suffix>/ {{ dictation.wordsCount }}</template>
            </n-statistic>
          </n-descriptions-item>

          <n-descriptions-item label="听写开始时间" label-style="font-weight: bold" :span="2">
            {{ startTime }}
          </n-descriptions-item>

          <n-descriptions-item label="听写结束时间" label-style="font-weight: bold" :span="2">
            {{ endTime }}
          </n-descriptions-item>
        </n-descriptions>
      </div>
    </n-gi>
    <n-gi>
      <!--<n-scrollbar trigger="hover" style="max-height: calc(100vh - 160px)">-->
      <n-data-table
        :columns="columns"
        :data="dictationDetails"
        :row-class-name="getRowClassName"
        size="large"
        max-height="500"
      />
      <!--</n-scrollbar>-->
    </n-gi>
  </n-grid>
</template>

<script lang="ts" setup>
import { computed, h, onMounted, reactive, ref } from "vue"
import { useRoute } from "vue-router"
import AntdHistoryOutlined from "@/assets/icon/antd/AntdHistoryOutlined.vue"
import AntdDetailsOutlined from "@/assets/icon/material/MaterialDetailsOutlined.vue"
import router from "@/router"
import { getDictationInformation } from "@/apis"
import Dictation from "@/models/biz/Dictation"
import moment from "moment"
import { DataTableColumns } from "naive-ui"
import DictationDetail from "@/models/biz/DictationDetail"

const route = useRoute()

const dictationId = ref<string>("")
const dictation = reactive<Dictation>({
  id: "",
  clientId: "",
  tag: null,
  wordsCount: 0,
  correctWordsCount: 0,
  incorrectWordsCount: 0,
  startTime: "",
  endTime: "",
  dictationDetails: null,
})

onMounted(async () => {
  // do something
  if (typeof route.params.dictationId === "string") {
    dictationId.value = route.params.dictationId
    const response = await getDictationInformation(dictationId.value)
    // console.log(response)
    if (response) {
      // console.log(response)
      Object.assign(dictation, response.data)
    }
  }
})

function getRowClassName(row: DictationDetail) {
  if (row.word != row.userSpelling) {
    return "incorrect"
  }
  return ""
}

const columns: DataTableColumns<DictationDetail> = [
  {
    title: "单词",
    key: "word",
    render(row) {
      return h("code", { innerHTML: row.word, style: "font-family: menlo, consolas;" })
    },
  },
  {
    title: "用户的拼写",
    key: "userSpelling",
    className: "user-spelling",
    render(row) {
      return h("code", { innerHTML: row.userSpelling, style: "font-family: menlo, consolas;" })
    },
  },
]

function onBreadcrumbClick() {
  router.push({ name: "HistoricalDictations" })
}

const startTime = computed(() => moment(dictation.startTime).format("YYYY年MM月DD日 HH:mm:ss"))
const endTime = computed(() =>
  dictation.endTime ? moment(dictation.endTime).format("YYYY年MM月DD日 HH:mm:ss") : "该听写暂未结束"
)
const dictationDetails = computed<DictationDetail[]>(() => dictation.dictationDetails || [])
</script>

<style scoped>
:root {
  --incorrect-colour: #d03050;
  --correct-colour: #18a058;
}

.breadcrumb {
  padding: 0;
  margin-bottom: 20px;
}

.correct-words-count {
  color: var(--correct-colour);
}

.incorrect-words-count {
  color: var(--incorrect-colour);
}

:deep(.incorrect .user-spelling) {
  color: #d03050 !important;
}
</style>