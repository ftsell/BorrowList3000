<script setup lang="ts">
defineProps<{
  url: string;
  alt: string;
}>();
</script>

<template>
  <img :src="url" :alt="alt" />
</template>

<style scoped></style>
