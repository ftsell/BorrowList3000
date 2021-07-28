<template>
  <v-form ref="form" v-model="isValid" @submit="login">
    <v-container>
      <v-row>
        <v-col>
          <v-text-field v-model="formData.username" label="Username" required :rules="usernameRules" />
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-text-field v-model="formData.password" label="Password" type="password" required
                        :rules="passwordRules" />
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="1" class="mr-4">
          <v-btn @click="login" color="primary" outlined>Login</v-btn>
        </v-col>
        <v-col cols="1">
          <v-btn @click="register" color="secondary" outlined>Register</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>

<script>
import gql from "graphql-tag";

export default {
  name: "AuthForm",
  data: () => ({
    isValid: true,
    formData: {
      username: "",
      password: ""
    },
    usernameRules: [
      v => !!v || "Username is required"
    ],
    passwordRules: [
      v => !!v || "Password is required"
    ]
  }),
  methods: {
    async login() {
      this.$refs.form.validate();
      if (this.isValid) {
        const result = await this.$apollo.mutate({
          mutation: gql`mutation ($username: String!, $password: String!) {
            login(username:$username, password:$password) {
              success
              message
            }
          }`,
          variables: {
            username: this.formData.username,
            password: this.formData.password
          }
        });

        this.$emit("onLoggedIn", result.data.login);
      }
    },

    async register() {
      this.$refs.form.validate();
      if (this.isValid) {
        const result = await this.$apollo.mutate({
          mutation: gql`mutation ($username: String!, $password: String!) {
            register(username:$username, password:$password) {
              success
              message
            }
          }`,
          variables: {
            username: this.formData.username,
            password: this.formData.password
          }
        });
        this.$emit("onRegistered", result.data.register);

        if (result.data.register.success) {
          await this.login();
        }
      }
    }
  }
};
</script>
