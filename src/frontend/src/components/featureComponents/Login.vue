<script setup lang="ts">
import TextField from "@/components/componentLibrary/TextField.vue";
import CustomButton from "@/components/componentLibrary/CustomButton.vue";
import { useRestApi } from "@/apiClient";
import { useAuthStore } from "@/stores/authStore";
import { useField, useForm } from "vee-validate";
import { string } from "yup";
import { ref } from "vue";
import CircularProgress from "@/components/componentLibrary/CircularProgress.vue";

const form = useForm({
  initialValues: {
    username: "",
    password: "",
  },
});
const username = useField<string>("username", string().required());
const password = useField<string>("password", string().required());

const api = useRestApi();
const authStore = useAuthStore();

const onSubmit = form.handleSubmit(async (values) => {
  const response = await api.value.auth.login({
    loginRequest: values,
  });
  authStore.authToken = response.authToken;
  authStore.persistAuth();
});
</script>

<template>
  <form @submit="onSubmit">
    <p class="space-after">
      Login to an existing account with your own credentials
    </p>

    <TextField
      v-model="username.value.value"
      :error-message="username.errorMessage.value"
      id="login-username"
      type="text"
      label="Username"
      placeholder="e.g. JohnDoe86"
      tab-index="1"
    />

    <TextField
      v-model="password.value.value"
      :error-message="password.errorMessage.value"
      id="login-password"
      type="password"
      label="Password"
      placeholder="**********"
      tab-index="2"
    />

    <CustomButton
      text="Login"
      type="submit"
      :outlined="true"
      tabindex="3"
      class="space-right"
    />
    <span v-if="form.isSubmitting.value || true">
      <CircularProgress :progress="0.5" />
    </span>
  </form>
</template>

<style scoped>
.space-after {
  margin-bottom: 32px;
}

.space-right {
  margin-right: 24px;
}
</style>
