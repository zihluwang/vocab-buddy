import { Nullable } from "@/types"
import WordClass from "@/models/biz/WordClass"

export default interface WordDescription {
  id: Nullable<string>
  phonetics: Nullable<string>
  wordClass: Nullable<WordClass>
  meaning: Nullable<string>
}