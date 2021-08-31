import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import { apolloProvider } from "./vue-apollo";
import { vuetify } from "./vuetify";
import "@mdi/font/css/materialdesignicons.css";

Vue.config.productionTip = false;

new Vue({
    router: router(apolloProvider),
    apolloProvider,
    vuetify,
    render: (h) => h(App),
}).$mount("#app");
