<template>
  <v-row align="center" class="mx-16">
    <v-col cols="4">
      <create-borrower-form @onBorrowerCreated="$fetch" />
    </v-col>
    <v-col v-for="borrower of user.borrowers" :key="borrower.name" cols="4">
      <borrower-shorty :borrower="borrower" />
    </v-col>
  </v-row>
</template>

<script>
import gql from "graphql-tag";
import BorrowerShorty from "~/components/BorrowerShorty";
import CreateBorrowerForm from "~/components/forms/CreateBorrowerForm";

export default {
  name: "AppIndex",
  components: { CreateBorrowerForm, BorrowerShorty },
  middleware: ["loginRequired"],
  data: () => ({
    user: null
  }),
  async fetch() {
    if (process.server) {
      this.user = await this.$nuxt.context.$db.users.getUserByUsername(this.$nuxt.context.req.session.username, true);
    } else {
      this.user = (await this.$apollo.query({
        query: gql`{
          me {
            username
            borrowers {
              name
              borrowedItems {
                specifier
              }
            }
          }
        }`,
        fetchPolicy: "no-cache"
      })).data.me;
    }
  }
};
</script>
