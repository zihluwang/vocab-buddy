<template>
  <n-dialog-provider>
    <router-view />
  </n-dialog-provider>
</template>

<script lang="ts" setup></script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
}

html,
body,
#app {
  width: 100%;
  height: 100%;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

.primary-text {
  color: #212121;
}

.secondary-text {
  color: #757575;
}

.mono {
  font-family: MonoLisa, "Cascadia Mono", Consolas, monospace;
}

.serif {
  font-family: -apple-system, Pingfang, Helvetica neue, "microsoft yahei", simsun, serif;
}
</style>
