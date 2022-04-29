<script setup lang="ts">
import type { ThingListDto } from "@/apiClient";
import Card from "@/components/componentLibrary/Card.vue";
import Thing from "@/components/featureComponents/Thing.vue";
import { useThingStore } from "@/stores/thingStore";
import Icon from "@/components/componentLibrary/Icon.vue";
import editIcon from "@/assets/icons/feather_adjust.svg";

defineProps<{
  list: ThingListDto;
}>();

const thingStore = useThingStore();
</script>

<template>
  <Card :title="list.name">
    <template v-slot:title-bar>
      <Icon :url="editIcon" alt="Edit List Name" />
    </template>
    <template v-slot:default>
      <div class="things-container">
        <Thing
          v-for="thing of thingStore.getThingsForList(list.name)"
          :key="thing.name"
          :thing="thing"
        />
      </div>
    </template>
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
