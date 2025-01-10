import { Nullable, SupportedRegion, SupportedRegionCode } from "@/types"

class Region {
  private readonly code: SupportedRegionCode
  private readonly label: SupportedRegion

  private constructor(code: SupportedRegionCode, label: SupportedRegion) {
    this.code = code
    this.label = label
  }

  /**
   * 获取该地区的国际区号
   * @returns {number} 该地区的国际区号
   */
  public get regionCode(): number {
    return this.code
  }

  /**
   * 获取该地区的地区名称
   * @returns {string} 该地区的名称
   */
  public get region(): SupportedRegion {
    return this.label
  }

  /**
   * 中国大陆地区
   * @type {Region}
   */
  public static readonly CHINA_MAINLAND = new Region(86, "中国大陆")

  /**
   * 中国香港特别行政区
   * @type {Region}
   */
  public static readonly HONG_KONG = new Region(852, "中国香港")

  /**
   * 中国澳门特别行政区
   * @type {Region}
   */
  public static readonly MACAU = new Region(853, "中国澳门")

  /**
   * 中国台湾特别行政区
   * @type {Region}
   */
  public static readonly TAIWAN = new Region(886, "中国台湾")

  public static get values() {
    return [this.CHINA_MAINLAND, this.HONG_KONG, this.MACAU, this.TAIWAN]
  }

  public static byCode(code: number): Nullable<Region> {
    return this.values.find((item) => item.code == code) || null
  }
}

export default Region