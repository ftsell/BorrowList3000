<script setup lang="ts">
import { RouterView } from "vue-router";
import { onMounted } from "vue";
import { useRestApi } from "@/apiClient";
import { useAuthStore } from "@/stores/authStore";
import HeaderBar from "@/components/featureComponents/HeaderBar.vue";

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
  <HeaderBar />
  <RouterView />
</template>

<style>
@import "normalize.css/normalize.css";
@import "./assets/color-scheme.css";
</style>

<style scoped></style>
