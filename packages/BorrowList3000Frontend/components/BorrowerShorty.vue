<template>
  <v-card shaped outlined>
    <v-card-text>
      <div class='d-flex align-center'>
        <borrower-profile-picture class='mr-4' />

        <div class='mr-8'>
          <span class='text-capitalize text-h3 font-weight-light'>{{ borrower.name }}</span>
        </div>

        <div>
          <span v-if='borrower.borrowedItems.length > 0' class='font-italic'>{{ borrower.borrowedItems.length }} items borrowed</span>
          <span v-else class='font-italic'>Nothing borrowed</span>
        </div>
      </div>
    </v-card-text>

    <v-card-actions>
      <v-spacer />
      <v-btn icon>
        <v-icon @click='onDelete'>mdi-delete</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import gql from 'graphql-tag'
import BorrowerProfilePicture from '~/components/BorrowerProfilePicture'

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
  components: { BorrowerProfilePicture },
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
