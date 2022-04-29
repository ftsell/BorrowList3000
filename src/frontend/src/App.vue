<script setup lang="ts">
import { RouterView } from "vue-router";
import { useAutomaticUserFetching } from "@/stores/userStore";
import { useAutomaticListFetching } from "@/stores/listStore";
import { useAutomaticPeopleFetching } from "@/stores/peopleStore";
import { useAutomaticThingFetching } from "@/stores/thingStore";
import { applyPersistedAuthState, useAuthStore } from "@/stores/authStore";
import { onMounted } from "vue";

const authStore = useAuthStore();
useAutomaticUserFetching();
useAutomaticListFetching();
useAutomaticPeopleFetching();
useAutomaticThingFetching();

onMounted(async () => {
  await applyPersistedAuthState(authStore);
});
</script>

<template>
  <RouterView />
</template>

<style>
@import "normalize.css/normalize.css";
@import "./assets/utils.css";
@import "./assets/color-scheme.css";
</style>

<style scoped></style>
