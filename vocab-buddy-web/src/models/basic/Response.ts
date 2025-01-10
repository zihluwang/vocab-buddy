import { Nullable } from "@/types"

/**
 * 响应内容
 */
export interface Response<T = unknown> {
  /**
   * 请求ID
   */
  requestId: string

  /**
   * 响应数据
   */
  data: Nullable<T>

  /**
   * 响应信息
   */
  message: string

  /**
   * 当前时间戳
   */
  timestamp: bigint

  /**
   * 响应代码
   */
  code: number
}

export default Response