<script setup lang="ts">
import Card from "@/components/componentLibrary/Card.vue";
import Login from "@/components/featureComponents/Login.vue";
import Register from "@/components/featureComponents/Register.vue";
import { useRouter } from "vue-router";

const router = useRouter();

async function onAuthSuccessful() {
  if (router.currentRoute.value.query["next"] != null) {
    const next = Array.isArray(router.currentRoute.value.query["next"])
      ? router.currentRoute.value.query["next"][0]
      : router.currentRoute.value.query["next"];
    await router.push(next ?? { name: "home" });
  } else {
    await router.push({
      name: "home",
    });
  }
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
  margin: 128px 5vw;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  row-gap: 32px;
  column-gap: 64px;
}

main > * {
  flex-grow: 1;
  max-width: 640px;
}
</style>
