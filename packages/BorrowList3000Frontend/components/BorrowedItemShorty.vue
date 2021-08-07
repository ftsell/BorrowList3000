<template>
  <div class='d-flex flex-row'>
    <!-- Actions -->
    <div class='d-flex flex-column align-start mr-2'>
      <v-btn @click='onReturnClicked' icon>
        <v-icon>mdi-keyboard-return</v-icon>
      </v-btn>
    </div>

    <!-- Data display -->
    <div class="d-flex flex-column align-start">
      <div>
        <span class="text-h5 font-weight-light">{{ item.specifier }}</span>
      </div>

      <div v-if="item.description != null">
        <span class="font-italic">{{ item.description }}</span>
      </div>
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
  },
  methods: {
    onReturnClicked() {
      throw new Error("not yet implemented")
    }
  }
};
</script>
