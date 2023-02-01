<script setup lang="ts">
import { computed } from "vue";

const props = withDefaults(
  defineProps<{
    text: string;
    type?: "button" | "submit" | "reset";
    outlined?: boolean;
  }>(),
  {
    type: "button",
    outlined: false,
  }
);

defineEmits<{
  (e: "click"): void;
}>();

const cssClasses = computed(() => {
  return {
    outlined: Boolean(props.outlined),
  };
});
</script>

<template>
  <button :type="type" @click="$emit('click')" :class="cssClasses">
    {{ text }}
  </button>
</template>

<style scoped>
button {
  padding: 0.4em 0.8em;
  border-radius: 4px;
  font-size: large;
}

button:focus {
  outline: 0.15em solid var(--color-primary);
}

button:not(.outlined) {
  background-color: var(--color-primary);
  color: var(--color-background);
  border: solid 1px var(--color-background);
}

button.outlined {
  border: solid 2px var(--color-primary);
  color: var(--color-primary);
}
</style>
