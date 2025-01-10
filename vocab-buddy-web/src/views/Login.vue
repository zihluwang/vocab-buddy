<template>
  <n-card class="login-panel">
    <template #header>
      <h3>立即登录 Vocab Buddy</h3>
    </template>
    <n-spin :show="showSpin">
      <n-form
        ref="formLogin"
        label-width="68"
        label-align="left"
        :model="client"
        :rules="loginRules"
        label-placement="left"
      >
        <n-form-item label="用户名" path="username">
          <n-input type="text" v-model:value="client.username" placeholder="请输入用户名">
            <template #prefix>
              <n-icon>
                <carbon-user />
              </n-icon>
            </template>
          </n-input>
        </n-form-item>
        <n-form-item label="密码" path="password">
          <n-input type="password" v-model:value="client.password" placeholder="请输入密码">
            <template #prefix>
              <n-icon>
                <carbon-password />
              </n-icon>
            </template>
          </n-input>
        </n-form-item>
      </n-form>
    </n-spin>
    <template #footer>
      <div class="helpers">
        <router-link :to="{ name: `Register` }" class="tip-link">立即注册</router-link>
        |
        <router-link :to="{ name: `Register` }" class="tip-link">找回密码</router-link>
      </div>
      <n-grid :x-gap="12" :cols="2">
        <n-gi>
          <n-button type="primary" class="btn" @click="login" :disabled="showSpin">
            登录
            <template #icon>
              <n-icon>
                <antd-login-outlined />
              </n-icon>
            </template>
          </n-button>
        </n-gi>
        <n-gi>
          <n-button type="warning" class="btn" @click="clearForm">
            清空
            <template #icon>
              <n-icon>
                <antd-clear-outlined />
              </n-icon>
            </template>
          </n-button>
        </n-gi>
      </n-grid>
    </template>
  </n-card>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue"
import { FormInst, FormRules } from "naive-ui"
import { clientLogin, getAliyunToken } from "@/apis"
import { message } from "@/utils/NaiveUtil"
import { useRoute, useRouter } from "vue-router"
import { useDefaultStore } from "@/store/DefaultStore"
import AntdLoginOutlined from "@/assets/icon/antd/AntdLoginOutlined.vue"
import AntdClearOutlined from "@/assets/icon/antd/AntdClearOutlined.vue"
import CarbonUser from "@/assets/icon/carbon/CarbonUser.vue"
import CarbonPassword from "@/assets/icon/carbon/CarbonPassword.vue"

const router = useRouter()
const route = useRoute()
const defaultStore = useDefaultStore()
const redirect = route.query.redirect
const formLogin = ref<FormInst | null>()

const showSpin = ref<boolean>(false)

const client = reactive({
  username: "",
  password: "",
})
const loginRules: FormRules = {
  username: [{ required: true, message: "用户名不能为空！", trigger: "blur" }],
  password: [{ required: true, message: "密码不能为空！", trigger: "blur" }],
}

function clearForm() {
  client.username = ""
  client.password = ""
}

function login(e: MouseEvent) {
  e.preventDefault()
  showSpin.value = true
  formLogin.value?.validate(async (errors) => {
    if (!errors) {
      Promise.all([clientLogin(client.username, client.password), getAliyunToken()])
        .then(([loginResponse, aliyunResponse]) => {
          if (!(loginResponse.code >= 200 && loginResponse.code < 300)) {
            return Promise.reject(loginResponse.message)
          }

          if (aliyunResponse.Message) {
            return Promise.reject(aliyunResponse.Message)
          }

          // handle success
          if (loginResponse.data) {
            defaultStore.updateClient(loginResponse.data)
            message.success(loginResponse.message)
            if (redirect && typeof redirect == "string" && redirect.startsWith("/")) {
              router.push(redirect)
            } else {
              router.push({ name: "Overview" })
            }
          } else {
            return Promise.reject("登录失败！")
          }

          if (aliyunResponse.Token) {
            defaultStore.updateAliyunToken(aliyunResponse.Token.Id)
          } else {
            return Promise.reject("登录失败！")
          }
        })
        .catch(() => {
          // console.log(errors)
          message.error("哦豁，出错了！")
        })
      showSpin.value = false
    }
  })
}
</script>

<!--region Style-->
<style scoped>
.login-panel {
  /* margin: 0 auto; */
  width: 440px;

  /* Make this form at the centre of the whole screen. */
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  border: 4px #FFC107 solid;
  border-radius: 20px;
}

.btn {
  width: 100%;
}

.helpers {
  margin-bottom: 10px;
}

.tip-link {
  color: #36ad6a;
}
</style>
<!--endregion-->