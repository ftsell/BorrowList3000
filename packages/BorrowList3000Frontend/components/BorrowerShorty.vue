<template>
  <v-card shaped outlined>
    <v-card-text>
      <div class='d-flex align-start flex-row'>
        <borrower-profile-picture class='mr-4' />

        <div class='mr-8 mt-1'>
          <span class='text-capitalize text-h3 font-weight-light'>{{ borrower.name }}</span>
        </div>

        <div class='flex-grow-1'>
          <span v-if='borrower.borrowedItems.length === 0' class='font-italic'>Nothing borrowed</span>
          <v-row v-else>
            <v-col v-for='item of borrower.borrowedItems' :key='item.id' cols='4'>
              <borrowed-item-shorty :item='item' />
            </v-col>
          </v-row>
        </div>

        <v-tooltip bottom>
          <span>Delete</span>
          <template #activator='{ on }'>
            <v-btn icon>
              <v-icon v-on='on' @click='onDelete'>mdi-delete</v-icon>
            </v-btn>
          </template>
        </v-tooltip>
      </div>
    </v-card-text>
  </v-card>
</template>

<script>
import gql from 'graphql-tag'
import BorrowerProfilePicture from '~/components/BorrowerProfilePicture'
import BorrowedItemShorty from '~/components/BorrowedItemShorty'

const GRAPHQL_QUERIES = {
  deleteBorrower: gql`mutation($name: String!) {
        deleteBorrower(name: $name) {
            success
            message
        }
    }`
}

export default {
  name: 'BorrowerShorty',
  components: { BorrowedItemShorty, BorrowerProfilePicture },
  props: {
    borrower: {
      type: Object,
      required: true
    },
  },
  methods: {
    async onDelete() {
      const result = await this.$apollo.mutate({
        mutation: GRAPHQL_QUERIES.deleteBorrower,
        variables: {
          name: this.borrower.name
        }
      })

      if (result.data.deleteBorrower.success) {
        this.$store.commit("deleteBorrower", this.borrower.name)
      }
    }
  }
}
</script>
