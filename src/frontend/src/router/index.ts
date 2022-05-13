import { createRouter, createWebHistory } from "vue-router";
import DashboardView from "../views/DashboardView.vue";
import { requireLogin } from "@/router/requireLoginGuard";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/app/",
      name: "home",
      component: DashboardView,
      beforeEnter: [requireLogin],
    },
    {
      path: "/app/auth",
      name: "auth",
      component: () => import("@/views/AuthView.vue"),
    },
    /*
    {
      path: "/about",
      name: "about",
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("@/views/AboutView.vue"),
    },
     */
    {
      path: "/:catchAll(.*)*",
      name: "not-found",
      component: () => import("@/views/404.vue"),
    },
  ],
});

export default router;
