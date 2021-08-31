<template>
  <v-form ref="form" @submit.prevent="onSubmit">
    <v-container>
      <v-row>
        <v-col>
          <v-textarea
            rows="3"
            label="Token"
            v-model="formData.authCode"
            :rules="formRules.authCode"
          />
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-btn color="primary" outlined type="submit">Submit</v-btn>
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
    authCode: this.$route.query.token as string | "",
  };
  formRules = {
    authCode: [(v: string) => !!v || "Token is required"],
  };

  get formElement(): any {
    return this.$refs.form;
  }

  async onSubmit(): Promise<void> {
    if (this.formElement.validate()) {
      const result = await this.$apollo.mutate({
        mutation: gql`
          mutation ($authCode: String!) {
            undoChangeEmail(authCode: $authCode) {
              success
              message
            }
          }
        `,
        variables: this.formData,
      });

      if (result.data.undoChangeEmail.success === true) {
        this.$emit("emailChangeUndone");
      } else {
        this.alerts.push({
          type: "error",
          message: result.data.undoChangeEmail.message,
        });
      }
    }
  }
}
</script>

<style scoped></style>
