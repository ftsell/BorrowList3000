<script setup lang="ts">
import Avatar from "vue-boring-avatars";

defineProps<{
  name: string;
}>();
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
