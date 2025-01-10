import { Nullable } from "@/types"

/**
 * 分页数据
 */
export default interface Paginated<T = unknown> {
  /**
   * 分页数据
   */
  records: Nullable<T[]>

  /**
   * 总条目数
   */
  total: number

  /**
   * 当前页面数据最大量
   */
  size: number

  /**
   * 当前页码
   */
  current: number

  /**
   * 共有页面数量
   */
  pages: number
}