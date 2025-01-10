import { defineStore } from "pinia"
import { reactive } from "vue"
import { FlagType, StartDictationInterface, QuestionBankItemInterface } from "@/models/biz/StartDictation"

export const useStartDictationStore = defineStore(
  "dictation",
  () => {
    const startDictation = reactive<StartDictationInterface>({
      // 是否是错题模式 1: 不是 2: 是
      isWrongMode: 1,

      // 选择的题库
      chooseQuestionBank: "",

      // 题库列表
      questionBankList: [],

      // 输入的单词数量
      totalQuestionsNumber: 0,

      // 选择的练习方案
      chooseTrainingProgram: "default",
    })

    /**
     * 获取到目前最新的数据
     */
    function getDictationData() {
      return startDictation
    }

    /**
     * 获取某一个元素的值
     * @param flag 元素名称
     */
    function getOneData(flag: FlagType) {
      return startDictation[flag]
    }

    /**
     * 一次性更新全部信息
     * @param data 新的数据
     */
    function updateAllData(data: StartDictationInterface) {
      startDictation.isWrongMode = data.isWrongMode
      startDictation.chooseQuestionBank = data.chooseQuestionBank
      startDictation.chooseTrainingProgram = data.chooseTrainingProgram
      startDictation.totalQuestionsNumber = data.totalQuestionsNumber
      startDictation.questionBankList = data.questionBankList
    }

    function updateIsWrongMode(data: 1 | 2) {
      startDictation.isWrongMode = data
    }
    function updateChooseQuestionBank(data: string) {
      startDictation.chooseQuestionBank = data
    }
    function updateChooseTrainingProgram(data: string) {
      startDictation.chooseTrainingProgram = data
    }
    function updateTotalQuestionsNumber(data: number) {
      startDictation.totalQuestionsNumber = data
    }
    function updateQuestionBankList(data: QuestionBankItemInterface[]) {
      startDictation.questionBankList = data
    }

    return {
      getDictationData,
      getOneData,
      updateAllData,
      updateIsWrongMode,
      updateChooseQuestionBank,
      updateChooseTrainingProgram,
      updateTotalQuestionsNumber,
      updateQuestionBankList,
    }
  },
  {
    persist: true,
  }
)
