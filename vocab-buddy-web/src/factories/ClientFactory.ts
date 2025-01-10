import Client from "@/models/biz/Client"
import { SupportedRegionCode } from "@/types"
import Region from "@/models/biz/Region"

class ClientFactory {
  // 屏蔽构造函数防止被实例化
  private constructor() {
    // do nothing
  }
  
  public static createEmptyClient(option?: {
    email?: string,
    grade?: number,
    id?: string,
    password?: string,
    phone?: string,
    regionCode?: SupportedRegionCode,
    username?: string
  }): Client {
    return {
      email: option?.email || null,
      grade: option?.grade || null,
      id: option?.id || null,
      password: option?.password || null,
      phone: option?.phone || null,
      region: Region.byCode(option?.regionCode || 0)?.region || null,
      regionCode: option?.regionCode || null,
      username: option?.username || null
    }
  }
}

export default ClientFactory