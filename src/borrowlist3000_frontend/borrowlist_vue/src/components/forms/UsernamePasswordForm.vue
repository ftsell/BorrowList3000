<template>
  <v-form ref="form" @submit.prevent="login">
    <v-container>
      <v-row v-if="explanation != null">
        <v-col>
          <p class="text-justify">
            {{ explanation }}
          </p>
        </v-col>
      </v-row>

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
          <v-btn color="primary" outlined type="submit">{{ submitText }}</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>

<script lang="ts">
import { Component, InjectReactive, Prop, Vue } from "vue-property-decorator";
import { Alert } from "@/utils";

@Component
export default class UsernamePasswordForm extends Vue {
  @Prop({ required: true, type: String }) submitText!: string;
  @Prop({ default: null, type: String }) explanation?: string;

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
    if (this.formElement.validate()) {
      this.$emit("submit", { ...this.formData });
    }
  }
}
</script>
