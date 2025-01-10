import { Nullable, SupportedRegion, SupportedRegionCode } from "@/types"

interface Register {
  id: Nullable<string>
  username: Nullable<string>
  email: Nullable<string>
  region: Nullable<SupportedRegion>
  regionCode: Nullable<SupportedRegionCode>
  phone: Nullable<string>
  userType: Nullable<string>
  userTypeCode: Nullable<number>
  grade: Nullable<number>
}

export default Register
