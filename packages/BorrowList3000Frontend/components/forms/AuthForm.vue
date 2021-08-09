<template>
  <v-form ref='form' v-model='isValid' @submit.prevent='login'>
    <v-container>
      <v-row>
        <v-col>
          <v-text-field v-model='formData.username' label='Username' required :rules='usernameRules' />
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-text-field v-model='formData.password' label='Password' type='password' required
                        :rules='passwordRules' />
        </v-col>
      </v-row>

      <v-row justify='start' class='d-flex'>
        <v-col>
          <v-btn color='primary' outlined type='submit' @click='login' >Login</v-btn>
          <v-btn color='secondary' outlined @click='register'>Register</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>

<script>
import gql from 'graphql-tag'

export default {
  name: 'AuthForm',
  data: () => ({
    isValid: true,
    formData: {
      username: '',
      password: ''
    },
    usernameRules: [
      v => !!v || 'Username is required'
    ],
    passwordRules: [
      v => !!v || 'Password is required'
    ]
  }),
  methods: {
    async login() {
      this.$refs.form.validate()
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
        })

        if (result.data.login.success === false) {
          this.$store.commit('showAlert', {
            type: 'error',
            message: result.data.login.message
          })
        } else {
          this.$store.commit("clearUserSpecificData")
          this.$emit('onLoggedIn', result.data.login)
        }
      }
    },

    async register() {
      this.$refs.form.validate()
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
        })

        if (result.data.register.success === false) {
          this.$store.commit('showAlert', {
            type: 'error',
            message: result.data.register.message
          })
        } else {
          this.$emit('onRegistered', result.data.register)
          await this.login()
        }
      }
    }
  }
}
</script>
