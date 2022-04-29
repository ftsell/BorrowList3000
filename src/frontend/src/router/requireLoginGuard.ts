import type { NavigationGuard } from "vue-router";
import { useAuthStore } from "@/stores/authStore";

export const requireLogin: NavigationGuard = function (to) {
  const authStore = useAuthStore();
  if (!authStore.isAuthenticated) {
    return {
      name: "auth",
      query:
        to.name === "home" && Object.keys(to.query).length === 0
          ? {}
          : { next: to.fullPath },
    };
  }
  return;
};
