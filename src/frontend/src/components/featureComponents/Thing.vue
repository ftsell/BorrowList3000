<script setup lang="ts">
import type { ThingDto } from "@/apiClient";
import NameBadge from "@/components/featureComponents/NameBadge.vue";

defineProps<{
  thing: ThingDto;
}>();
</script>

<template>
  <div class="thing">
    <div class="person-container">
      <NameBadge class="name-badge" :name="thing.personName" />
      <span>{{ thing.personName }}</span>
    </div>

    <div class="details-container">
      <span class="details-name">{{ thing.name }}</span>
      <span class="details-description">{{ thing.description }}</span>
    </div>
  </div>
</template>

<style scoped>
.thing {
  display: flex;
  justify-content: space-between;
  gap: 32px;
}

.person-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

.person-container .name-badge {
  width: 48px;
  height: 48px;
}

.details-container {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}

.details-container .details-name {
  font-weight: bold;
}

.details-container .details-description {
  font-weight: lighter;
}
</style>
