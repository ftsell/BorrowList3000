<template>
  <default-layout>
    <v-row align="center" justify="center">
      <v-col cols="5">
        <v-card rounded>
          <auth-form @loggedIn="onLoggedIn" />
        </v-card>
      </v-col>
    </v-row>
  </default-layout>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DefaultLayout from "@/layouts/default.vue";
import AuthForm from "@/components/forms/AuthForm.vue";

@Component({
  components: { DefaultLayout, AuthForm },
})
export default class Auth extends Vue {
  onLoggedIn(result: any): void {
    if (result.success === true) {
      if (this.$route.query.next) {
        this.$router.push({ path: this.$route.query.next as string });
      } else {
        this.$router.push({ name: "Index" });
      }
    }
  }
}
</script>
