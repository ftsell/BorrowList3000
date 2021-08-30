import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import Auth from "@/views/Auth.vue";
import NotFound from "@/views/404.vue";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
    {
        path: "/",
        name: "Index",
        component: () =>
            import(/* webpackChunkName: "chunk-index" */ "@/views/Index.vue"),
    },
    {
        path: "/auth",
        name: "Auth",
        component: Auth,
    },
    {
        path: "/settings",
        name: "Settings",
        component: () =>
            import(
                /* webpackChunkName: "chunk-index" */ "@/views/Settings.vue"
            ),
    },
    {
        path: "*",
        component: NotFound,
    },
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
});

export default router;
