import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router"
import { useDefaultStore } from "@/store/DefaultStore"
import moment from "moment"
import { message } from "@/utils/NaiveUtil"

// 页面跳转路由
const routes: Array<RouteRecordRaw> = [
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/NewLogin.vue"),
    meta: {
      title: "用户登录",
      requireAuth: false,
    },
  },
  {
    path: "/n",
    name: "New",
    component: () => import("@/views/NewDictationExercise.vue"),
    meta: {
      title: "新页面测试",
      requireAuth: false,
    },
  },
  {
    path: "/register",
    name: "Register",
    component: () => import("@/views/Register.vue"),
    meta: {
      title: "用户注册",
      requireAuth: false,
    },
  },
  {
    path: "/",
    name: "Home",
    component: () => import("@/layouts/DefaultLayout.vue"),
    meta: {
      title: "个人主页",
      requireAuth: true,
    },
    redirect: { name: "Overview" },
    children: [
      {
        path: "overview",
        name: "Overview",
        component: () => import("@/views/Overview.vue"),
        meta: {
          title: "听写概览",
          requireAuth: true,
        },
      },
      {
        path: "historical-dictations",
        name: "HistoricalDictations",
        component: () => import("@/views/HistoricalDictations.vue"),
        meta: {
          title: "历史听写",
          requireAuth: true,
        },
      },
      {
        path: "dictation-detail/:dictationId",
        name: "DictationDetail",
        component: () => import("@/views/DictationDetailView.vue"),
        meta: {
          title: "听写详情",
          requireAuth: true,
        },
      },
      {
        path: "generate-dictation",
        name: "GenerateDictation",
        component: () => import("@/views/GenerateDictationExercise.vue"),
        meta: {
          title: "生成听写",
          requireAuth: true,
        },
      },
      {
        path: "dictation-exercise",
        name: "DictationExercise",
        component: () => import("@/views/NewDictationExercise.vue"),
        meta: {
          title: "听写测试",
          requireAuth: true,
        },
      },
      {
        path: "mine",
        name: "Mine",
        component: () => import("@/views/Mine.vue"),
        meta: {
          title: "我的",
          requireAuth: true,
        },
      },
    ],
  },
  {
    path: "/not-found",
    name: "NotFound",
    component: () => import("@/views/NotFound.vue"),
  },
  {
    path: "/:catchAll(.*)",
    redirect: { name: "NotFound" },
  },
]

// 路由器
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router

// 路由守卫
router.beforeEach((to, from, next) => {
  // 修改页面标题
  if (to.meta.title) {
    document.title = to.meta.title
  }

  const defaultStore = useDefaultStore()
  // console.log(defaultStore.token)

  // 检查用户是否登录
  if (to.matched.some((record) => record.meta.requireAuth)) {
    if (defaultStore.token == null || defaultStore.token == "") {
      // 表示用户没有登录
      message.warning("您还没有登录，请登录后再试！", {
        duration: moment.duration(3, "seconds").asMilliseconds(),
        closable: true,
      })
      next({
        name: "Login",
        query: {
          redirect: to.path,
        },
      })
    } else {
      next()
    }
  } else {
    next()
  }
})
