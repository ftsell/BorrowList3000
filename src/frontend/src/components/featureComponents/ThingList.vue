<script setup lang="ts">
import type { ThingListDto } from "@/apiClient";
import Card from "@/components/componentLibrary/Card.vue";
import Thing from "@/components/featureComponents/Thing.vue";
import { useThingStore } from "@/stores/thingStore";
import Icon from "@/components/componentLibrary/Icon.vue";
import editIcon from "@/assets/icons/feather_adjust.svg";
import Popup from "@/components/componentLibrary/Popup.vue";
import { ref } from "vue";
import EditThingList from "@/components/featureComponents/EditThingList.vue";

defineProps<{
  list: ThingListDto;
}>();

const thingStore = useThingStore();

const isEditing = ref(false);
</script>

<template>
  <Card :title="list.name">
    <template v-slot:title-bar>
      <div class="list-action-container">
        <Icon
          class="action-icon"
          :url="editIcon"
          alt="Edit List Name"
          @click="isEditing = !isEditing"
        />
      </div>
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

  <Popup :title="`Edit ${list.name}`" v-model:open="isEditing">
    <EditThingList
      :list="list"
      @finishEdit="isEditing = false"
      @cancelEdit="isEditing = false"
    />
  </Popup>
</template>

<style scoped>
.things-container {
  margin: 16px 0 8px;
  display: flex;
  flex-wrap: wrap;
  column-gap: 128px;
  row-gap: 32px;
}

.list-action-container {
  margin-left: 16px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.list-action-container .action-icon {
  width: 28px;
  height: 28px;
}
</style>
