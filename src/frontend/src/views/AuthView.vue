<script setup lang="ts">
import Card from "@/components/componentLibrary/Card.vue";
import Login from "@/components/featureComponents/Login.vue";
</script>

<template>
  <main>
    <Card title="Login">
      <Login />
    </Card>
    <Card title="Register">
      <h2>Register</h2>
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
  flex-grow: 1;
  animation-fill-mode: forwards;
  animation-name: unFocus;
  animation-duration: 0.5s;
}

main > *:hover,
main > *:active {
  animation-name: focus;
}

@keyframes focus {
  to {
    flex-grow: 1.3;
  }
}

@keyframes unFocus {
  from {
    flex-grow: 1.3;
  }
}
</style>
