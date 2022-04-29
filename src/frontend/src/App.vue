<script setup lang="ts">
import { RouterView } from "vue-router";
import { useAutomaticUserFetching } from "@/stores/userStore";
import { userAutomaticListFetching } from "@/stores/listStore";

useAutomaticUserFetching();
userAutomaticListFetching();
</script>

<template>
  <RouterView />
</template>

<style>
@import "normalize.css/normalize.css";
@import "./assets/color-scheme.css";
</style>

<style scoped></style>
