<script setup lang="ts">
import VerticalDivider from "@/components/componentLibrary/VerticalDivider.vue";

defineProps<{
  title?: string;
}>();
</script>

<template>
  <div class="card elevated">
    <!-- Title bar & title -->
    <div class="title-bar" v-if="title">
      <h2 v-if="title" class="title">{{ title }}</h2>
      <div class="title-bar-extra">
        <slot name="title-bar" />
      </div>
    </div>
    <VerticalDivider v-if="title" />

    <!-- Card content -->
    <slot />
  </div>
</template>

<style scoped>
.card {
  padding: 12px;
  border-radius: 4px;
}

.card .title-bar {
  display: flex;
  align-items: stretch;
  gap: 8px;
}

.card .title-bar .title-bar-extra {
  flex-grow: 1;
  margin-right: 16px;
}

.card .title {
  margin-top: 6px;
  margin-bottom: 6px;
}
</style>
