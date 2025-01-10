type FlagType = "chooseQuestionBank" | "totalQuestionsNumber" | "chooseTrainingProgram" | "questionBankList" | "isWrongMode"

interface QuestionBankItemInterface {
  id: string
  label: string
  value: string
}

interface StartDictationInterface {
  isWrongMode: 1 | 2
  chooseQuestionBank: string
  totalQuestionsNumber: number
  chooseTrainingProgram: string
  questionBankList: QuestionBankItemInterface[]
}

export { FlagType, StartDictationInterface, QuestionBankItemInterface }
