<template>
  <!--<input v-model="currentWord.word" />-->
  <n-grid cols="12" class="wrapper" y-gap="100">
    <n-gi class="progress-bar" offset="3" span="6">
      <n-progress
        type="line"
        :percentage="percentage"
        indicator-placement="inside"
        processing
        indicator-text-color="#212121"
      ></n-progress>
    </n-gi>

    <n-gi class="container" offset="3" span="6">
      <n-space vertical>
        <div class="sound">
          <n-button secondary round @click="playSound" class="btn btn-round btn-audio" type="info">
            <n-icon :component="TablerSpeakerPhone" size="60px"></n-icon>
          </n-button>
          <audio ref="wordAudio" hidden :src="audioUrlBuilder(currentWord.word || ``)" id="wordPronunciation" />
        </div>

        <div class="user-input">
          <n-form label-placement="left">
            <n-form-item path="userSpelling">
              <n-input v-model:value="userSpelling" placeholder="请在此处填写单词"></n-input>
            </n-form-item>
          </n-form>
        </div>

        <div>
          <n-list>
            <n-list-item v-for="item in currentWord.wordDescriptions" :key="item[`id`]">
              <span class="word-class secondary-text serif">{{ item["wordClass"]["code"] }}</span
              >.
              <span class="word-description primary-text serif">{{ item["meaning"].replaceAll(";", "；") }}</span>
            </n-list-item>
          </n-list>
        </div>
      </n-space>

      <n-space justify="end" class="action">
        <n-button @click="handleStop" type="warning" secondary class="btn btn-cancel">停止练习</n-button>
        <n-button @click="next" type="info" secondary class="btn btn-next">下一个</n-button>
      </n-space>
    </n-gi>
  </n-grid>
</template>

<script lang="ts" setup>
import { computed, ref } from "vue"
import { useDictationStore } from "@/store"
import { audioUrlBuilder } from "@/utils/AliyunUtil"
import TablerSpeakerPhone from "@/assets/icon/tabler/TablerSpeakerPhone.vue"
import { dialog, message } from "@/utils/NaiveUtil"
import moment from "moment"
import { saveDictationDetail, stopDictation } from "@/apis"
import router from "@/router"
import Decimal from "decimal.js"

const dictationStore = useDictationStore()

const dictationId = ref(dictationStore.exercise.dictationId)
const words = dictationStore.exercise.words
const currentWordIndex = computed(() => dictationStore.currentWordIndex)
const currentWord = computed(() => words[currentWordIndex.value])

const percentage = computed(() =>
  new Decimal(currentWordIndex.value).dividedBy(words.length).mul(100).toNumber()
)

const userSpelling = ref<string>("")

const wordAudio = ref<HTMLAudioElement | null>(null)

async function next() {
  if (currentWord.value && currentWord.value.id && currentWord.value.word) {
    const response = await saveDictationDetail({
      dictationId: dictationId.value,
      wordId: currentWord.value.id,
      word: currentWord.value.word,
      clientInputWord: userSpelling.value,
    })

    if (response.code != 200) {
      message.error("保存失败！")
      return
    }

    // 跳转至下一个单词
    if (!dictationStore.nextWord()) {
      message.error("已经是最后一个单词啦！", {
        closable: true,
        duration: moment.duration({ seconds: 3 }).asMilliseconds(),
      })
      // todo 结束听写练习
      await stopExercise()
    }
  } else {
    message.error("数据丢失，请结束本次听写后重新开始！")
  }
  userSpelling.value = ""
}

function handleStop() {
  dialog.warning({
    title: "确定要提前结束吗？",
    positiveText: "是",
    negativeText: "否",
    onPositiveClick: () => stopExercise()
  })
}

async function stopExercise() {
  const response = await stopDictation(dictationId.value)
  if (response.code != 200) {
    message.error("停止失败！")
  } else {
    await router.push({ name: "Overview" })
  }
}

function playSound() {
  if (wordAudio.value) {
    wordAudio.value.play()
  } else {
    message.error("音频无法播放")
  }
}
</script>

<style scoped>
.action {
  /* display: flex;
  justify-content: flex-end; */
}

.user-input {
  margin-top: 20px;
}

.word-description {
  font-weight: bold;
}

.word-class {
  font-family: "Times New Roman", serif, sans-serif;
  font-size: 18px;
  font-style: italic;
  font-weight: bold;
}

.btn-cancel {
  margin-right: 6px;
}

.btn-audio {
  width: 100px;
  height: 100px;
}

.sound {
  display: flex;
  justify-content: center;
}

.container {
  margin-top: 10px;
  margin-bottom: 10px;
}

.user-input {
  /* margin-top: 0 !important;
  margin-bottom: 0 !important; */
}

.container {
}
</style>
