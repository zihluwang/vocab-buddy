import { NIcon } from "naive-ui"
import { Component, h } from "vue"

function buildIcon(icon: Component) {
  return () => h(NIcon, null, { default: () => h(icon) })
}

export { buildIcon }