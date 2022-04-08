<script setup lang="ts">
import TextField from "@/components/componentLibrary/TextField.vue";
</script>

<template>
  <form>
    <p>Login to an existing account with your own credentials</p>
    <TextField
      id="username"
      type="text"
      label="Username"
      placeholder="e.g. JohnDoe86"
      tab-index="1"
    />
  </form>
</template>

<style scoped></style>
