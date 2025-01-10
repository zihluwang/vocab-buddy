import { defineStore } from "pinia"
import { reactive, ref } from "vue";
import DictationExercise from "@/models/biz/DictationExercise"
import Tag from "@/models/biz/Tag"

export const useDictationStore = defineStore(
  "dictation",
  () => {
    const exercise = reactive<DictationExercise>({
      dictationId: "",
      words: [],
    })

    const currentWordIndex = ref(0)

    const tags = reactive<Tag[]>([])

    /**
     * 更新听写测试题
     * @param {DictationExercise} dictationExercise
     */
    function updateDictationExercise(dictationExercise: DictationExercise) {
      exercise.dictationId = dictationExercise.dictationId
      exercise.words = dictationExercise.words
    }

    /**
     * 存放所有Tag
     */
    function putAllTags(_tags: Tag[]) {
      _tags.forEach((item) => tags.push(item))
    }

    function putTag(tag: Tag) {
      tags.push(tag)
    }

    function nextWord(): boolean {
      if (currentWordIndex.value < exercise.words.length - 1) {
        currentWordIndex.value++
        return true
      }
      return false
    }

    function finishExercise() {
      currentWordIndex.value = 0
    }

    return {
      tags,
      exercise,
      currentWordIndex,
      updateDictationExercise,
      putAllTags,
      putTag,
      nextWord,
      finishExercise
    }
  },
  {
    persist: true,
  }
)