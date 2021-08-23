<template>
  <div v-if="!$apollo.loading" class="mx-16">
    <v-form ref="form" :disabled="!editable" @submit.prevent="onFormSubmit">
      <v-container>
        <!-- Form Fields -->
        <v-row>
          <v-text-field v-model="user.username" label="Username" disabled />
        </v-row>
        <v-row>
          <v-text-field
            v-model="user.password"
            label="Password"
            type="password"
          />
        </v-row>
        <v-row v-if="isEmailEnabled">
          <v-text-field v-model="user.email" label="EMail address" />
        </v-row>

        <!-- Form Actions -->
        <v-row class="d-flex flex-row">
          <v-btn v-if="editable" class="mr-4" color="primary" type="submit"
            >Save
          </v-btn>
          <v-btn :outlined="editable" @click="editable = !editable">
            <span v-if="!editable">Edit</span>
            <span v-else>Cancel</span>
          </v-btn>
        </v-row>
      </v-container>
    </v-form>
  </div>
</template>

<script>
import gql from "graphql-tag";

export default {
  name: "Settings",
  layout: "app",
  middleware: ["loginRequired"],
  data: () => ({
    user: null,
    editable: false,
  }),
  computed: {
    isEmailEnabled() {
      return this.$config.emailEnabled
    }
  },
  apollo: {
    user: gql`
      query {
        user: me {
          username
          email
        }
      }
    `,
  },
  methods: {
    async onFormSubmit() {
      if (this.$refs.form.validate()) {
        const result = await this.$apollo.mutate({
          mutation: gql`
            mutation($email: String, $password: String) {
              alterUser(user: { email: $email, password: $password }) {
                username
                email
              }
            }
          `,
          variables: this.user,
        });

        this.user = result.data.alterUser;
        this.editable = false;
      }
    },
  },
};
</script>
