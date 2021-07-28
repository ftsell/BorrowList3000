<template>
  <v-row align="center" justify="center">
    <v-col cols="12" md="6" sm="8">
      <auth />
    </v-col>
  </v-row>
</template>

<script>
import Auth from "~/components/forms/AuthForm";

export default {
  name: "Index",
  components: {Auth}
};
</script>
