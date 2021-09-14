<template>
  <div>
    <v-tabs v-model="currentTab">
      <v-tab>Login</v-tab>
      <v-tab>Register</v-tab>
      <v-tab>Forgot Password</v-tab>
    </v-tabs>
    <v-tabs-items v-model="currentTab">
      <v-tab-item>
        <username-password-form
          @submit="doLogin"
          submit-text="Login"
          explanation="To login to an existing account, enter your username and password."
        />
      </v-tab-item>
      <v-tab-item>
        <username-password-form
          @submit="doRegister"
          submit-text="Register"
          explanation="To create a new account, enter your desired username and password."
        />
      </v-tab-item>
      <v-tab-item>
        <username-form
          submit-text="Reset Password"
          explanation="If you forgot your password, you can start a reset by entering your username here. An email with additional instructions will be sent to your configured email address."
          @submit="doForgotPassword"
        />
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script lang="ts">
import gql from "graphql-tag";
import { Component, InjectReactive, Vue } from "vue-property-decorator";
import UsernamePasswordForm from "@/components/forms/UsernamePasswordForm.vue";
import UsernameForm from "@/components/forms/UsernameForm.vue";
import { Alert } from "@/utils";

@Component({
  components: { UsernameForm, UsernamePasswordForm },
})
export default class AuthForm extends Vue {
  currentTab = 0;

  @InjectReactive() alerts!: Alert[];

  async doLogin(data: { username: string; password: string }): Promise<void> {
    const result = await this.$apollo.mutate({
      mutation: gql`
        mutation ($username: String!, $password: String!) {
          login(username: $username, password: $password) {
            success
            message
          }
        }
      `,
      variables: data,
    });

    if (result.data.login.success === false) {
      this.alerts.push({
        message: result.data.login.message,
        type: "error",
      });
    } else {
      // perform cache-busting
      await this.$apollo.getClient().resetStore();

      await this.$apollo.query({
        query: gql`
          {
            loggedIn
          }
        `,
        fetchPolicy: "network-only",
      });

      // finalize
      this.$emit("loggedIn", result.data.login);
    }
  }

  async doRegister(data: {
    username: string;
    password: string;
  }): Promise<void> {
    const result = await this.$apollo.mutate({
      mutation: gql`
        mutation ($username: String!, $password: String!) {
          register(username: $username, password: $password) {
            success
            message
          }
        }
      `,
      variables: data,
    });

    if (result.data.register.success === false) {
      this.alerts.push({
        message: result.data.register.message,
        type: "error",
      });
    } else {
      this.$emit("registered", result.data.register);
      await this.doLogin(data);
    }
  }

  async doForgotPassword(data: { username: string }): Promise<void> {
    const result = await this.$apollo.mutate({
      mutation: gql`
        mutation ($username: String!) {
          forgotPassword1(username: $username) {
            success
            message
          }
        }
      `,
      variables: data,
    });

    this.alerts.push({
      message: result.data.forgotPassword1.message,
      type: result.data.forgotPassword1.success ? "success" : "error",
    });
  }
}
</script>
