<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script lang="ts">
import { Component, Vue, ProvideReactive } from "vue-property-decorator";
import gql from "graphql-tag";

@Component({
  apollo: {
    appConfig: gql`
      query {
        appConfig {
          emailEnabled
        }
      }
    `,
  },
})
export default class App extends Vue {
  @ProvideReactive() appConfig: any;
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
