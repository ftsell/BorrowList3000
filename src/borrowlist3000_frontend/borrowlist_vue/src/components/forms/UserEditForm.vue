<template>
  <div v-if="user != null">
    <v-form ref="form" :disabled="!editable" @submit.prevent="onFormSubmit">
      <v-container>
        <!-- Form Fields -->
        <v-row>
          <v-text-field v-model="user.username" label="Username" />
        </v-row>
        <v-row>
          <v-text-field
            v-model="user.password"
            label="Password"
            type="password"
          />
        </v-row>
        <v-row v-if="appConfig.emailEnabled">
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

<script lang="ts">
import { Component, InjectReactive, Vue } from "vue-property-decorator";
import gql from "graphql-tag";

@Component({
  apollo: {
    user: gql`
      query {
        user: me {
          id
          username
          email
        }
      }
    `,
  },
})
export default class UserEditForm extends Vue {
  @InjectReactive() appConfig!: any;
  user: any | null = null;
  editable = false;

  get formElement(): any {
    return this.$refs.form;
  }

  async onFormSubmit(): Promise<void> {
    if (this.formElement.validate()) {
      await this.$apollo.mutate({
        mutation: gql`
          mutation ($email: String, $password: String, $username: String) {
            alterUser(
              user: { email: $email, password: $password, username: $username }
            ) {
              user {
                id
                username
                email
              }
            }
          }
        `,
        variables: {
          username: this.user.username,
          password: this.user.password,
          email: this.user.email,
        },
      });

      this.editable = false;
    }
  }
}
</script>
