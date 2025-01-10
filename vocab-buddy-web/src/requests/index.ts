import axios, { AxiosError } from "axios"
import moment from "moment"
import { useDefaultStore } from "@/store/DefaultStore"
import { message } from "@/utils/NaiveUtil"
import { AUTH_KEY } from "@/persists/constants"
import router from "@/router"

const defaultStore = useDefaultStore()

const fetch = axios.create({
  // baseURL: "http://192.168.2.51:9080",
  baseURL: "https://vocab-buddy.vorbote.cn/api/",
  timeout: moment.duration({ seconds: 10 }).asMilliseconds()
})

fetch.interceptors.request.use(
  (request) => {
    if (defaultStore.token) {
      request.headers[AUTH_KEY] = defaultStore.token
    }

    return request
  },
  (error) => {
    console.log(error)
    message.error("网络异常，请检查网络连接！")
    return Promise.reject(error)
  }
)

fetch.interceptors.response.use(
  (response) => {
    if (response.headers[AUTH_KEY.toLowerCase()]) {
      defaultStore.updateToken(response.headers[AUTH_KEY.toLowerCase()])
    }

    return response.data
  },
  async (error) => {
    if (error instanceof AxiosError) {
      // console.log(typeof error.code)
      if (error.response) {
        message.error(error.response.data["message"], {
          duration: moment.duration({ seconds: 3 }).asMilliseconds(),
          closable: true
        })
        await router.push({ name: "Login", query: { redirect: router.currentRoute.value.fullPath } })
      } else {
        message.error("网络连接错误，请咨询客服！", {
          duration: moment.duration({ seconds: 3 }).asMilliseconds(),
          closable: true
        })
      }
    } else {
      message.error("哦豁，出错了～", {
        duration: moment.duration({ seconds: 3 }).asMilliseconds(),
        closable: true
      })
    }
    return Promise.reject(error)
  }
)

const pureFetch = axios.create({
  timeout: moment.duration({ seconds: 10 }).asMilliseconds()
})

pureFetch.interceptors.response.use(
  (value) => value.data,
  (error) => Promise.reject(error)
)

export default fetch
export { pureFetch }
