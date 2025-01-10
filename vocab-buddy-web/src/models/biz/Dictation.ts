import Tag from "@/models/biz/Tag"
import DictationDetail from "@/models/biz/DictationDetail"
import { Nullable } from "@/types"

export default interface Dictation {
  id: string
  clientId: string
  tag: Nullable<Tag>
  wordsCount: number
  correctWordsCount: number
  incorrectWordsCount: number
  startTime: string
  endTime: string
  dictationDetails: Nullable<DictationDetail[]>
}