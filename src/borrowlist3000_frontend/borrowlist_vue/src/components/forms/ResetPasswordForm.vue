<template>
  <v-form ref="form" @submit.prevent="onSubmit">
    <v-container>
      <v-row>
        <v-col>
          <v-text-field
            label="New Password"
            type="password"
            v-model="formData.newPassword"
            :rules="formRules.newPassword"
          />
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-btn color="primary" outlined type="submit">Reset Password</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>

<script lang="ts">
import { Component, InjectReactive, Vue } from "vue-property-decorator";
import gql from "graphql-tag";
import { Alert } from "@/utils";

@Component
export default class UndoSetEmailForm extends Vue {
  @InjectReactive() alerts!: Alert[];

  formData = {
    newPassword: "",
  };
  formRules = {
    newPassword: [(v: string) => !!v || "A new password is required"],
  };

  get formElement(): any {
    return this.$refs.form;
  }

  async onSubmit(): Promise<void> {
    if (this.formElement.validate()) {
      const result = await this.$apollo.mutate({
        mutation: gql`
          mutation ($token: String!, $newPassword: String!) {
            forgotPassword2(token: $token, newPassword: $newPassword) {
              success
              message
            }
          }
        `,
        variables: {
          token: (this.$route.query.token as string) || "",
          newPassword: this.formData.newPassword,
        },
      });

      if (result.data.forgotPassword2.success === true) {
        this.$emit("resetDone");
      } else {
        this.alerts.push({
          type: "error",
          message: result.data.forgotPassword2.message,
        });
      }
    }
  }
}
</script>

<style scoped></style>
