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
})
export default class DefaultLayout extends Vue {
  @ProvideReactive() alerts: Alert[] = [];

  onAlertClose(alert: Alert): void {
    this.alerts = this.alerts.filter((a) => a !== alert);
  }
}
</script>
