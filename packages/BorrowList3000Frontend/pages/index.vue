<template>
  <v-row align="center" justify="center">
    <p>You will be redirected…</p>
  </v-row>
</template>

<script>
export default {
  name: "Index",
  mounted() {
    this.$router.replace({path: "/app"})
  }
};
</script>
