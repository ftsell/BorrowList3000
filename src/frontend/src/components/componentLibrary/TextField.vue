<script setup lang="ts">
defineProps<{
  id: string;
  type: string;
  label?: string;
  placeholder?: string;
  tabIndex?: number | string;
  modelValue?: string;
}>();
defineEmits<{
  (e: "update:modelValue", value: string): void;
}>();
</script>

<template>
  <div class="input-container">
    <label v-if="label" for="id">{{ label }}</label>
    <input
      :id="id"
      :type="type"
      :placeholder="placeholder"
      :tabindex="tabIndex"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
    />
  </div>
</template>

<style scoped>
.input-container {
  position: relative;
  margin-bottom: 16px;
}

input {
  width: 100%;
  padding: 0.6em;
  border-radius: 2px;
  border: 2px solid var(--color-secondary--06);
  transition: border-color 0.1s;
}

label ~ input {
  padding-top: 0.8em;
}

input:focus {
  border-color: var(--color-secondary);
  outline: none;
}

input::placeholder {
  color: var(--color-secondary-alt);
}

label {
  color: var(--color-secondary);
  font-size: 0.9em;
  position: absolute;
  left: 1em;
  top: -0.5em;
  padding: 0 0.5em;
}
</style>
