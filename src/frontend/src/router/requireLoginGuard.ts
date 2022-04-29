import type { NavigationGuard } from "vue-router";
import { TokenValidation, useAuthStore } from "@/stores/authStore";

export const requireLogin: NavigationGuard = async function (to) {
  const authStore = useAuthStore();
  if (authStore.tokenValidation === TokenValidation.UNCHECKED) {
    await authStore.validateAuthToken(true);
  }

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
