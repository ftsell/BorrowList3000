<template>
  <v-row align="center" justify="center">
    <v-col cols="12">
      <v-card>
        <auth-form @onLoggedIn="onLoggedIn" />

        <v-alert v-if="alert" :type="alert.type" text class="mt-2">
          {{ alert.message }}
        </v-alert>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import AuthForm from "~/components/forms/AuthForm";

export default {
  name: "Auth",
  components: { AuthForm },
  data: () => ({
    alert: null,
  }),
  methods: {
    onLoggedIn(result) {
      if (result.success) {
        this.alert = null
        this.$router.push({path: "/app"})
      } else {
        this.alert = {
          type: "error",
          message: result.message
        }
      }
    }
  }
};
</script>
