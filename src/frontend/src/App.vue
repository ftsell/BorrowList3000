<script setup lang="ts">
import { RouterView } from "vue-router";
import HeaderBar from "@/components/featureComponents/HeaderBar.vue";
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
