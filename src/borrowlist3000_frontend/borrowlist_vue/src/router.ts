import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import Auth from "@/views/Auth.vue";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
    {
        path: "/app",
        name: "Index",
        component: () =>
            import(/* webpackChunkName: "chunk-index" */ "@/views/Index.vue"),
    },
    {
        path: "/auth",
        name: "Auth",
        component: Auth,
    },
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
});

export default router;
