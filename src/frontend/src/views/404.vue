<script setup lang="ts">
import Card from "@/components/componentLibrary/Card.vue";
import VerticalDivider from "@/components/componentLibrary/VerticalDivider.vue";
import RegularButton from "@/components/componentLibrary/RegularButton.vue";
</script>

<template>
  <div class="center-container">
    <Card>
      <main>
        <h1>Not Found</h1>
        <p>
          The page you are trying to access does not exist or could not be found
        </p>
        <VerticalDivider />
        <div class="action-container">
          <RouterLink :to="{ name: 'home' }">
            <RegularButton :outlined="true" text="Go back to Dashboard" />
          </RouterLink>
        </div>
      </main>
    </Card>
  </div>
</template>

<style scoped>
.center-container {
  margin-top: 64px;
  display: flex;
  justify-content: center;
}

.center-container > * {
  flex-basis: 256px;
  flex-grow: 1;
  max-width: 1024px;
}

.action-container {
  margin-top: 16px;
  display: flex;
  justify-content: space-evenly;
}
</style>
