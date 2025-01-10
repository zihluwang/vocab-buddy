/**
 * 可以为空数据的类型
 */
export type Nullable<T = unknown> = T | null

export type SupportedRegionCode = 86 | 852 | 853 | 886
export type SupportedRegion = "中国大陆" | "中国香港" | "中国澳门" | "中国台湾"