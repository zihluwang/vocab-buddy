export default interface DictationDetail {
  id: string
  dictationId: string
  wordId: string
  word: string
  userSpelling: string
  correct: number
}