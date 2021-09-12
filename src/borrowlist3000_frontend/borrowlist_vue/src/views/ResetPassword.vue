<template>
  <default-layout>
    <v-row align="center" justify="center">
      <v-col cols="12">
        <v-card>
          <reset-password-form @resetDone="onDone" />
        </v-card>
      </v-col>
    </v-row>
  </default-layout>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DefaultLayout from "@/layouts/default.vue";
import ResetPasswordForm from "@/components/forms/ResetPasswordForm.vue";

@Component({
  components: { DefaultLayout, ResetPasswordForm },
})
export default class ResetPassword extends Vue {
  onDone(): void {
    this.$router.push({ name: "Auth" });
  }
}
</script>
