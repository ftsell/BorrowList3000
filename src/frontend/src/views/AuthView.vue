<script lang="ts" setup>
import CustomButton from "@/components/componentLibrary/CustomButton.vue";
import VerticalDivider from "@/components/componentLibrary/VerticalDivider.vue";
import { useRestApi } from "@/apiClient";
import { useAuthStore } from "@/stores/authStore";
import { useRouter } from "vue-router";

const router = useRouter();
const api = useRestApi();
const authStore = useAuthStore();

async function createAccount(): Promise<void> {
  const response = await api.value.auth.register({
    registerRequest: {},
  });
  authStore.authToken = response.token;
  authStore.persistAuth();
  await router.push({ name: "home" });
}
</script>

<template>
  <div class="view-container">
    <main>
      <h1>Currently operating without user account</h1>
      <VerticalDivider />

      <p>
        You can create an account by clicking the button. Don't worry, you won't
        need to enter any additional information.
      </p>

      <div class="vertical-container">
        <CustomButton text="Create Account" @click="createAccount" />
        <span>or</span>
        <CustomButton text="Login" />
      </div>
    </main>
  </div>
</template>

<style scoped>
main {
  max-width: 70%;
}

h1 {
  margin-bottom: 8px;
}

.view-container {
  display: flex;
  justify-content: center;
  min-height: 100vh;
}

.vertical-container {
  display: flex;
  justify-content: center;
  align-items: baseline;
  gap: 16px;
}
</style>
