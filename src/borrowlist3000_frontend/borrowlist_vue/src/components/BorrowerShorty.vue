<template>
  <v-card shaped outlined>
    <v-card-text>
      <div class="d-flex align-start flex-row">
        <borrower-profile-picture class="mr-4" />

        <div class="mr-8 mt-1">
          <span class="text-capitalize text-h3 font-weight-light">{{
            borrower.name
          }}</span>
        </div>

        <div class="flex-grow-1">
          <span v-if="borrower.borrowedItems.length === 0" class="font-italic"
            >Nothing borrowed</span
          >
          <v-row v-else>
            <v-col
              v-for="item of borrower.borrowedItems"
              :key="item.id"
              cols="4"
            >
              <borrowed-item-shorty :item="item" v-on="$listeners" />
            </v-col>
          </v-row>
        </div>

        <v-tooltip bottom>
          <span>Delete</span>
          <template #activator="{ on }">
            <v-btn icon>
              <v-icon v-on="on" @click="onDelete">mdi-delete</v-icon>
            </v-btn>
          </template>
        </v-tooltip>
      </div>
    </v-card-text>
  </v-card>
</template>

<script lang="ts">
import { Component, Vue, Prop } from "vue-property-decorator";
import gql from "graphql-tag";
import BorrowerProfilePicture from "@/components/BorrowerProfilePicture.vue";
import BorrowedItemShorty from "@/components/BorrowedItemShorty.vue";

@Component({
  components: { BorrowedItemShorty, BorrowerProfilePicture },
})
export default class BorrowerShorty extends Vue {
  @Prop({ required: true, type: Object }) readonly borrower!: any;

  async onDelete(): Promise<void> {
    await this.$apollo.mutate({
      mutation: gql`
        mutation ($id: UUID!) {
          deleteBorrower(id: $id) {
            success
          }
        }
      `,
      variables: {
        id: this.borrower.id,
      },
    });

    this.$emit("borrowerDeleted");
  }
}
</script>
