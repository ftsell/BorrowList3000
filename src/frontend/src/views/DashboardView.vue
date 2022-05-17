<script setup lang="ts">
import HeaderBar from "@/components/featureComponents/HeaderBar.vue";
import { useListStore } from "@/stores/listStore";
import ThingList from "@/components/featureComponents/ThingList.vue";
import { useTitle } from "@vueuse/core";
import VerticalDivider from "@/components/componentLibrary/VerticalDivider.vue";
import CreateListForm from "@/components/featureComponents/CreateListForm.vue";

useTitle("Thing-People-List");
const listStore = useListStore();
</script>

<template>
  <header>
    <HeaderBar />
  </header>
  <div class="main-container">
    <main>
      <div class="action-bar">
        <CreateListForm />
      </div>

      <VerticalDivider class="space-around" />

      <div class="list-container">
        <ThingList
          v-for="list of listStore.lists"
          :key="list.id"
          :list="list"
        />
      </div>
    </main>
  </div>
</template>

<style scoped>
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

.list-container {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: center;
  gap: 32px;
}

.space-around {
  margin-top: 24px;
  margin-bottom: 24px;
}
</style>
