<script setup lang="ts">
import { ref } from "vue";
import type { ThingListDto } from "@/apiClient";
import Card from "@/components/componentLibrary/Card.vue";
import Thing from "@/components/featureComponents/Thing.vue";
import { useThingStore } from "@/stores/thingStore";
import Icon from "@/components/componentLibrary/Icon.vue";
import Popup from "@/components/componentLibrary/Popup.vue";
import EditThingList from "@/components/featureComponents/EditThingList.vue";
import editIcon from "mono-icons/svg/edit.svg?raw";
import addIcon from "mono-icons/svg/add.svg?raw";
import deleteIcon from "mono-icons/svg/delete.svg?raw";
import { useListStore } from "@/stores/listStore";
import CreateThing from "@/components/featureComponents/CreateThing.vue";

const props = defineProps<{
  list: ThingListDto;
}>();

const thingStore = useThingStore();
const listStore = useListStore();

const isEditing = ref(false);
const isCreatingThing = ref(false);

async function onDelete() {
  if (
    confirm(
      `Are you sure you want to delete ${props.list.name} and all its things?`
    )
  ) {
    await listStore.deleteList(props.list.id);
  }
}
</script>

<template>
  <Card :title="list.name">
    <template v-slot:title-bar>
      <div class="list-action-container">
        <!-- Delete -->
        <Icon class="action-icon" :raw-svg="deleteIcon" @click="onDelete" />

        <!-- Edit -->
        <Icon
          class="action-icon"
          :raw-svg="editIcon"
          @click="isEditing = !isEditing"
        />

        <!-- Add Thing -->
        <Icon
          class="action-icon"
          :raw-svg="addIcon"
          @click="isCreatingThing = !isCreatingThing"
        />
      </div>
    </template>

    <template v-slot:default>
      <div class="things-container">
        <Thing
          v-for="thing of thingStore.getThingsForList(list.id)"
          :key="thing.id"
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

  <Popup title="Create new Thing" v-model:open="isCreatingThing">
    <CreateThing
      :list="list"
      @thingCreated="isCreatingThing = false"
      @canceled="isCreatingThing = false"
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
