import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import Auth from "@/views/Auth.vue";
import NotFound from "@/views/404.vue";
import { loginRequired } from "@/route-guards";
import VueApollo from "vue-apollo";

Vue.use(VueRouter);

function routes(apolloProvider: VueApollo): RouteConfig[] {
    const loginRequiredInst = loginRequired(apolloProvider);

    return [
        {
            path: "/",
            name: "Index",
            component: () =>
                import(
                    /* webpackChunkName: "chunk-index" */ "@/views/Index.vue"
                ),
            beforeEnter: loginRequiredInst,
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
                    /* webpackChunkName: "chunk-settings" */ "@/views/Settings.vue"
                ),
            beforeEnter: loginRequiredInst,
        },
        {
            path: "/undoSetEmail",
            name: "UndoSetEmail",
            component: () =>
                import(
                    /* webpackChunkName: "chunk-undoSetEmail" */ "@/views/UndoSetEmail.vue"
                ),
        },
        {
            path: "/resetPassword",
            name: "ResetPassword",
            component: () =>
                import(
                    /* webpackChunkName: "chunk-resetPassword" */ "@/views/ResetPassword.vue"
                ),
        },
        {
            path: "*",
            component: NotFound,
        },
    ];
}

export default function (apolloProvider: VueApollo): VueRouter {
    return new VueRouter({
        mode: "history",
        base: process.env.BASE_URL,
        routes: routes(apolloProvider),
    });
}
