<template>
  <v-col class="d-flex flex-column align-start">
    <div>
      <span class="text-h3 font-weight-light">{{ item.specifier }}</span>
    </div>

    <div v-if="item.description != null">
      <span class="font-italic">{{ item.description }}</span>
    </div>

    <div>
      <span class="font-italic font-weight-light">{{ item.dateBorrowed }}</span>
    </div>
  </v-col>
</template>

<script>
export default {
  name: "BorrowedItemShorty",
  props: {
    item: Object,
    default: null
  }
};
</script>
