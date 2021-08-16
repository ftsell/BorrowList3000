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

        <Nuxt />

        <!-- Profile Link -->
        <nuxt-link :to="settingsButton.to">
          <v-tooltip bottom>
            <template #default>
              <span>{{ settingsButton.tooltip }}</span>
            </template>
            <template #activator="{ on }">
              <v-btn id="settings-link" fab v-on="on">
                <v-icon>{{ settingsButton.icon }}</v-icon>
              </v-btn>
            </template>
          </v-tooltip>
        </nuxt-link>
      </v-container>
    </v-main>
    <Footer />
  </v-app>
</template>

<script>
import Footer from "~/components/Footer";

export default {
  components: { Footer },
  computed: {
    alerts() {
      return this.$store.state.alerts;
    },
    settingsButton() {
      const isSettings = this.$route.name === "app-settings";

      return {
        to: {
          ...this.$route,
          name: isSettings ? "app" : "app-settings",
        },
        icon: isSettings ? "mdi-arrow-left" : "mdi-cog",
        tooltip: isSettings ? "Back to app" : "Settings"
      };
    },
  },
  methods: {
    onAlertClose(alert) {
      this.$store.commit("hideAlert", alert);
    },
  },
};
</script>

<style>
#settings-link {
  position: fixed;
  top: 16px;
  right: 16px;
}
</style>
