import { defineStore } from "pinia"
import { reactive, ref } from "vue"
import UserLogin from "@/models/biz/UserLogin"
import { Nullable } from "@/types"

export const useUserStore = defineStore(
  "userStore",
  () => {
    // 用户登录信息
    const userInfo = reactive<UserLogin>({
      id: "",
      username: "",
      email: "",
      region: null,
      regionCode: null,
      phone: "",
      userType: "",
      userTypeCode: null,
      grade: null,
    })
    // 用户令牌
    const userToken = ref<Nullable<string>>(null)
    // 阿里云令牌
    const aLiYunToken = ref<Nullable<string>>(null)

    /**
     * @description 更新用户信息
     * @param _userInfo
     */
    const updateUserInfo = (_userInfo: UserLogin) => {
      Object.assign(userInfo, _userInfo)
    }

    /**
     * #@description 清空用户信息
     */
    const clearUserInfo = () => {
      userInfo.id = ""
      userInfo.username = ""
      userInfo.email = ""
      userInfo.region = null
      userInfo.regionCode = null
      userInfo.phone = ""
      userInfo.userType = ""
      userInfo.userTypeCode = null
      userInfo.grade = null
    }

    /**
     * @description 更新用户令牌
     * @param _userToken
     */
    const updateUserToken = (_userToken: string) => {
      userToken.value = _userToken
    }

    /**
     * @description 清空用户令牌
     */
    const clearUserToken = () => {
      userToken.value = null
    }

    /**
     * @description 更新阿里云令牌
     * @param _aLiYunToken
     */
    const updateAliYunToken = (_aLiYunToken: string) => {
      aLiYunToken.value = _aLiYunToken
    }

    /**
     * @description 清空阿里云令牌
     */
    const clearAliYunToken = () => {
      aLiYunToken.value = null
    }

    return {
      userInfo,
      userToken,
      aLiYunToken,
      updateUserInfo,
      clearUserInfo,
      updateUserToken,
      clearUserToken,
      updateAliYunToken,
      clearAliYunToken,
    }
  },
  {
    persist: true,
  }
)
