<script setup lang="ts">
import Avatar from "vue-boring-avatars";
import { withDefaults } from "vue";

withDefaults(
  defineProps<{
    name?: string;
  }>(),
  {
    name: "",
  }
);
</script>

<template>
  <Avatar class="avatar" :name="name" variant="beam" />
</template>

<style scoped>
.avatar {
  height: 64px;
  width: 64px;
}
</style>
