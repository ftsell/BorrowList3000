<script setup lang="ts">
import Card from "@/components/componentLibrary/Card.vue";
import Login from "@/components/featureComponents/Login.vue";
import Register from "@/components/featureComponents/Register.vue";
import { useRouter } from "vue-router";

const router = useRouter();

async function onAuthSuccessful() {
  await router.push({
    name: "home",
  });
}
</script>

<template>
  <main>
    <Card title="Login">
      <Login @authSuccessful="onAuthSuccessful" />
    </Card>
    <Card title="Register">
      <Register @authSuccessful="onAuthSuccessful" />
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
