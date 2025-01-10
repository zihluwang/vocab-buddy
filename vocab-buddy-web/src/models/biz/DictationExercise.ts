import { Nullable } from "@/types"
import Word from "@/models/biz/Word"

export default interface DictationExercise {
  dictationId: string
  words: Word[]
}