<template>
  <v-card shaped outlined>
    <v-card-text>
      <div class="d-flex align-center">
        <borrower-profile-picture class="mr-4" />

        <div class="mr-8">
          <span class="text-capitalize text-h3 font-weight-light">{{ borrower.name }}</span>
        </div>

        <div>
          <span v-if="borrower.borrowedItems.length > 0" class="font-italic">{{ borrower.borrowedItems.length }} items borrowed</span>
          <span v-else class="font-italic">Nothing borrowed</span>
        </div>
      </div>
    </v-card-text>

    <v-card-actions>
      <v-spacer />
      <nuxt-link :to="{query: {borrower: borrower.name}}">
        <v-btn icon>
          <v-icon>mdi-open-in-app</v-icon>
        </v-btn>
      </nuxt-link>
      <v-btn icon>
        <v-icon @click="onDelete">mdi-delete</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import BorrowerProfilePicture from "~/components/BorrowerProfilePicture";

export default {
  name: "BorrowerShorty",
  components: { BorrowerProfilePicture },
  props: ["borrower"],
  methods: {
    onDelete() {
      // TODO Implement borrower deletion
      throw new Error("not yet implemented");
    }
  }
};
</script>
