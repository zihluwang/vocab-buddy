// 创建 Vue App
import { createApp } from "vue"

// Naive UI
import naive from "naive-ui"

// 基本应用
import App from "./App.vue"

// VueRouter
import router from "./router"

// Pinia
import pinia from "@/store"

// 通用字体
import 'vfonts/Lato.css'
// 等宽字体
import 'vfonts/FiraCode.css'

const app = createApp(App)
app.use(router)
  .use(pinia)
  .use(naive)
  .mount("#app")
