<template>
  <n-layout>
    <n-layout-header>
      <custom-header-menu/>
    </n-layout-header>
    <n-layout-content content-style="padding: 20px 24px;" style="height: calc(100vh - 60px)">
      <router-view/>
    </n-layout-content>
  </n-layout>
</template>

<script lang="ts" setup>
import CustomHeaderMenu from "@/components/CustomHeaderMenu.vue"
</script>

<style scoped>
</style>