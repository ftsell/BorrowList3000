<template>
  <v-form ref="form" @submit.prevent="onSubmit">
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
            :rules="formRules.username"
          />
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-btn color="primary" outlined type="submit">{{ submitText }}</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class UsernameForm extends Vue {
  @Prop({ required: true, type: String }) submitText!: string;
  @Prop({ default: null, type: String }) explanation?: string;

  formData = {
    username: "",
  };
  formRules = {
    username: [(v: string) => !!v || "Username is required"],
  };

  get formElement(): any {
    return this.$refs.form as Element;
  }

  onSubmit(): void {
    if (this.formElement.validate()) {
      this.$emit("submit", { ...this.formData });
    }
  }
}
</script>
