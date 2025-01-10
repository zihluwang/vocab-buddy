import { Nullable, SupportedRegion, SupportedRegionCode } from "@/types"

interface Client {
  id: Nullable<string>
  username: Nullable<string>
  email: Nullable<string>
  region: Nullable<SupportedRegion>
  regionCode: Nullable<SupportedRegionCode>
  phone: Nullable<string>
  grade: Nullable<number>
  password: Nullable<string>
}

export default Client