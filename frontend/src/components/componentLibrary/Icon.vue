<script setup lang="ts">
defineProps<{
  rawSvg: string;
}>();
</script>

<template>
  <svg v-html="rawSvg" />
</template>

<style scoped></style>
