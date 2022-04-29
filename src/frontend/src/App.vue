<script setup lang="ts">
import { RouterView } from "vue-router";
import { useAutomaticUserFetching } from "@/stores/userStore";

useAutomaticUserFetching();
</script>

<template>
  <RouterView />
</template>

<style>
@import "normalize.css/normalize.css";
@import "./assets/color-scheme.css";
</style>

<style scoped></style>
