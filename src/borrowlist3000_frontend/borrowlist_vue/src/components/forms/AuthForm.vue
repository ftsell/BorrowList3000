<template>
  <v-form ref="form" v-model="isValid" @submit.prevent="login">
    <v-container>
      <v-row>
        <v-col>
          <v-text-field
            v-model="formData.username"
            label="Username"
            required
            :rules="usernameRules"
          />
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-text-field
            v-model="formData.password"
            label="Password"
            type="password"
            required
            :rules="passwordRules"
          />
        </v-col>
      </v-row>

      <v-row justify="start" class="d-flex">
        <v-col>
          <v-btn color="primary" outlined type="submit">Login</v-btn>
          <v-btn color="secondary" outlined @click="register">Register</v-btn>
          <v-btn color="secondary" outlined @click="forgotPassword">
            Forgot password
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>

<script lang="ts">
import gql from "graphql-tag";
import { Component, InjectReactive, Vue } from "vue-property-decorator";
import { Alert } from "@/utils";

@Component
export default class AuthForm extends Vue {
  isValid = true;
  formData = {
    username: "",
    password: "",
  };
  usernameRules = [(v: string) => !!v || "Username is required"];
  passwordRules = [(v: string) => !!v || "Password is required"];

  @InjectReactive() alerts!: Alert[];

  get formElement(): any {
    return this.$refs.form as Element;
  }

  async login(): Promise<void> {
    this.formElement.validate();
    if (this.isValid) {
      const result = await this.$apollo.mutate({
        mutation: gql`
          mutation ($username: String!, $password: String!) {
            login(username: $username, password: $password) {
              success
              message
            }
          }
        `,
        variables: {
          username: this.formData.username,
          password: this.formData.password,
        },
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
        this.$emit("onLoggedIn", result.data.login);
      }
    }
  }

  async register(): Promise<void> {
    this.formElement.validate();
    if (this.isValid) {
      const result = await this.$apollo.mutate({
        mutation: gql`
          mutation ($username: String!, $password: String!) {
            register(username: $username, password: $password) {
              success
              message
            }
          }
        `,
        variables: {
          username: this.formData.username,
          password: this.formData.password,
        },
      });

      if (result.data.register.success === false) {
        alert(result.data.register.message);
      } else {
        this.$emit("onRegistered", result.data.register);
        await this.login();
      }
    }
  }

  async forgotPassword(): Promise<void> {
    const result = await this.$apollo.mutate({
      mutation: gql`
        mutation ($username: String!) {
          forgotPassword1(username: $username) {
            message
          }
        }
      `,
      variables: {
        username: this.formData.username,
      },
    });

    this.alerts.push({
      message: result.data.forgotPassword1.message,
    });
  }
}
</script>
