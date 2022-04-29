import type { UserDto } from "@/apiClient";
import { defineStore } from "pinia";
import { watch } from "vue";
import { useAuthStore } from "@/stores/authStore";
import { useRestApi } from "@/apiClient";

export interface UserState {
  me: UserDto | null;
}

export const useUserStore = defineStore({
  id: "user",
  state: () =>
    ({
      me: null,
    } as UserState),
  actions: {
    async fetchFromApi() {
      const api = useRestApi();
      this.me = await api.value.user.getMe();
    },
  },
});

/**
 * Enable automatic fetching of the `me` field of the userStore whenever
 * authentication parameters change.
 * Also handles clearing of user data when authentication parameters are
 * cleared.
 */
export function useAutomaticUserFetching(): void {
  const authStore = useAuthStore();
  const userStore = useUserStore();

  watch([() => authStore.isAuthenticated], async () => {
    if (authStore.authToken != null) {
      await userStore.fetchFromApi();
    } else {
      userStore.me = null;
    }
  });
}
