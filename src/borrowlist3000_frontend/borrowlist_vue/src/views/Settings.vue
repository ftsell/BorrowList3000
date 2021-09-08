<template>
  <app-layout>
    <user-edit-form />

    <v-spacer class="my-8" />

    <v-container>
      <v-row>
        <v-btn color="red" outlined @click="onDeleteClicked"
          >Delete Account
        </v-btn>
      </v-row>
    </v-container>
  </app-layout>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import gql from "graphql-tag";
import AppLayout from "@/layouts/app.vue";
import UserEditForm from "@/components/forms/UserEditForm.vue";

@Component({
  components: { AppLayout, UserEditForm },
})
export default class Settings extends Vue {
  async onDeleteClicked(): Promise<void> {
    if (
      confirm(
        "You are about to delete your account with all your data. Are you sure?"
      )
    ) {
      await this.$apollo.mutate({
        mutation: gql`
          mutation {
            deleteAccount {
              success
            }
          }
        `,
      });

      await this.$apollo.getClient().resetStore();
      await this.$router.push({ name: "Auth" });
    }
  }
}
</script>
