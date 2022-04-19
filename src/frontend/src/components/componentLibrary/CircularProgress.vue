<!-- See https://css-tricks.com/building-progress-ring-quickly/ for explanation -->

<script setup lang="ts">
import { computed, watchEffect } from "vue";

const SVG_SIZE = 48;
const STROKE_WIDTH = 4;

const props = defineProps<{
  progress?: number;
}>();

defineEmits<{
  (e: "update:progress", value: string): void;
}>();

const circumference = computed(() => (SVG_SIZE - STROKE_WIDTH * 2) * Math.PI);
const circumferenceProgress = computed(
  () => circumference.value - (props.progress ?? 0) * circumference.value
);
</script>

<template>
  <svg xmlns="http://www.w3.org/2000/svg" :width="SVG_SIZE" :height="SVG_SIZE">
    <circle
      class="background"
      :cx="SVG_SIZE / 2"
      :cy="SVG_SIZE / 2"
      :r="SVG_SIZE / 2 - STROKE_WIDTH"
      :stroke-width="STROKE_WIDTH"
      fill="transparent"
    />
    <circle
      class="foreground"
      :cx="SVG_SIZE / 2"
      :cy="SVG_SIZE / 2"
      :r="SVG_SIZE / 2 - STROKE_WIDTH"
      :stroke-width="STROKE_WIDTH"
      fill="transparent"
    />
  </svg>
</template>

<style scoped>
.background {
  stroke: var(--color-secondary--06);
}

.foreground {
  stroke: var(--color-primary);
  stroke-dasharray: v-bind(circumference) v-bind(circumference);
  stroke-dashoffset: v-bind(circumferenceProgress);
  transition: stroke-dashoffset 0.35s;

  transform: rotate(-90deg);
  transform-origin: 50% 50%;
}
</style>
