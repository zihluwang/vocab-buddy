<script setup lang="ts">
/*
 * 1. 页面进来先开启全屏加载用于发请求创建听写训练
 * 2. 直至请求回来结束加载，开始渲染测试
 * 3. 测试过程中每次点击下一步都要将数据传回后端直至测试结束
 * 4. 测试结束的时候点击结束测试按钮回到测试前的页面
 *
 * 页面传递：
 * 1. tagId 听写任务的词库版本id
 * 2. maxWords 最大听写单词数
 * 均由创建时候的 dialog 产生，在页面跳转的时候使用query形式传递至新的测试界面
 */

import { onMounted, reactive, ref } from "vue"
import { useRoute, useRouter } from "vue-router"
import { getDictationExercise } from "@/apis"
import { message } from "@/utils/NaiveUtil"
import DictationExercise from "@/models/biz/DictationExercise"
import { useDefaultStore } from "@/store/DefaultStore"
import DictationExerciseContent from "@/components/DictationExerciseContent.vue"

const route = useRoute()
const router = useRouter()
const defaultStore = useDefaultStore()

// 只有当其转变为 true 的时候才会初始化
enum initType {
  FAIL,
  SUCCESS,
  LOADING,
}
const isInitSuccess = ref<initType>(initType.LOADING)

// 当前任务的展示形式
type programType = "default" | "choose" | "fill" | "dictation" | ""
let taskProgram: programType = ""

// 是否显示全屏加载遮罩
const fullScreenLoading = ref(false)

// 听写任务的详细信息
const taskInfo = reactive<DictationExercise>({
  dictationId: "",
  words: [],
})

async function createDictationTaskApi(answeringMode: number, generationMode: number, tagId: string, wordsCount: number) {
  try {
    let result = null
    if (generationMode === 1) {
      result = await getDictationExercise(answeringMode, generationMode, tagId, wordsCount)
    } else {
      result = await getDictationExercise(answeringMode, generationMode)
    }
    const { code, data } = result
    if (code === 200 && data) {
      taskInfo.dictationId = data?.id
      taskInfo.words = data?.wordLib
      isInitSuccess.value = initType.SUCCESS
    } else {
      message.error("初始化失败，请刷新重试")
      isInitSuccess.value = initType.FAIL
      return Promise.reject("创建听写任务失败")
    }
  } catch (e) {
    isInitSuccess.value = initType.FAIL
    if (e instanceof Error) {
      console.error(e.message)
    } else {
      console.error(e)
    }
  }
}

function initProgram(program: programType) {
  if (program === "default") {
    // 获取当前账户的数据
    const grade = defaultStore.client.grade
    switch (grade) {
      case 1:
      case 2:
        taskProgram = "choose"
        break
      case 3:
      case 4:
        taskProgram = "fill"
        break
      default:
        taskProgram = "dictation"
    }
  } else {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    taskProgram = program
  }
}

onMounted(async () => {
  fullScreenLoading.value = true
  // 解析 query
  const { wordsCount, tagId, answeringMode, generationMode } = route.query
  if (wordsCount?.length && tagId?.length && generationMode && answeringMode) {
    try {
      let _answeringMode = -1
      if (answeringMode === "choose") {
        _answeringMode = 1
      } else if (answeringMode === "fill") {
        _answeringMode = 2
      } else {
        _answeringMode = 3
      }

      // 发送请求
      await createDictationTaskApi(_answeringMode,parseInt(generationMode as string),tagId as string,parseInt(wordsCount as string))
      // 初始化
      if (isInitSuccess.value === initType.SUCCESS) {
        initProgram(answeringMode as programType)
      }
    } catch {
      // 如果初始化失败，三秒后返回前一个页面
      if (isInitSuccess.value === initType.FAIL) {
        setTimeout(() => {
          router.back()
        }, 3000)
      }
    } finally {
      // 关闭loading
      fullScreenLoading.value = false
    }
  }
})
</script>

<template>
  <div class="new-dictation-exercise">
    <n-spin :show="fullScreenLoading">
      <template #description>正在初始化系统中...</template>
      <div class="content">
        <!--初始化错误的显示-->
        <n-result
          v-if="isInitSuccess === initType.FAIL"
          status="error"
          title="任务初始化错误"
          description="页面将在3s钟后自动返回"
        >
        </n-result>
        <dictation-exercise-content
          v-else-if="isInitSuccess === initType.SUCCESS"
          :task-program="taskProgram"
          :words="taskInfo.words"
          :dictation-id="taskInfo.dictationId"
        ></dictation-exercise-content>
      </div>
    </n-spin>
  </div>
</template>

<style scoped lang="less">
.new-dictation-exercise {
  width: 100%;
  height: 100%;

  .n-spin-container {
    width: 100%;
    height: 100%;

    /deep/ .n-spin-content {
      width: 100%;
      height: 100%;

      .content {
        width: 100%;
        height: 100%;
        margin: 0 auto;

        .n-result {
          width: 100%;
          height: 100%;
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
        }
      }
    }
  }
}
</style>
