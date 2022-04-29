<script setup lang="ts">
import { RouterView } from "vue-router";
import { useAutomaticUserFetching } from "@/stores/userStore";
import { useAutomaticListFetching } from "@/stores/listStore";
import { useAutomaticPeopleFetching } from "@/stores/peopleStore";
import { useAutomaticThingFetching } from "@/stores/thingStore";

useAutomaticUserFetching();
useAutomaticListFetching();
useAutomaticPeopleFetching();
useAutomaticThingFetching();
</script>

<template>
  <RouterView />
</template>

<style>
@import "normalize.css/normalize.css";
@import "./assets/color-scheme.css";
</style>

<style scoped></style>
