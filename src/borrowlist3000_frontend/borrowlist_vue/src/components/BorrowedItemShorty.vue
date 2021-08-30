<template>
  <div class="d-flex flex-row">
    <!-- Actions -->
    <div class="d-flex flex-column align-start mr-2">
      <v-tooltip bottom>
        <span>Mark as returned</span>
        <template #activator="{ on }">
          <v-btn icon v-on="on" @click="onReturnClicked">
            <v-icon>mdi-keyboard-return</v-icon>
          </v-btn>
        </template>
      </v-tooltip>
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

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import gql from "graphql-tag";

@Component({})
export default class BorrowedItemShorty extends Vue {
  @Prop({ required: true, type: Object }) readonly item!: any;

  async onReturnClicked(): Promise<void> {
    await this.$apollo.mutate({
      mutation: gql`
        mutation ($itemId: UUID!) {
          returnBorrowedItem(id: $itemId) {
            success
          }
        }
      `,
      variables: {
        itemId: this.item.id,
      },
    });
  }
}
</script>
