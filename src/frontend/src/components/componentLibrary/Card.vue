<script setup lang="ts">
import VerticalDivider from "@/components/componentLibrary/VerticalDivider.vue";

defineProps<{
  title?: string;
}>();
</script>

<template>
  <div class="card elevated">
    <h2 v-if="title" class="card-title">{{ title }}</h2>
    <VerticalDivider v-if="title" />
    <slot />
  </div>
</template>

<style scoped>
.card {
  padding: 12px;
  border-radius: 4px;
}

.card .card-title {
  margin-top: 6px;
  margin-bottom: 6px;
}
</style>
