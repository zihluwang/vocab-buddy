import { defineStore } from "pinia"
import { reactive, ref } from "vue"
import { Nullable } from "@/types"
import Client from "@/models/biz/Client"
import ClientFactory from "@/factories/ClientFactory"

/**
 * 默认 Store
 */
export const useDefaultStore = defineStore("default", () => {
  /**
   * 用户令牌
   */
  const token = ref<Nullable<string>>(null)

  /**
   * 用户数据
   */
  const client = reactive<Client>(ClientFactory.createEmptyClient())

  /**
   * 阿里云令牌
   */
  const aliyunToken = ref<Nullable<string>>(null)

  /**
   * 更新用户令牌
   * @param {string} _token
   */
  function updateToken(_token: string) {
    token.value = _token
  }

  /**
   * 清空用户令牌
   */
  function clearToken() {
    token.value = null
  }

  /**
   * 更新用户数据
   * @param {Client} _client 用户数据
   */
  function updateClient(_client: Client) {
    Object.assign(client, _client)
  }

  /**
   * 清空用户数据
   */
  function clearClient() {
    Object.assign(client, ClientFactory.createEmptyClient())
  }

  /**
   * 更新阿里云请求令牌
   * @param {string} token 阿里云令牌
   */
  function updateAliyunToken(token: string) {
    aliyunToken.value = token
  }

  return {
    token,
    client,
    aliyunToken,
    updateToken,
    updateClient,
    clearToken,
    clearClient,
    updateAliyunToken
  }
}, {
  persist: true
})