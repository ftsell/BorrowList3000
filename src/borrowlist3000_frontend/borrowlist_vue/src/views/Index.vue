<template>
  <app-layout>
    <div v-if="user != null">
      <!-- Normal page content -->
      <v-row class="mx-16">
        <v-col cols="12">
          <smart-form
            @borrowerAdded="refreshData"
            @borrowedItemAdded="refreshData"
          />
        </v-col>
        <v-col
          v-for="borrower of user.borrowers"
          :key="borrower.name"
          cols="12"
        >
          <borrower-shorty
            :borrower="borrower"
            @borrowerDeleted="refreshData"
            @itemReturned="refreshData"
          />
        </v-col>
      </v-row>
    </div>
  </app-layout>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import gql from "graphql-tag";
import AppLayout from "@/layouts/app.vue";
import BorrowerShorty from "@/components/BorrowerShorty.vue";
import SmartForm from "@/components/forms/SmartForm.vue";

@Component({
  components: { AppLayout, SmartForm, BorrowerShorty },
  apollo: {
    user: gql`
      query {
        user: me {
          email
          username
          id
          borrowers {
            id
            name
            borrowedItems {
              id
              specifier
              description
              dateBorrowed
            }
          }
        }
      }
    `,
  },
})
export default class Index extends Vue {
  user: any;

  async refreshData(): Promise<void> {
    await this.$apollo.queries.user.refetch();
  }
}
</script>
