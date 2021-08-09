<template>
  <v-row align="center" justify="center">
    <v-col cols="12">
      <v-card>
        <auth-form @onLoggedIn="onLoggedIn" />
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import AuthForm from "~/components/forms/AuthForm";

export default {
  name: "Auth",
  components: { AuthForm },
  methods: {
    onLoggedIn(result) {
      if (result.success === true) {
        this.$router.push({path: "/app"})
      }
    }
  }
};
</script>
