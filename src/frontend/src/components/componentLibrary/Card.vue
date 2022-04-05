<script setup lang="ts"></script>

<template>
  <div class="card">
    <slot />
  </div>
</template>

<style scoped>
.card {
  padding: 8px;
  border-radius: 4px;
  box-shadow: 0 0 16px 8px rgba(70, 137, 102, 0.1),
    0.5px 0.5px 4px 1px var(--color-secondary-alt);
}
</style>
