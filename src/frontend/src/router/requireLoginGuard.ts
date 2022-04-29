import type { NavigationGuard } from "vue-router";
import { useAuthStore } from "@/stores/authStore";

export const requireLogin: NavigationGuard = function () {
  const authStore = useAuthStore();
  if (!authStore.isAuthenticated) {
    return { name: "auth" };
  }
  return;
};
