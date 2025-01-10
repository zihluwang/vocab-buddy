import { Nullable } from "@/types"

export default interface WordClass {
  id: Nullable<number>
  name: Nullable<string>
  chineseName: Nullable<string>
  code: Nullable<string>
}