<template>
  <default-layout>
    <v-row align="center" justify="center">
      <p>You will be redirected…</p>
    </v-row>
  </default-layout>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import DefaultLayout from "@/layouts/default.vue";

@Component({
  components: { DefaultLayout },
})
export default class Index extends Vue {}
</script>
