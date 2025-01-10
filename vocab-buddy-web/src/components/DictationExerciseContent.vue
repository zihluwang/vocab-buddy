<script setup lang="ts">
/*
 * 组件所需数据：
 * 1. taskProgram， 来自页面，通过 props 传入,表示当前训练使用的模式
 * 2. words，来自页面，通过 props 传入，本次训练的数据集
 * 3. dictationId, 来自页面，通过 props 传入，用于结束测试
 */

// todo: 选择题的静态界面开发完成，将其动起来所需的条件还不具备，因此检查按钮的功能暂不开放
// todo: 挖空填写的静态界面开发完成，将启动起来所需的条件还不具备，因此检查按钮暂不开放
// todo: 完整填空的静态界面开发完成，将其动起来所需的条件还不具备，因此检查按钮暂不开放
// todo: 目前进入页面自动播放音频和点击播放音频的功能以开发完毕

import { defineProps, onMounted, reactive, ref, watch, computed } from "vue"
import ChooseRadioItem from "@/components/ChooseRadioItem.vue"
import { useDialog } from "naive-ui"
import { stopDictation, saveDictationDetail } from "@/apis"
import { message } from "@/utils/NaiveUtil"
import { useRouter } from "vue-router"
import { audioUrlBuilder } from "@/utils/AliyunUtil"
import Word from "@/models/biz/Word"
import saveDictationParams from "@/models/request/SaveDictationParams"

const dialog = useDialog()
const router = useRouter()

// 传递进来的 props
const props = defineProps({
  taskProgram: String,
  words: Array,
  dictationId: String,
})

watch(
  () => props.taskProgram,
  () => {
    // 选择题需要初始化第一题的选项
    if (props.taskProgram === "choose") {
      if (currentWord?.choices) {
        currentWord.choices.forEach((item, index) => {
          chooseOptions.value.push({
            id: `${item}-${index}`,
            label: item,
            value: item,
          })
        })
      }
    } else if (props.taskProgram === "fill") {
      // 初始化填空题
      if (currentWord) {
        randomTruncWord()
      }
    }
  }
)

// 当前正在使用的word的index(index从0开始)
let currentWordIndex: number
let currentWord: Word | null
// 初始化 currentWordIndex 和 currentWord
currentWordIndex = 0
currentWord = (props.words as Word[])[currentWordIndex]

// audio 标签
const audioRef = ref<HTMLAudioElement | null>(null)
watch(audioRef, (newValue) => {
  newValue?.addEventListener("loadeddata", () => {
    playSound()
  })
})
function playSound() {
  if (audioRef.value) {
    audioRef.value.play()
  }
}

interface OptionItem {
  id: string
  label: string
  value: string
}
// 虚拟的选项列表
const chooseOptions = ref<OptionItem[]>([])

// 进度条相关的数据
const progressInfo = reactive({
  // 每次进度条增长的数量
  step: 0,
  percentage: 0,
  // 进度条颜色
  color: "#18a058",
  height: 25,
  // 轨道的颜色
  railColor: "#e5e5e5",
  // 是否显示指标
  showIndicator: false,
})

// 当前被激活按钮的key（其实就是按钮显示的label）
const activeChooseKey = ref("")

// 选择题选项被点击时候的回调函数
function clickChooseItem(item: OptionItem) {
  // 为被点击的选项更新样式
  activeChooseKey.value = item.id
}

interface FillInputInterface {
  prefix: string
  suffix: string
  currentFillInput: string
}
// 挖空默写的详细数据
const fillInfo = reactive<FillInputInterface>({
  prefix: "",
  suffix: "",
  currentFillInput: "",
})

// 完整默写的实时记录变量(用于展示)
const currentDictationInput = ref<string>("")

// 限制输入框只能输入小写和大写字符
function checkInput(value: string) {
  const regex = /^[a-zA-Z]+$/
  return value === "" || regex.test(value)
}

// 检查按钮的文本
const checkBtnLabel = ref("")
checkBtnLabel.value = currentWordIndex === (props.words as Word[]).length - 1 ? "结束训练" : "检查"

// 检查按钮的回调函数
function check() {
  switch (props.taskProgram) {
    case "choose":
      chooseCheck()
      break
    case "fill":
      fillCheck()
      break
    case "dictation":
      dictationCheck()
      break
  }
}

// 选择题的检查逻辑
function chooseCheck() {
  const choose = chooseOptions.value.find((item) => item.id === activeChooseKey.value)
  const params: saveDictationParams = {
    wordId: currentWord?.id as string,
    userSpelling: choose?.value as string,
  }
  // 发请求保存数据
  saveDictationDetail(props.dictationId as string, params)
    .then((res) => {
      const { code, message: resMessage } = res
      if (code === 200) {
        message.success(resMessage)

        // 更新进度条
        progressInfo.percentage += progressInfo.step
        progressInfo.percentage = parseFloat(progressInfo.percentage.toFixed(2))
        if (progressInfo.percentage > 100) progressInfo.percentage = 100

        if (currentWordIndex < (props.words as Word[]).length - 1) {
          // 下一个单词
          currentWordIndex++
          currentWord = (props.words as Word[])[currentWordIndex]
          chooseOptions.value = []
          if (currentWord?.choices) {
            currentWord.choices.forEach((item, index) => {
              chooseOptions.value.push({
                id: `${item}-${index}`,
                label: item,
                value: item,
              })
            })
          }

          // 当前是最后一个单词
          if (currentWordIndex === (props.words as Word[]).length - 1) {
            checkBtnLabel.value = "结束训练"
          }
        } else {
          progressInfo.percentage = 100
          // 结束训练
          return stopDictation(props.dictationId as string)
        }
      }
    })
    .then((res) => {
      if (res) {
        const { code } = res
        if (code === 200) {
          message.success("训练已完成")
          router.back()
        }
      }
    })
    .catch((e) => {
      console.error(e)
    })
}

// 挖空的检查逻辑
function fillCheck() {
  const resultWord = `${fillInfo.prefix}${fillInfo.currentFillInput}${fillInfo.suffix}`
  const params: saveDictationParams = {
    wordId: currentWord?.id as string,
    userSpelling: resultWord,
  }
  // 发请求保存数据
  saveDictationDetail(props.dictationId as string, params)
    .then((res) => {
      const { code, message: resMessage } = res
      if (code === 200) {
        message.success(resMessage)

        // 更新进度条
        progressInfo.percentage += progressInfo.step
        progressInfo.percentage = parseFloat(progressInfo.percentage.toFixed(2))
        if (progressInfo.percentage > 100) progressInfo.percentage = 100

        if (currentWordIndex < (props.words as Word[]).length - 1) {
          // 下一个单词
          currentWordIndex++
          currentWord = (props.words as Word[])[currentWordIndex]
          randomTruncWord()

          // 当前是最后一个单词
          if (currentWordIndex === (props.words as Word[]).length - 1) {
            checkBtnLabel.value = "结束训练"
          }
        } else {
          progressInfo.percentage = 100
          // 结束训练
          return stopDictation(props.dictationId as string)
        }
      }
    })
    .then((res) => {
      if (res) {
        const { code } = res
        if (code === 200) {
          message.success("训练已完成")
          router.back()
        }
      }
    })
    .catch((e) => {
      console.error(e)
    })
}

// 完整默写的检查逻辑
function dictationCheck() {
  const params: saveDictationParams = {
    wordId: currentWord?.id as string,
    userSpelling: currentDictationInput.value,
  }
  // 发请求保存数据
  saveDictationDetail(props.dictationId as string, params)
    .then((res) => {
      const { code, message: resMessage } = res
      if (code === 200) {
        message.success(resMessage)

        // 更新进度条
        progressInfo.percentage += progressInfo.step
        progressInfo.percentage = parseFloat(progressInfo.percentage.toFixed(2))
        if (progressInfo.percentage > 100) progressInfo.percentage = 100

        if (currentWordIndex < (props.words as Word[]).length - 1) {
          // 下一个单词
          currentWordIndex++
          currentWord = (props.words as Word[])[currentWordIndex]
          currentDictationInput.value = ""

          // 当前是最后一个单词
          if (currentWordIndex === (props.words as Word[]).length - 1) {
            checkBtnLabel.value = "结束训练"
          }
        } else {
          progressInfo.percentage = 100
          // 结束训练
          return stopDictation(props.dictationId as string)
        }
      }
    })
    .then((res) => {
      if (res) {
        const { code } = res
        if (code === 200) {
          message.success("训练已完成")
          router.back()
        }
      }
    })
    .catch((e) => {
      console.error(e)
    })
}

// 结束训练按钮的回调函数
function clickFinishBtn() {
  // 展示dialog
  dialog.warning({
    title: "注意",
    content: "确定结束任务了嘛？",
    positiveText: "确定",
    negativeText: "不确定",
    onPositiveClick: async () => {
      try {
        if (props.dictationId) {
          // 请求停止
          const { code } = await stopDictation(props.dictationId)
          if (code === 200 || code === 1) {
            message.success("停止成功")
            router.back()
          } else {
            message.error("结束任务失败，请稍后重试。")
            return Promise.reject("无法结束任务， code !== 200")
          }
        } else {
          message.error("系统出现错误，请刷新重试")
        }
      } catch (e) {
        if (e instanceof Error) {
          console.error(e.message)
        } else {
          console.error(e)
        }
      }
    },
  })
}

// 计算属性-计算检查按钮是否应该启用
const isCheckBtnDisable = computed(() => {
  let result = false

  switch (props.taskProgram) {
    case "choose":
      result = activeChooseKey.value === ""
      break
    case "fill":
      result = fillInfo.currentFillInput === ""
      break
    case "dictation":
      result = currentDictationInput.value === ""
      break
  }

  return result
})

// 函数-随机截断单词
// 长度 <= 5, 例如：cat、water、have 仅在其一端截断，随机截断前或者后，随机截断1-3个字符
// 长度 > 5, 例如：winter， summer 同时截断前后，填写中间区域内容，填写内容不少于3个字符
function randomTruncWord() {
  const word = currentWord?.word
  const length = word?.length

  // 中间3字符
  const subStrLengthStr3 = (length as number) - 3
  const prefixLengthStr3 = Math.floor(Math.random() * (subStrLengthStr3 - 1) + 1)
  const suffixLengthStr3 = subStrLengthStr3 - prefixLengthStr3
  // 中间4字符
  const subStrLengthStr4 = (length as number) - 4
  const prefixLengthStr4 = Math.floor(Math.random() * (subStrLengthStr4 - 1) + 1)
  const suffixLengthStr4 = subStrLengthStr4 - prefixLengthStr4
  // 中间5字符
  const subStrLengthStr5 = (length as number) - 5
  const prefixLengthStr5 = Math.floor(Math.random() * (subStrLengthStr5 - 1) + 1)
  const suffixLengthStr5 = subStrLengthStr5 - prefixLengthStr5

  // 清空原有数据
  fillInfo.prefix = ""
  fillInfo.currentFillInput = ""
  fillInfo.suffix = ""

  if (length) {
    switch (length) {
      case 1:
      case 2:
        // 转换成纯默写就好(啥都不做)
        break
      case 3:
        // 随机截断前或后1个字符
        if (parseInt(Math.random().toFixed(0))) {
          // 前置截取

          fillInfo.prefix = word?.substring(0, 1) as string
        } else {
          // 后置截取
          fillInfo.suffix = word?.slice(-1) as string
        }
        break
      case 4:
        // 随机截断前或者后2个字符
        if (parseInt(Math.random().toFixed(0))) {
          // 前置截取
          fillInfo.prefix = word?.substring(0, 2) as string
        } else {
          // 后置截取
          fillInfo.suffix = word?.slice(-2) as string
        }
        break
      case 5:
        // 随机截断前或者后3个字符
        if (parseInt(Math.random().toFixed(0))) {
          // 前置截取
          fillInfo.prefix = word?.substring(0, 3) as string
        } else {
          // 后置截取
          fillInfo.suffix = word?.slice(-3) as string
        }
        break
      case 6:
      case 7:
        // 同时截取前后，中间输入3个字符
        fillInfo.prefix = word.substring(0, prefixLengthStr3)
        fillInfo.suffix = word.slice(-suffixLengthStr3)
        break
      case 8:
      case 9:
        // 同时截取前后，中间输入4个字符
        fillInfo.prefix = word.substring(0, prefixLengthStr4)
        fillInfo.suffix = word.slice(-suffixLengthStr4)
        break
      default:
        // 同时截取前后，中间输入5个字符
        fillInfo.prefix = word.substring(0, prefixLengthStr5)
        fillInfo.suffix = word.slice(-suffixLengthStr5)
    }
  }
}

onMounted(() => {
  // 初始化进度条
  if (props.words) {
    if (props.words.length > 0) {
      progressInfo.step = 100 / props.words.length
    } else {
      progressInfo.step = 0
    }
  }
})
</script>

<template>
  <div class="dictation-exercise-content">
    <div class="wrapper">
      <!--进度条-->
      <div class="top progress">
        <n-progress
          type="line"
          processing
          :height="progressInfo.height"
          :color="progressInfo.color"
          :percentage="progressInfo.percentage"
          :indicator-placement="'inside'"
          :rail-color="progressInfo.railColor"
          :show-indicator="progressInfo.showIndicator"
        />
      </div>

      <!--选择题-->
      <template v-if="taskProgram === 'choose'">
        <div class="choose-wrapper">
          <h1 class="title">你听到了什么？</h1>
          <div ref="playBtnRef" class="play-btn" @click="playSound()">
            <img src="@/assets/svg/carbon/Play.svg" alt="" />
          </div>
          <audio ref="audioRef" autoplay :src="audioUrlBuilder(currentWord?.word || ``)"></audio>
          <div class="choose-group">
            <choose-radio-item
              v-for="(item, index) in chooseOptions"
              :class="{ 'active-btn': activeChooseKey === item.id }"
              :key="item.label"
              :item-index="index + 1"
              :label="item.label"
              @click="clickChooseItem(item)"
            ></choose-radio-item>
          </div>
        </div>
      </template>
      <!--挖空默写-->
      <template v-if="taskProgram === 'fill'">
        <div class="fill-wrapper">
          <h1 class="title">把空缺的地方补充一下吧!</h1>
          <div ref="playBtnRef" class="play-btn" @click="playSound">
            <img src="@/assets/svg/carbon/Play.svg" alt="" />
          </div>
          <audio ref="audioRef" autoplay :src="audioUrlBuilder(currentWord?.word || ``)"></audio>
          <n-input-group class="fill-input-group">
            <n-input-group-label v-show="fillInfo.prefix.length > 0" class="prefix">{{
              fillInfo.prefix
            }}</n-input-group-label>
            <n-input
              class="fill-input"
              placeholder="输入空缺部分的字母"
              :style="{ width: '33%' }"
              :allow-input="checkInput"
              v-model:value="fillInfo.currentFillInput"
            />
            <n-input-group-label v-show="fillInfo.suffix.length > 0" class="suffix">{{
              fillInfo.suffix
            }}</n-input-group-label>
          </n-input-group>
        </div>
      </template>
      <!--完整默写-->
      <template v-if="taskProgram === 'dictation'">
        <div class="dictation-wrapper">
          <h1 class="title">把听到的单词完整默写出来哦!</h1>
          <div ref="playBtnRef" class="play-btn" @click="playSound">
            <img src="@/assets/svg/carbon/Play.svg" alt="" />
          </div>
          <audio ref="audioRef" autoplay :src="audioUrlBuilder(currentWord?.word || ``)"></audio>
          <n-input
            v-model:value="currentDictationInput"
            class="dictation-input"
            type="text"
            placeholder="输入听到的单词"
          />
        </div>
      </template>

      <!--下一题/结束训练按钮-->
      <div class="bottom next-button">
        <n-button
          size="large"
          class="cancel"
          type="tertiary"
          @click="clickFinishBtn"
          :disabled="checkBtnLabel === '结束训练'"
          >提前结束训练</n-button
        >
        <n-button
          size="large"
          class="next"
          type="primary"
          :disabled="isCheckBtnDisable && checkBtnLabel === '检查'"
          @click="check"
          >{{ checkBtnLabel }}</n-button
        >
      </div>
    </div>
  </div>
</template>

<style scoped lang="less">
.dictation-exercise-content {
  position: relative;
  width: 100%;
  height: 100%;

  // 底部灰色分割线
  &::after {
    content: "";
    display: block;
    width: 100%;
    height: 0;
    border-top: 2px solid #dfdfdf;
    position: absolute;
    bottom: 150px;
  }

  .wrapper {
    position: relative;
    width: 100%;
    height: 100%;
    min-width: 500px;
    max-width: 1200px;
    margin: 0 auto;

    // 顶部的进度条
    .top {
      padding: 70px 0 100px;
    }

    // 底部的按钮组
    .bottom {
      position: absolute;
      bottom: 0;
      width: 100%;
      height: 100px;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
    }

    // 中间作答区域
    .choose-wrapper,
    .fill-wrapper,
    .dictation-wrapper {
      width: 60%;
      margin: 0 auto;

      // 提示语
      h1 {
        padding-bottom: 50px;
      }

      // 音乐播放按钮
      .play-btn {
        width: 100px;
        height: 100px;
        background-color: #3993f9;
        box-shadow: 0 3px 5px 1px #2e77c9;
        border-radius: 16px;
        margin: 0 auto 40px;
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        cursor: pointer;
        user-select: none;
        transition: all 0.1s;

        &:hover,
        &:active {
          background-color: #50a1fa;
        }

        &:active {
          transform: translateY(3px);
          box-shadow: unset;
        }

        img {
          width: 50%;
          height: 50%;
          font-size: 20px;
          color: white;
          filter: invert(1);
          user-select: none;
        }
      }

      // 选择题的选择项组
      .choose-group {
        width: 80%;
        max-width: 600px;
        margin: 0 auto;
        display: flex;
        flex-wrap: wrap;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
      }

      .fill-input-group {
        margin-left: 10%;
        width: 80%;
        height: 30px;
        max-width: 600px;
        justify-content: center;
        align-items: center;

        .prefix,
        .suffix {
          width: 25%;
        }
        .prefix {
          text-align: right;
        }
        .suffix {
          text-align: left;
        }
        .fill-input {
          flex: 1;
          text-align: center;
        }
      }

      // 完整填写的输入框
      .dictation-input {
        width: 60%;
        max-width: 600px;
        margin-left: 20%;
        text-align: center;
      }
    }
  }
}
</style>
