<script setup lang="ts">
import NameBadge from "@/components/featureComponents/NameBadge.vue";
import { useUserStore } from "@/stores/userStore";

const userStore = useUserStore();
</script>

<template>
  <div v-if="userStore.me" class="profile-badge">
    <RouterLink
      :to="{ name: 'preferences' }"
      class="horizontal-container a-unstyle"
    >
      <h4 class="no-select">Logged in</h4>
      <NameBadge :name="userStore.me?.id" />
    </RouterLink>
  </div>
</template>

<style scoped>
.profile-badge {
  position: absolute;
  top: 0;
  right: 0;
  margin: 12px;
}

.horizontal-container {
  display: flex;
  align-items: center;
  gap: 16px;
}

.a-unstyle {
  text-decoration: none;
}
</style>
