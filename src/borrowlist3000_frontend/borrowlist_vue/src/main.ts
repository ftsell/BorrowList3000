import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import { apolloProvider } from "./vue-apollo";
import { vuetify } from "./vuetify";

Vue.config.productionTip = false;

new Vue({
    router,
    apolloProvider,
    vuetify,
    render: (h) => h(App),
}).$mount("#app");
