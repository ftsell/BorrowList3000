<template>
  <div v-if='user != null'>
    <!-- Normal page content -->
    <v-row class='mx-16'>
      <v-col cols='12'>
        <smart-form />
      </v-col>
      <v-col v-for='borrower of user.borrowers' :key='borrower.name' cols='12'>
        <borrower-shorty :borrower='borrower' />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import gql from 'graphql-tag'
import BorrowerShorty from '~/components/BorrowerShorty'
import SmartForm from '~/components/forms/SmartForm'

export default {
  name: 'AppIndex',
  layout: "app",
  components: { SmartForm, BorrowerShorty },
  middleware: ['loginRequired'],
  computed: {
    user: {
      get() {
        return this.$store.state.user
      },
      set(user) {
        this.$store.commit('updateUser', user)
      }
    },
  },
  async fetch() {
    if (!process.server) {
      this.user = (await this.$apollo.query({
        query: gql`{
          me {
            username
            borrowers {
              name
              borrowedItems {
                id
                specifier
                description
                dateBorrowed
              }
            }
          }
        }`,
        fetchPolicy: 'no-cache'
      })).data.me
    }
  }
}
</script>
