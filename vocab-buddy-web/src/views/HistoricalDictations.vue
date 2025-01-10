<template>
  <n-data-table
    remote
    :loading="tableLoading"
    :columns="columns"
    :data="dictations"
    :pagination="pagination"
    :bordered="false"
    :single-line="false"
    :row-props="rowProps"
    :row-class-name="getRowClassName"
    @update:page="handlePageChange"
    max-height="600"
  />
</template>

<script lang="ts" setup>
import { h, onMounted, reactive, ref } from "vue"
import { useRouter } from "vue-router"
import { DataTableColumns, NButton } from "naive-ui"
import Decimal from "decimal.js"
import moment from "moment"
import { AxiosError } from "axios"
import { getDictationHistories } from "@/apis"
import { message } from "@/utils/NaiveUtil"
import Dictation from "@/models/biz/Dictation"

function accuracyRate(dictation: Dictation) {
  return new Decimal(dictation.correctWordsCount).div(dictation.wordsCount)
}

function getRowClassName(row: Dictation) {
  if (accuracyRate(row).lt(0.6)) {
    return "failed"
  }
  return ""
}

function toDetail(dictationId: string) {
  router.push({ name: "DictationDetail", params: { dictationId } })
}

const rowProps = (row: Dictation) => {
  return {
    onClick: () => toDetail(row.id),
  }
}

const router = useRouter()

const dictations = reactive<Dictation[]>([])

const columns: DataTableColumns<Dictation> = [
  {
    title: "听写开始时间",
    key: "startTime",
    render(row) {
      return moment(row.startTime).format("yyyy[年]MM[月]DD[日] HH:mm:ss")
    },
  },
  {
    title: "听写结束时间",
    key: "endTime",
    render(row) {
      return row.endTime ? moment(row.endTime).format("yyyy[年]MM[月]DD[日] HH:mm:ss") : "该听写暂未结束"
    },
  },
  {
    title: "听写总数量",
    key: "wordsCount",
  },
  {
    title: "正确单词数量",
    key: "correctWordsCount",
  },
  {
    title: "正确率",
    key: "accuracyRate",
    className: "accuracyRate",
    render(row) {
      return `${new Decimal(row.correctWordsCount).div(row.wordsCount).mul(100).toFixed(2)}%`
    },
  },
  {
    title: "操作",
    key: "action",
    render(row) {
      return h(
        NButton,
        {
          strong: true,
          onClick: () => toDetail(row.id),
        },
        { default: () => "查看详情" }
      )
    },
  },
]

const tableLoading = ref(false)

const pagination = reactive({
  page: 0,
  pageSize: 0,
  pageCount: 0,
})

function handlePageChange(currentPage: number) {
  // 清空数组
  dictations.splice(0, dictations.length)
  getDataByPage(currentPage, pagination.pageSize)
}

async function getDataByPage(currentPage = 1, pageSize = 10) {
  try {
    tableLoading.value = true
    const response = await getDictationHistories(currentPage, pageSize)
    if (response) {
    if (response.code == 200 && response.data?.records) {
      pagination.page = response.data.current
      pagination.pageSize = response.data.size
      pagination.pageCount = response.data.pages
      response.data.records.forEach((item) => dictations.push(item))
    } else {
      message.info("您还没有参加过听写哦～", {
        duration: moment.duration({ seconds: 3 }).asMilliseconds(),
        closable: true,
      })
    }
  }
  } catch(error) {
    if (error instanceof AxiosError && error.response?.data.code == 401) {
      router.push({ name: "Login" })
    }
  } finally {
    tableLoading.value = false
  }
} 

onMounted(() => {
  // 初始化
  getDataByPage()
})
</script>

<style scoped>
:deep(.failed .accuracyRate) {
  color: #d03050 !important;
}
</style>
