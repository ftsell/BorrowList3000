<script setup lang="ts">
import type { ThingListDto } from "@/apiClient";
import Card from "@/components/componentLibrary/Card.vue";
import Thing from "@/components/featureComponents/Thing.vue";
import { useThingStore } from "@/stores/thingStore";

defineProps<{
  list: ThingListDto;
}>();

const thingStore = useThingStore();
</script>

<template>
  <Card :title="list.name">
    <div class="things-container">
      <Thing
        v-for="thing of thingStore.getThingsForList(list.name)"
        :key="thing.name"
        :thing="thing"
      />
    </div>
  </Card>
</template>

<style scoped>
.things-container {
  margin: 16px 0 8px;
  display: flex;
  flex-wrap: wrap;
  column-gap: 128px;
  row-gap: 32px;
}
</style>
