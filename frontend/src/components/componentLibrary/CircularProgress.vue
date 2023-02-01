<!-- See https://css-tricks.com/building-progress-ring-quickly/ for explanation -->

<script setup lang="ts">
import { computed } from "vue";

const props = defineProps({
  size: { type: Number, default: 48 },
  strokeWidth: { type: Number, default: 4 },
  progress: { type: Number, default: 0 },
  indeterminate: { type: Boolean, default: false },
});

defineEmits<{
  (e: "update:progress", value: string): void;
}>();

const circumference = computed(
  () => (props.size - props.strokeWidth * 2) * Math.PI
);
const circumferenceProgress = computed(
  () => circumference.value - (props.progress ?? 0) * circumference.value
);

const cssClasses = computed(() => ({
  indeterminate: props.indeterminate,
}));
</script>

<template>
  <svg
    xmlns="http://www.w3.org/2000/svg"
    :class="cssClasses"
    :width="size"
    :height="size"
  >
    <circle
      class="background"
      :cx="size / 2"
      :cy="size / 2"
      :r="size / 2 - strokeWidth"
      :stroke-width="strokeWidth"
      fill="transparent"
    />
    <circle
      class="foreground"
      :cx="size / 2"
      :cy="size / 2"
      :r="size / 2 - strokeWidth"
      :stroke-width="strokeWidth"
      fill="transparent"
    />
  </svg>
</template>

<style scoped>
svg {
  display: inline-block;
}

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
