<template>
    <div class="register-container">
        <div class="register-wrapper">
            <div class="illustration-box">
                <img src="@/assets/images/register_illustration.jpg" alt="">
            </div>
            <div class="register-card">
                <div class="title">快速开始</div>
                <div class="sub-title">即刻创建专属于你的账号</div>
                <n-form ref="registerFormRef" :model="registerModel" label-align="left" label-placement="top"
                        :rules="registerRules">
                    <n-form-item size="large" path="username" label="用户名">
                        <n-input v-model:value="registerModel.username" placeholder="请输入用户名">
                            <template #prefix>
                                <n-icon>
                                    <carbon-user/>
                                </n-icon>
                            </template>
                        </n-input>
                    </n-form-item>
                    <n-form-item size="large" path="password" label="密码">
                        <n-input v-model:value="registerModel.password" type="password" placeholder="请输入密码">
                            <template #prefix>
                                <n-icon>
                                    <carbon-password/>
                                </n-icon>
                            </template>
                        </n-input>
                    </n-form-item>
                    <n-form-item size="large" path="confirmPwd" label="确认密码">
                        <n-input v-model:value="registerModel.confirmPwd" type="password" placeholder="请再次输入密码">
                            <template #prefix>
                                <n-icon>
                                    <carbon-password/>
                                </n-icon>
                            </template>
                        </n-input>
                    </n-form-item>
                    <n-form-item size="large" path="phone" label="国家/地区及手机号">
                        <n-select v-model:value="registerModel.region" size="large" :options="regionOptions"
                                  placeholder="请选择国家与地区" :loading="regionSelectLoading" class="region-select"/>
                        <n-input v-model:value="registerModel.phone" placeholder="请输入手机号"
                                 :allow-input="onlyAllowNumber" class="phone-input">
                            <template #prefix>
                                <n-icon>
                                    <carbon-phone/>
                                </n-icon>
                            </template>
                        </n-input>
                    </n-form-item>
                    <n-form-item size="large" path="email" label="邮箱">
                        <n-input v-model:value="registerModel.email" placeholder="请输入邮箱地址">
                            <template #prefix>
                                <n-icon>
                                    <carbon-email/>
                                </n-icon>
                            </template>
                        </n-input>
                    </n-form-item>
                    <n-form-item size="large" path="grade" label="年级">
                        <n-select v-model:value="registerModel.grade" size="large" :options="gradeOptions"
                                  placeholder="请选择年级"/>
                    </n-form-item>
                </n-form>
                <div class="register-btn-group">
                    <n-button type="success" @click.prevent="registerApi" :loading="registerBtnLoading">注册</n-button>
                </div>
                <div class="login-tip">
                    <span class="text">已经拥有账户？</span>
                    <span class="link">
                    <router-link :to="{ name: `Login` }" class="tip-link">立即登录！</router-link>
                </span>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import CarbonPassword from "@/assets/icon/carbon/CarbonPassword.vue";
import CarbonUser from "@/assets/icon/carbon/CarbonUser.vue";
import CarbonEmail from "@/assets/icon/carbon/CarbonEmail.vue";
import CarbonPhone from "@/assets/icon/carbon/CarbonPhone.vue";
import {onMounted, reactive, ref} from "vue";
import {FormInst, FormRules, FormItemRule, SelectOption} from "naive-ui";
import gradeLevel from "@/datas/gradeLevel";
import regionList from "@/datas/regionList";
import {getSupportedRegions, getAliyunToken, userRegisterV3} from "@/apis";
import {message} from "@/utils/NaiveUtil";
import RegisterParamsV3 from "@/models/request/RegisterParamsV3";
import {useRouter} from "vue-router"
import AliyunToken from "@/models/biz/AliyunToken";
import {useUserStore} from "@/store";
import UserLogin from "@/models/biz/UserLogin";

const router = useRouter()
const userStore = useUserStore()

const registerFormRef = ref<FormInst | null>(null)

const registerModel = reactive({
    username: '',
    password: '',
    confirmPwd: '',
    region: 86,
    phone: '',
    email: '',
    grade: 1
})

// 表单校验规则
const registerRules: FormRules = {
    username: [{required: true, message: "用户名不能为空！", trigger: "blur"}],
    password: [{required: true, message: "密码不能为空！", trigger: "blur"}],
    confirmPwd: [
        { required: true, message: "请再次输入密码", trigger: "blur" },
        { validator: confirmValidator, message: "两次密码不一致", trigger: ["input", "blur"]}
    ],
    phone: [{required: true, message: "请填写手机号码", trigger: "blur"}],
    email: [
        {validator: emailAddressValidator, message: "邮箱地址格式错误", trigger: ["input", "blur"]}
    ]
}

let regionOptions: SelectOption[] = []
const gradeOptions: SelectOption[] = gradeLevel
const regionSelectLoading = ref(false)
const registerBtnLoading = ref(false)

// 校验重复密码是否一致
function confirmValidator(rule: FormItemRule, value: string): boolean {
    return value === registerModel.password;
}

// 校验邮箱格式
function emailAddressValidator(rule: FormItemRule, value: string): boolean {
    const regex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
    return value === '' ? true : regex.test(value);
}

// 只允许输入数字（用于格式化手机号输入框）
function onlyAllowNumber(value: string) {
    return !value || /^\d+$/.test(value)
}

/**
 * 获取受支持的国家地区电话号码
 */
async function getSupportedRegionApi() {
    try {
        regionSelectLoading.value = true
        const {code, data} = await getSupportedRegions()
        if (code === 200 && data) {
            regionOptions = data.map(item => ({label: item.label, value: item.code}))
        } else {
            // 使用内置列表顶替
            regionOptions = regionList
        }
    } catch (e) {
        if (e instanceof Error) {
            console.error(e.message)
            // message.error("区号数据加载失败，请刷新重试")
        } else {
            console.error(e)
            // message.error("区号数据加载失败，请刷新重试")
        }
        // 使用内置列表顶替
        regionOptions = regionList
    } finally {
        regionSelectLoading.value = false
    }
}

// 注册账号
function registerApi() {
    registerFormRef?.value?.validate((error) => {
        if (!error) {
            const params: RegisterParamsV3 = {
                username: registerModel.username,
                password: registerModel.password,
                region: registerModel.region,
                phone: registerModel.phone,
                grade: registerModel.grade,
                email: registerModel.email.trim() !== "" ? registerModel.email : null,
            }
            // 请求
            registerBtnLoading.value = true
            userRegisterV3(params)
                // 注册请求解析
                .then((res) => {
                    const {code, data} = res
                    if (code === 200 && data) {
                        message.success("账户注册成功")
                        userStore.updateUserInfo(data as unknown as UserLogin)
                        // 请求阿里云
                        return getAliyunToken()
                    } else {
                        message.warning(res.message || "注册账户异常，请稍后重试")
                    }
                })
                // 阿里云请求解析
                .then((res) => {
                    const { Message, Token } = res as AliyunToken
                    if (Message) {
                        // 回复 userStore
                        userStore.clearUserInfo()
                        // 跳转到login页面
                        router.push({name: 'Login', query: {username: registerModel.username}})
                    }
                    if (Token) {
                        userStore.updateAliYunToken(Token.Id)
                        // 跳转到主页
                        router.push({name: 'Overview'})
                    }
                })
                // 请求失败
                .catch((e) => {
                    if (e instanceof Error) {
                        console.error(e.message)
                    } else {
                        console.error(e)
                    }
                })
                .finally(() => {
                    registerBtnLoading.value = false
                })
        } else {
            message.warning(`请将${error.length}处错误修正后再操作`)
        }
    })
}

onMounted(async () => {
    await getSupportedRegionApi();
})

</script>

<style scoped lang="less">
.register-container {
  position: relative;
  width: 100%;
  height: 100%;
  // 背景图片
  background-image: url('@/assets/images/2kb_bg.png');
  background-repeat: repeat;

  .register-wrapper {
    // 水平垂直居中
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    // 基础样式
    width: 75%;
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

  // 注册表单
  .register-card {
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
      margin-bottom: 15px;
    }

    .n-form-item {
      /deep/ .n-form-item-blank {
        .region-select {
          flex: 1;
          margin-right: 5px;
        }

        .phone-input {
          flex: 3;
          margin-left: 5px;
        }
      }
    }

    .register-btn-group {
      width: 100%;
      display: flex;
      flex-direction: row;
      flex-wrap: wrap;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;

      /deep/ button {
        width: 100%;
        height: 40px;

        &:nth-child(1) {
          margin-bottom: 15px;
        }
      }
    }

    .login-tip {
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
