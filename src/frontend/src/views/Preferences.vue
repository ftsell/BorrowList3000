<script lang="ts" setup>
import HeaderBar from "@/components/featureComponents/HeaderBar.vue";
import { useTitle } from "@vueuse/core";

useTitle("Preferences");
</script>

<template>
  <header>
    <h1>Preferences</h1>
    <HeaderBar />
  </header>
  <div class="main-container">
    <main>
      <!-- Implement preferences -->
      <p>Nothing here yet</p>
    </main>
  </div>
</template>

<style scoped>
header {
  padding-left: 8px;
}

.main-container {
  display: flex;
  justify-content: center;
  min-height: 100vh;
}

.main-container > * {
  flex-grow: 1;
  max-width: max(75%, 512px);
  padding: 16px;
}
</style>
