<template>
  <div class="new-login">
    <div class="login-wrapper">
      <div class="illustration-box">
        <img src="@/assets/images/login_illustration.jpg" alt="" />
      </div>
      <div class="new-login-card">
        <div class="title">欢迎回来！</div>
        <div class="sub-title">输入您的详细信息以登录您的账户</div>
        <n-form ref="loginFormRef" :model="loginModel" label-align="left" label-placement="top" :rules="loginRules">
          <n-form-item size="large" path="username" label="用户名">
            <n-input v-model:value="loginModel.username" placeholder="请输入用户名">
              <template #prefix>
                <n-icon>
                  <carbon-user />
                </n-icon>
              </template>
            </n-input>
          </n-form-item>
          <n-form-item size="large" path="password" label="密码" class="password-form-item">
            <n-input v-model:value="loginModel.password" type="password" placeholder="请输入密码">
              <template #prefix>
                <n-icon>
                  <carbon-password />
                </n-icon>
              </template>
            </n-input>
          </n-form-item>
          <n-form-item class="radio-forget">
            <n-radio-group v-model:value="loginModel.checkedValue" name="radioGroup1">
              <n-space>
                <n-radio value="client">用户</n-radio>
                <n-radio value="admin">管理员</n-radio>
              </n-space>
            </n-radio-group>
            <div class="forget-pwd-tip">
              <router-link :to="{ name: `Register` }" class="tip-link">忘记密码？</router-link>
            </div>
          </n-form-item>
        </n-form>
        <div class="login-btn-group">
          <n-button type="success" @click.prevent="login" :loading="loginLoading">登录</n-button>
          <!--<n-button @click="clearForm">重置</n-button>-->
        </div>
        <div class="register-tip">
          <span class="text">还没有账户？</span>
          <span class="link">
            <router-link :to="{ name: `Register` }" class="tip-link">立即注册！</router-link>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from "vue"
import CarbonUser from "@/assets/icon/carbon/CarbonUser.vue"
import CarbonPassword from "@/assets/icon/carbon/CarbonPassword.vue"
import { FormInst, FormRules } from "naive-ui"
import { userLoginV3, adminLoginV3, getAliyunToken } from "@/apis"
import { message } from "@/utils/NaiveUtil"
import Response from "@/models/basic/Response"
import Client from "@/models/biz/Client"
import Admin from "@/models/biz/Admin"
import AliyunToken from "@/models/biz/AliyunToken"
import { useRoute, useRouter } from "vue-router"
import { useUserStore, useDefaultStore } from "@/store"
// import UserLogin from "@/models/biz/UserLogin"

const router = useRouter()
const route = useRoute()
const redirect = route.query.redirect
const userStore = useUserStore()
const defaultStore = useDefaultStore()

// loginForm ref 对象
const loginFormRef = ref<FormInst | null>(null)

// 登录按钮加载状态
const loginLoading = ref<boolean>(false)

// 登录表单
const loginModel = reactive({
  username: "",
  password: "",
  checkedValue: "client",
})

// 登录校验规则
const loginRules: FormRules = {
  username: [{ required: true, message: "用户名不能为空！", trigger: "blur" }],
  password: [{ required: true, message: "密码不能为空！", trigger: "blur" }],
}

// function clearForm() {
//     loginModel.username = ""
//     loginModel.password = ""
//     loginModel.checkedValue = "client"
// }

function login() {
  loginLoading.value = true
  /*
   * 校验
   * 判断身份
   * 调用对应的接口进行请求
   * 返回结果并输出
   * */
  loginFormRef.value?.validate((error) => {
    if (!error) {
      const promiseArray = []
      if (loginModel.checkedValue === "client") {
        promiseArray.push(userLoginV3(loginModel.username, loginModel.password))
      } else {
        promiseArray.push(adminLoginV3(loginModel.username, loginModel.password))
      }
      // promiseArray.push(userLogin(loginModel.username, loginModel.password))
      promiseArray.push(getAliyunToken())
      Promise.all(promiseArray)
        .then(async (results) => {
          loginModel.checkedValue === "client"
            ? await clientResultParser(results[0] as Response<Client>)
            : await adminResultParser(results[0] as Response<Admin>)

          // const loginResult = results[0] as Response<UserLogin>
          const aLiYunResult = results[1] as AliyunToken

          // todo: 编写userLogin的返回解析
          // if (loginResult) {
          //   const { code, data } = loginResult
          //   if (code >= 200 && code < 300) {
          //     const userTypeCode = data?.userTypeCode
          //     if (userTypeCode) {
          //       switch (userTypeCode) {
          //         case 1:
          //           userStore.updateUserInfo(loginResult.data as UserLogin)
          //           message.success(loginResult.message)
          //           if (redirect && typeof redirect == "string" && redirect.startsWith("/")) {
          //             await router.push(redirect)
          //           } else {
          //             await router.push({ name: "Overview" })
          //           }
          //           console.log("用户登录成功")
          //           break
          //         case 2:
          //           console.log("管理员登录成功")
          //           break
          //         case 3:
          //           console.log("超级管理员登录成功")
          //           break
          //       }
          //     }
          //   } else {
          //     return Promise.reject(loginResult.message)
          //   }
          // }

          // 解析阿里云
          if (aLiYunResult.Message) {
            return Promise.reject(aLiYunResult.Message)
          }

          if (aLiYunResult.Token) {
            userStore.updateAliYunToken(aLiYunResult.Token.Id)
          } else {
            return Promise.reject("登录失败！")
          }
        })
        .catch((e) => {
          if (e instanceof Error) {
            console.error(e.message)
          } else {
            console.error(e)
          }
          message.error("哦豁，出错了！")
        })
        .finally(() => {
          loginLoading.value = false
        })
    }
  })
}

async function clientResultParser(response: Response<Client>) {
  if (!(response.code >= 200 && response.code < 300)) {
    return Promise.reject(response.message)
  }
  if (response.data) {
    defaultStore.updateClient(response.data)
    message.success(response.message)
    if (redirect && typeof redirect == "string" && redirect.startsWith("/")) {
      await router.push(redirect)
    } else {
      await router.push({ name: "Overview" })
    }
  } else {
    return Promise.reject("登录失败！")
  }
}

async function adminResultParser(response: Response<Admin>) {
  if (!(response.code >= 200 && response.code < 300)) {
    return Promise.reject(response.message)
  }
  // todo: 不知道怎么跳转暂时先用console占位
  console.log("admin登陆成功!!!")
}

onMounted(() => {
  // 注册跳转至login会自动填写刚注册的账号的username
  const username = route.query.username
  if (username && typeof username === "string") {
    loginModel.username = username
  }
})
</script>

<style scoped lang="less">
.new-login {
  position: relative;
  width: 100%;
  height: 100%;
  // 背景图片
  background-image: url("@/assets/images/2kb_bg.png");
  background-repeat: repeat;

  .login-wrapper {
    // 水平垂直居中
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    // 基础样式
    width: 50%;
    height: 60%;
    min-width: 450px;
    min-height: 550px;
    background-color: #fff;
    box-shadow: 0 0 5px 5px rgba(0, 0, 0, 0.1);
    box-sizing: border-box;
    // 配置子元素并排
    display: flex;
    flex-wrap: nowrap;
    flex-direction: row;
  }

  .illustration-box {
    flex: 1.4;
    display: flex;
    align-items: center;
    justify-content: center;

    img {
      max-width: 100%;
      max-height: 100%;
      height: auto;
      width: auto;
    }
  }

  // 登录表单
  .new-login-card {
    position: relative;
    flex: 1;
    height: 100%;
    box-sizing: border-box;
    padding: 35px;

    .title {
      font-size: 30px;
      font-weight: 700;
    }

    .sub-title {
      font-size: 16px;
      color: #aaa;
      margin-bottom: 40px;
    }

    // 密码框自带的间隔
    .password-form-item {
      /deep/ .n-form-item-feedback-wrapper {
        height: 15px;
        min-height: 15px;
      }
    }

    .radio-forget {
      grid-template-areas: "blank" "feedback";
      // 忘记密码靠右
      /deep/ .n-form-item-blank {
        justify-content: space-between;
      }

      /deep/ .n-form-item-feedback-wrapper {
        height: 10px;
        min-height: 10px;
      }
    }

    .login-btn-group {
      width: 100%;
      display: flex;
      flex-direction: row;
      flex-wrap: wrap;
      justify-content: space-between;
      align-items: center;

      /deep/ button {
        width: 100%;
        height: 40px;

        &:nth-child(1) {
          margin-bottom: 15px;
        }
      }
    }

    .register-tip {
      position: absolute;
      bottom: 20px;
      width: calc(100% - 70px);
      text-align: center;
    }

    .tip-link {
      color: black;
      text-decoration: none;

      &:hover {
        color: #18a058;
      }
    }
  }
}
</style>
