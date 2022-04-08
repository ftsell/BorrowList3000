<script setup lang="ts">
import TextField from "@/components/componentLibrary/TextField.vue";
import { ref } from "vue";

const username = ref("");
const password = ref("");
</script>

<template>
  <form>
    <p class="space-after">Create a new account with the given credentials</p>
    <TextField
      v-model="username"
      id="register-username"
      type="text"
      label="Username"
      placeholder="e.g. JohnDoe86"
      tab-index="3"
    />
    <TextField
      v-model="password"
      id="register-password"
      type="password"
      label="Password"
      placeholder="**********"
      tab-index="4"
    />
  </form>
</template>

<style scoped>
.space-after {
  margin-bottom: 32px;
}
</style>
