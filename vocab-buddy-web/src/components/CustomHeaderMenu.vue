<template>
  <div class="menu-bar">
    <n-menu v-model:value="activeKey" mode="horizontal" :options="businessMenuOptions" class="menu-bar-left"></n-menu>
    <n-menu
      v-model:value="activeKey"
      mode="horizontal"
      :options="personalMenuOptions"
      class="menu-bar-right"
      @update:value="handleUpdatePersonalMenu"
    ></n-menu>
  </div>
</template>

<script lang="ts" setup>
import { h, ref } from "vue"
import { MenuOption } from "naive-ui"
import { RouterLink, useRoute } from "vue-router"
import { buildIcon } from "@/utils/IconUtil"
import IconUser from "@/assets/icon/carbon/CarbonUser.vue"
import IconDashboard from "@/assets/icon/carbon/CarbonDashboard.vue"
import IconHistoryOutlined from "@/assets/icon/antd/AntdHistoryOutlined.vue"
import IconUserProfile from "@/assets/icon/carbon/CarbonUserProfile.vue"
import IconLogoutOutlined from "@/assets/icon/antd/AntdLogoutOutlined.vue"
import { dialog, message } from "@/utils/NaiveUtil"
import { useDefaultStore } from "@/store/DefaultStore"
import { useStartDictationStore } from "@/store"
import moment from "moment"
import router from "@/router"
import IconStartSharp from "@/assets/icon/material/MaterialStartSharp.vue"
import DictationStartForm from "@/components/DictationStartForm.vue"
import { QuestionBankItemInterface } from "@/models/biz/StartDictation"

const route = useRoute()
const defaultStore = useDefaultStore()
const startDictationStore = useStartDictationStore()

const activeKey = ref<string>(route.path.replace("/", ""))
const businessMenuOptions: MenuOption[] = [
  {
    key: "overview",
    label: () => h(RouterLink, { to: { name: "Overview" } }, { default: () => "近期听写概览" }),
    icon: buildIcon(IconDashboard),
  },
  {
    key: "historical-dictations",
    label: () => h(RouterLink, { to: { name: "HistoricalDictations" } }, { default: () => "听写历史记录" }),
    icon: buildIcon(IconHistoryOutlined),
  },
]

const personalMenuOptions: MenuOption[] = [
  {
    key: "create-dictation",
    // label: () => h(RouterLink, { to: { name: "GenerateDictation" } }, { default: () => "开始听写" }),
    label: "开始听写",
    icon: buildIcon(IconStartSharp),
  },
  {
    key: "personal",
    label: () => h(RouterLink, { to: { name: "Mine" } }, { default: () => "个人中心" }),
    icon: buildIcon(IconUser),
    children: [
      {
        key: "mine",
        // label: () => h(RouterLink, { to: { name: "Mine" } }, { default: () => "个人中心" }),
        label: "我的",
        icon: buildIcon(IconUserProfile),
      },
      {
        key: "logout",
        label: "退出登录",
        icon: buildIcon(IconLogoutOutlined),
      },
    ],
  },
]

function handleUpdatePersonalMenu(key: string) {
  switch (key) {
    case "create-dictation":
      dictationDialog()
      break
    case "logout":
      logoutDialog()
      break
    default:
      console.log("当前激活menu暂无对应回调事件")
  }
}

// 退出登录
function logoutDialog() {
  dialog.warning({
    title: "确定要退出吗？",
    positiveText: "是",
    negativeText: "否",
    maskClosable: false,
    onPositiveClick: () => {
      message.info("欢迎您的下次使用，再见！", {
        duration: moment.duration({ seconds: 3 }).asMilliseconds(),
      })
      defaultStore.clearClient()
      defaultStore.clearToken()
      router.push({ name: "Login" })
    },
  })
}

// 创建听力开始的 dialog 函数
function dictationDialog() {
  dialog.info({
    title: "创建新的训练",
    content: () => h(DictationStartForm),
    positiveText: "开始训练",
    negativeText: "取消训练",
    maskClosable: false,
    closable: false,
    onPositiveClick: () => {
      const questionBankList = startDictationStore.getOneData("questionBankList")
      // 得到的是下拉框option的value
      const chooseQuestionBank = startDictationStore.getOneData("chooseQuestionBank")
      const chooseOption = (questionBankList as QuestionBankItemInterface[]).find(
        (item) => item.value === chooseQuestionBank
      )

      message.success("开始训练")
      // 业务需求指定的路由
      router.push({
        name: "DictationExercise",
        query: {
          tagId: chooseOption?.id,
          wordsCount: startDictationStore.getOneData("totalQuestionsNumber") as number,
          answeringMode: startDictationStore.getOneData("chooseTrainingProgram") as string,
          generationMode: startDictationStore.getOneData("isWrongMode") as number
        },
      })

      // 测试新页面的时候将路由切换至 New
      // router.push({
      //   name: "New",
      //   query: {
      //     tagId: chooseOption?.id,
      //     wordsCount: startDictationStore.getOneData("totalQuestionsNumber") as number,
      //     answeringMode: startDictationStore.getOneData("chooseTrainingProgram") as string,
      //     generationMode: startDictationStore.getOneData("isWrongMode") as number
      //   },
      // })
    },
    onNegativeClick: () => {
      message.success("已取消")
    },
  })
}
</script>

<style scoped>
.menu-bar {
  width: 100%;
  /* height: 60px; */
  border-bottom: 1px solid lightcyan;

  display: flex;
  justify-content: space-between;
}

.menu-bar-flex {
  display: inline-flex;
  /* flex: 1; */
}
</style>
