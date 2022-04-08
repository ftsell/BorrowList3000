<script setup lang="ts">
defineProps<{
  modelValue?: number | string;
  indeterminate?: boolean | string;
}>();

defineEmits<{
  (e: "update:modelValue", value: string): void;
}>();
</script>

<template>
  <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48">
    <circle
      class="background"
      cx="24"
      cy="24"
      r="21.5"
      fill="transparent"
      stroke-width="5"
    />
    <circle
      class="foreground"
      cx="24"
      cy="24"
      r="21.5"
      fill="transparent"
      stroke-dasharray="565.48"
    />
  </svg>
</template>

<style scoped>
.background {
  stroke: var(--color-secondary--06);
}
.foreground {
  stroke: var(--color-primary);
  stroke-dasharray: 100%;
  stroke-dashoffset: 3;
}
</style>
