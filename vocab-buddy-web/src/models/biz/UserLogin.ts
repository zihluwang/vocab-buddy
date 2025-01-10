// todo: 创建一个用户登录返回的data的模型

import { Nullable, SupportedRegion, SupportedRegionCode } from "@/types"

interface UserLogin {
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

export default UserLogin
