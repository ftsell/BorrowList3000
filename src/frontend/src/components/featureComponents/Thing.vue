<script setup lang="ts">
import type { ThingDto } from "@/apiClient";

defineProps<{
  thing: ThingDto;
}>();
</script>

<template>
  <div>
    {{ thing.name }}
  </div>
</template>

<style scoped></style>
