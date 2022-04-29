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
    <Thing
      v-for="thing of thingStore.getThingsForList(list.name)"
      :key="thing.name"
      :thing="thing"
    />
  </Card>
</template>

<style scoped></style>
