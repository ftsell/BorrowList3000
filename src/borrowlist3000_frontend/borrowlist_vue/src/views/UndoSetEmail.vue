<template>
  <default-layout>
    <v-row align="center" justify="center">
      <v-col cols="12">
        <v-card>
          <undo-set-email-form @emailChangeUndone="onDone" />
        </v-card>
      </v-col>
    </v-row>
  </default-layout>
</template>

<script lang="ts">
import { Component, Vue, InjectReactive } from "vue-property-decorator";
import DefaultLayout from "@/layouts/default.vue";
import UndoSetEmailForm from "@/components/forms/UndoSetEmailForm.vue";

@Component({
  components: { DefaultLayout, UndoSetEmailForm },
})
export default class UndoSetEmail extends Vue {
  @InjectReactive() appConfig!: any;

  onDone(): void {
    this.$router.push({ name: "Index" });
  }
}
</script>
