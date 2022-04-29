<script setup lang="ts">
import Card from "@/components/componentLibrary/Card.vue";

defineProps<{
  title: string;
  open: boolean;
}>();

const emit = defineEmits<{
  (e: "update:open", value: boolean): void;
}>();

function onBackgroundClick(e: Event): void {
  // only close the dialog if the exact element (background) was clicked
  // but not if the dialog content was clicked
  if (e.target === e.currentTarget) {
    emit("update:open", false);
  }
}
</script>

<template>
  <teleport to="body">
    <div v-if="open" class="popup-container" @click="onBackgroundClick">
      <Card class="popup" :title="title">
        <slot />
      </Card>
    </div>
  </teleport>
</template>

<style scoped>
.popup-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;

  display: flex;
  justify-content: center;
  align-items: center;

  background: rgba(242, 245, 248, 70%);
}

.popup {
  max-width: 512px;
}
</style>
