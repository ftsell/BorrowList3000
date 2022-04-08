<script setup lang="ts">
import TextField from "@/components/componentLibrary/TextField.vue";

const username = "";
const password = "";
</script>

<template>
  <form>
    <p class="space-after">
      Login to an existing account with your own credentials
    </p>
    <TextField
      v-model="username"
      id="username"
      type="text"
      label="Username"
      placeholder="e.g. JohnDoe86"
      tab-index="1"
    />
    <TextField
      v-model="password"
      id="password"
      type="password"
      label="Password"
      placeholder="**********"
      tab-index="2"
    />
  </form>
</template>

<style scoped>
.space-after {
  margin-bottom: 32px;
}
</style>
