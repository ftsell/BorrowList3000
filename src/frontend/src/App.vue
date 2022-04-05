<script setup lang="ts">
import { RouterView } from "vue-router";
import { onMounted } from "vue";
import { useRestApi } from "@/apiClient";
import { useAuthStore } from "@/stores/authStore";
import { nextTick } from "vue";

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

  await nextTick(async () => {
    console.log(await api.value.auth.listSessions());
  });
});
</script>

<template>
  <RouterView />
</template>

<style></style>
