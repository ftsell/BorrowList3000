<script setup lang="ts">
import { RouterView } from "vue-router";
import { onMounted } from "vue";
import { useRestApi } from "@/apiClient";
import { useAuthStore } from "@/stores/authStore";

let api = useRestApi();
let authStore = useAuthStore();

onMounted(async () => {
  if (!authStore.isAuthenticated) {
    const response = await api.value.auth.login({
      loginRequest: {
        username: "ftsell",
        password: "foobar123",
      },
    });

    authStore.authToken = response.authToken;
    authStore.persistAuth();
  }
});
</script>

<template>
  <RouterView />
</template>

<style>
@import "normalize.css/normalize.css";
@import "./assets/color-scheme.css";
</style>
