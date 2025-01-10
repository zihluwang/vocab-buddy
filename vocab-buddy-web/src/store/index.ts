import { createPinia } from "pinia"
import { createPersistedState } from "pinia-plugin-persistedstate"
import { useDefaultStore } from "@/store/DefaultStore"
import { useDictationStore } from "@/store/DictationStore"
import { useStartDictationStore } from "@/store/startDictationStore"
import { useUserStore } from "@/store/UserStore"

const pinia = createPinia().use(
  createPersistedState({
    storage: sessionStorage,
  })
)

export default pinia

export { useDefaultStore, useDictationStore, useStartDictationStore, useUserStore }
