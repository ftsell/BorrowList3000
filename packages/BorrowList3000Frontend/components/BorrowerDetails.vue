<template>
  <div>
    <create-borrowed-item-form :borrower-name="borrower.name" />

    <borrowed-item-shorty
      v-for="item of borrower.borrowedItems"
      :key="item.id"
      :item="item"
    />
  </div>
</template>

<script>
import BorrowedItemShorty from "~/components/BorrowedItemShorty";
import CreateBorrowedItemForm from "~/components/forms/CreateBorrowedItemForm";

export default {
  name: "BorrowerDetails",
  components: { CreateBorrowedItemForm, BorrowedItemShorty },
  props: {
    borrower: Object,
    default: null
  }
};
</script>
