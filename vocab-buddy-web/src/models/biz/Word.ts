import { Nullable } from "@/types"
import Tag from "@/models/biz/Tag"
import WordDescription from "@/models/biz/WordDescription"

export default interface Word {
  id: Nullable<string>
  word: Nullable<string>
  tags: Nullable<Tag>
  wordDescriptions: Nullable<WordDescription[]>
  choices?: Nullable<Array<string>>
}