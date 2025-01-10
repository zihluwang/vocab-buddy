<script setup lang="ts">
import { defineProps } from "vue"

const props = defineProps({
  itemIndex: Number,
  label: String,
})
</script>

<template>
  <div class="choose-item">
    <div class="index">{{ props.itemIndex }}</div>
    <div class="label">{{ props.label }}</div>
  </div>
</template>

<style scoped lang="less">
.choose-item {
  width: calc(50% - 7px);
  height: 40px;
  line-height: 40px;
  border: 1px solid #dfdfdf;
  border-radius: 10px;
  margin-bottom: 10px;
  cursor: pointer;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  user-select: none;

  &:nth-child(2n + 1) {
    margin-right: 5px;
  }
  &:nth-child(2n) {
    margin-left: 5px;
  }
  &:hover {
    background-color: #f7f7f7;
  }
  &.active-btn {
    border-color: #84d8ff;
    background-color: #ddf4ff;
    color: #1899d6;
  }

  .index {
    width: 25px;
    height: 25px;
    line-height: 25px;
    text-align: center;
    color: #bfbfbf;
    border: 1px solid #eee;
    margin-left: 10px;
    border-radius: 6px;
    user-select: none;
  }

  .label {
    width: calc(100% - 35px);
    height: 100%;
    text-align: center;
    font-size: 16px;
    user-select: none;
  }
}
</style>
