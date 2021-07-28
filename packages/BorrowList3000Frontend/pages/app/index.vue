<template>
  <div>
    <div>{{ user }}</div>
    <v-btn @click="$fetch">Reload</v-btn>
  </div>
</template>

<script>
import gql from "graphql-tag";

export default {
  name: "AppIndex",
  middleware: ["loginRequired"],
  data: () => ({
    user: null
  }),
  async fetch() {
    if (process.server) {
      this.user = await this.$nuxt.context.$db.users.getUserByUsername(this.$nuxt.context.req.session.username)
    } else {
      this.user = (await this.$apollo.query({query: gql`{
        me {
          username
          borrowers {
            name
            borrowedItems {
              specifier
              description
              dateBorrowed
            }
          }
        }
      }`})).data.me
    }
  },
};
</script>
