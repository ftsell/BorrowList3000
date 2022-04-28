<script setup lang="ts">
import { RouterView } from "vue-router";
import HeaderBar from "@/components/featureComponents/HeaderBar.vue";
import { useAutomaticUserFetching } from "@/stores/userStore";

useAutomaticUserFetching();
</script>

<template>
  <HeaderBar />
  <RouterView />
</template>

<style>
@import "normalize.css/normalize.css";
@import "./assets/color-scheme.css";
</style>

<style scoped></style>
