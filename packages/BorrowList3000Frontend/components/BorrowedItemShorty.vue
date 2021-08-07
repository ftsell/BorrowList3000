<template>
  <div class="d-flex flex-column align-start">
    <div>
      <span class="text-h5 font-weight-light">{{ item.specifier }}</span>
    </div>

    <div v-if="item.description != null">
      <span class="font-italic">{{ item.description }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "BorrowedItemShorty",
  props: {
    item: {
      type: Object,
      required: true
    }
  }
};
</script>
