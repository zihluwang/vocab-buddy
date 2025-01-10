import { v4 as randomUuid } from "uuid"
import moment from "moment"
import { createHmac } from "crypto"
import { useDefaultStore, useUserStore } from "@/store"

const defaultStore = useDefaultStore()
const userStore = useUserStore()

/**
 * 阿里云服务接入点
 */
const Endpoint = "https://nls-meta.cn-shanghai.aliyuncs.com/"

/**
 * 阿里云服务访问密钥
 * @type {{Secret: string, Id: string}}
 */
const AccessKey: { Id: string; Secret: string } = {
  Id: "my_access_key_id",
  Secret: "my_access_key_secret",
}

/**
 * URL编码
 * @param {string} str 被编码的字符串
 * @returns {string} 编码后的字符串
 */
function percentEncode(str: string): string {
  return encodeURIComponent(str).replace(/\+/g, "%20").replace(/\*/g, "%2A").replace(/%7E/g, "~")
}

/**
 * 生成请求URL
 * @returns {string} 请求URL
 */
function generateAliyunRequestUrl() {
  // 公共请求参数
  const requestParams: Record<string, string> = {
    AccessKeyId: AccessKey.Id,
    Action: "CreateToken",
    Version: "2019-02-28",
    Format: "JSON",
    RegionId: "cn-shanghai",
    Timestamp: moment().utc().format("YYYY-MM-DD[T]HH:mm:ss[Z]"),
    SignatureMethod: "HMAC-SHA1",
    SignatureVersion: "1.0",
    SignatureNonce: randomUuid(),
  }

  const stdQueryStringBuilder = new URLSearchParams()
  Object.keys(requestParams)
    .sort()
    .forEach((key) => stdQueryStringBuilder.append(key, requestParams[key]))

  const stdQueryString = stdQueryStringBuilder.toString()
  const stringToSign = `GET&${encodeURIComponent("/")}&${encodeURIComponent(stdQueryString)}`
  // 按照 base64 编码规则生成最后的签名字符串
  const signature = createHmac("sha1", `${AccessKey.Secret}&`).update(stringToSign).digest().toString("base64")
  return `${Endpoint}?Signature=${percentEncode(signature)}&${stdQueryString}`
}

function audioUrlBuilder(word: string): string {
  const params = new URLSearchParams()
  params.append("appkey", "")
  params.append("text", word)
  params.append("format", "mp3")
  params.append("token", userStore.aLiYunToken || "")
  return `https://proxy.vorbote.cn/aliyun/nls/stream/v1/tts?${params.toString()}`
}

export { generateAliyunRequestUrl, audioUrlBuilder }
