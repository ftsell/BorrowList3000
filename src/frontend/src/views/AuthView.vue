<script setup lang="ts">
import Card from "@/components/componentLibrary/Card.vue";
import Login from "@/components/featureComponents/Login.vue";
import Register from "@/components/featureComponents/Register.vue";
</script>

<template>
  <main>
    <Card title="Login">
      <Login />
    </Card>
    <Card title="Register">
      <Register />
    </Card>
  </main>
</template>

<style scoped>
main {
  margin: 0 20vw;
  display: flex;
  justify-content: space-around;
  gap: 64px;
}

main > * {
  flex-basis: 100px;
  flex-grow: 1;
  transition: flex-grow 0.5s ease-in-out;
}

main > *:hover,
main > *:focus-within {
  flex-grow: 1.4;
}
</style>
