<template>
  <v-app>
    <v-main>
      <v-container>
        <v-alert v-for='alert of alerts' :key='alert.message' :type='alert.type || "info"' dismissible
                 @input='onAlertClose(alert)'>
          {{ alert.message }}
        </v-alert>
        <Nuxt />
      </v-container>
    </v-main>
    <Footer />
  </v-app>
</template>

<script>
import Footer from '~/components/Footer'

export default {
  components: { Footer },
  computed: {
    alerts() {
      return this.$store.state.alerts
    }
  },
  methods: {
    onAlertClose(alert) {
      this.$store.commit('hideAlert', alert)
    }
  },
}
</script>
