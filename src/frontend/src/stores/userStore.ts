import type { UserDto } from "@/apiClient";
import { defineStore } from "pinia";
import { watchEffect } from "vue";
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
  const api = useRestApi();
  watchEffect(async () => {
    if (authStore.authToken != null) {
      userStore.me = await api.value.user.getMe();
    } else {
      userStore.me = null;
    }
  });
}
