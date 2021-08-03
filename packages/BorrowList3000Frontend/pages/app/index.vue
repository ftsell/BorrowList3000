<template>
  <div v-if="user != null">
    <!-- Normal page content -->
    <v-row align="center" class="mx-16">
      <v-col cols="4">
        <create-borrower-form @onBorrowerCreated="$fetch" />
      </v-col>
      <v-col v-for="borrower of user.borrowers" :key="borrower.name" cols="4">
        <borrower-shorty :borrower="borrower" />
      </v-col>
    </v-row>

    <!-- Show Borrower dialog -->
    <v-dialog
      :value="showBorrower != null"
      @input="(value) => showBorrower = value"
      max-width="60vw"
    >
      <v-card v-if="showBorrower != null">
        <v-card-title class="text-h2 font-weight-light">{{ showBorrower.name }}</v-card-title>
        <v-card-text>
          <borrower-details :borrower="showBorrower" />
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { omit } from "lodash";
import gql from "graphql-tag";
import BorrowerShorty from "~/components/BorrowerShorty";
import CreateBorrowerForm from "~/components/forms/CreateBorrowerForm";
import BorrowerDetails from "~/components/BorrowerDetails";

export default {
  name: "AppIndex",
  components: { BorrowerDetails, CreateBorrowerForm, BorrowerShorty },
  middleware: ["loginRequired"],
  data: () => ({
    user: null
  }),
  computed: {
    showBorrower: {
      get() {
        if (this.$route.query.borrower != null || this.user != null) {
          return this.user.borrowers.find(b => b.name === this.$route.query.borrower);
        }
        return null;
      },
      set(value) {
        if (!value) {
          this.$router.push({
            query: omit(this.$route.query, ["borrower"])
          });
        }
      }
    }
  },
  async fetch() {
    if (process.server) {
      this.user = (await this.$nuxt.context.$db.users.getUserByUsername(this.$nuxt.context.req.session.username, true)).toJSON();
    } else {
      this.user = (await this.$apollo.query({
        query: gql`{
          me {
            username
            borrowers {
              name
              borrowedItems {
                specifier
                description
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
