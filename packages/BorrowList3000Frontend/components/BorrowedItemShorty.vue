<template>
  <div class='d-flex flex-row'>
    <!-- Actions -->
    <div class='d-flex flex-column align-start mr-2'>
      <v-tooltip bottom>
        <span>Mark as returned</span>
        <template #activator='{ on }'>
          <v-btn icon v-on='on' @click='onReturnClicked'>
            <v-icon>mdi-keyboard-return</v-icon>
          </v-btn>
        </template>
      </v-tooltip>
    </div>

    <!-- Data display -->
    <div class='d-flex flex-column align-start'>
      <div>
        <span class='text-h5 font-weight-light'>{{ item.specifier }}</span>
      </div>

      <div v-if='item.description != null'>
        <span class='font-italic'>{{ item.description }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import gql from 'graphql-tag'

const GRAPHQL_QUERIES = {
  returnItem: gql`mutation ($itemId: String!) {
        returnBorrowedItem(id: $itemId) {
            message
            success
        }
    }`
}

export default {
  name: 'BorrowedItemShorty',
  props: {
    item: {
      type: Object,
      required: true
    }
  },
  methods: {
    async onReturnClicked() {
      const result = await this.$apollo.mutate({
        mutation: GRAPHQL_QUERIES.returnItem,
        variables: {
          itemId: this.item.id
        }
      })

      if (result.data.returnBorrowedItem.success === true) {
        this.$store.commit('removeBorrowedItem', {id: this.item.id})
      }
    }
  }
}
</script>
