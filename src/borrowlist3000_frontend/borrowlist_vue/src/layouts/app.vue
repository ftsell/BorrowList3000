<template>
  <v-app>
    <v-main>
      <v-container>
        <v-alert
          v-for="alert of alerts"
          :key="alert.message"
          :type="alert.type || 'info'"
          dismissible
          @input="onAlertClose(alert)"
        >
          {{ alert.message }}
        </v-alert>

        <slot />

        <!-- Profile Link -->
        <router-link :to="settingsButtonData.to">
          <v-tooltip bottom>
            <template #default>
              <span>{{ settingsButtonData.tooltip }}</span>
            </template>
            <template #activator="{ on }">
              <v-btn id="settings-link" fab v-on="on">
                <v-icon>{{ settingsButtonData.icon }}</v-icon>
              </v-btn>
            </template>
          </v-tooltip>
        </router-link>
      </v-container>
    </v-main>
    <Footer />
  </v-app>
</template>

<script lang="ts">
import { Component, Vue, ProvideReactive } from "vue-property-decorator";
import Footer from "@/components/Footer.vue";
import { Alert } from "@/utils";

@Component({
  components: { Footer },
  provide: {
    alerts: [],
  },
})
export default class AppLayout extends Vue {
  @ProvideReactive() alerts: Alert[] = [];

  get settingsButtonData() {
    const isSettings = this.$route.name === "Settings";
    return {
      to: {
        ...this.$route,
        name: isSettings ? "Index" : "Settings",
      },
      icon: isSettings ? "mdi-arrow-left" : "mdi-cog",
      tooltip: isSettings ? "Back to app" : "Settings",
    };
  }
}
</script>

<style scoped>
#settings-link {
  position: fixed;
  top: 16px;
  right: 16px;
}
</style>
