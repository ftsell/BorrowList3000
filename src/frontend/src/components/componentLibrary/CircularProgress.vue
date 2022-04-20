<!-- See https://css-tricks.com/building-progress-ring-quickly/ for explanation -->

<script setup lang="ts">
import { computed } from "vue";

const SVG_SIZE = 48;
const STROKE_WIDTH = 4;

const props = defineProps<{
  progress?: number;
  indeterminate?: boolean;
}>();

defineEmits<{
  (e: "update:progress", value: string): void;
}>();

const circumference = computed(() => (SVG_SIZE - STROKE_WIDTH * 2) * Math.PI);
const circumferenceProgress = computed(
  () => circumference.value - (props.progress ?? 0) * circumference.value
);

const cssClasses = computed(() => ({
  indeterminate: props.indeterminate ?? false,
}));
</script>

<template>
  <svg
    xmlns="http://www.w3.org/2000/svg"
    :class="cssClasses"
    :width="SVG_SIZE"
    :height="SVG_SIZE"
  >
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

svg.indeterminate .foreground {
  animation: ease-in-out infinite alternate indeterminate-progress 1.5s,
    linear infinite indeterminate-rotation 1s;
}

@keyframes indeterminate-progress {
  from {
    stroke-dashoffset: 0;
  }
  to {
    stroke-dashoffset: v-bind(circumference);
  }
}

@keyframes indeterminate-rotation {
  from {
    transform: rotate(-90deg);
  }
  to {
    transform: rotate(+270deg);
  }
}
</style>
