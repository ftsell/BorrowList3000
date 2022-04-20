<script setup lang="ts">
import TextField from "@/components/componentLibrary/TextField.vue";
import CustomButton from "@/components/componentLibrary/CustomButton.vue";
import CircularProgress from "@/components/componentLibrary/CircularProgress.vue";
import { useField, useForm } from "vee-validate";
import { string } from "yup";
import { useRestApi } from "@/apiClient";
import { useAuthStore } from "@/stores/authStore";

const form = useForm({
  initialValues: {
    username: "",
    password: "",
  },
});
const username = useField("username", string().required());
const password = useField("password", string().required());

const api = useRestApi();
const authStore = useAuthStore();

const onSubmit = form.handleSubmit(async (values) => {
  await api.value.auth.register({
    registerRequest: values,
  });

  const loginResponse = await api.value.auth.login({
    loginRequest: values,
  });
  authStore.authToken = loginResponse.authToken;
  authStore.persistAuth();
});
</script>

<template>
  <form @submit="onSubmit">
    <p class="space-after">Create a new account with the given credentials</p>
    <TextField
      v-model="username.value.value"
      :error-message="username.errorMessage.value"
      id="register-username"
      type="text"
      label="Username"
      placeholder="e.g. JohnDoe86"
      tab-index="4"
    />
    <TextField
      v-model="password.value.value"
      :error-message="password.errorMessage.value"
      id="register-password"
      type="password"
      label="Password"
      placeholder="**********"
      tab-index="5"
    />

    <div class="form-bottom">
      <CustomButton
        text="Register"
        type="submit"
        :outlined="true"
        tabindex="6"
      />
      <div v-show="form.isSubmitting.value" class="loading-container">
        <CircularProgress :indeterminate="true" :size="36" />
        <span>Loading…</span>
      </div>
    </div>
  </form>
</template>

<style scoped>
.space-after {
  margin-bottom: 32px;
}

.form-bottom {
  display: flex;
  align-items: center;
  gap: 32px;
}

.loading-container {
  display: flex;
  align-items: center;
  gap: 8px;
}
</style>
