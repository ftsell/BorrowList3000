<template>
  <v-col class="d-flex align-center">
    <borrower-profile-picture class="mr-4" />

    <div class="mr-8">
      <span class="text-capitalize text-h3 font-weight-light">{{ borrower.name }}</span>
    </div>

    <div>
      <span v-if="borrower.borrowedItems.length > 0" class="font-italic">{{ borrower.borrowedItems.length }} items borrowed</span>
      <span v-else class="font-italic">Nothing borrowed</span>
    </div>
  </v-col>
</template>

<script>
import BorrowerProfilePicture from "~/components/BorrowerProfilePicture";

export default {
  name: "BorrowerShorty",
  components: { BorrowerProfilePicture },
  props: ["borrower"]
};
</script>
