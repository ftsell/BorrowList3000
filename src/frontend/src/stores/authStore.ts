import { defineStore } from "pinia";
import { useRestApi } from "@/apiClient";

const LOCAL_STORAGE_KEY = "ThingPeopleListAuth";

export interface AuthState {
  authToken: string | null;
}

export const useAuthStore = defineStore({
  id: "auth",
  state: () =>
    ({
      authToken: null,
    } as AuthState),
  actions: {
    persistAuth() {
      localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(this.$state));
    },
  },
  getters: {
    isAuthenticated: (state) => state.authToken != null,
  },
});

/**
 * Retrieve the persisted auth state from local storage (if it exists)
 * and validate the credentials.
 */
export async function applyPersistedAuthState(
  authStore: ReturnType<typeof useAuthStore>
): Promise<void> {
  // retrieve persisted auth state
  const persisted = localStorage.getItem(LOCAL_STORAGE_KEY);
  if (persisted != null) {
    const state = JSON.parse(persisted) as AuthState;
    if (state.authToken != null) {
      // validate the token by trying it out
      const api = useRestApi();
      try {
        await api.value.user.getMe({
          headers: {
            Authorization: state.authToken,
          },
        });
      } catch (e) {
        if ((e as Response).status === 401) {
          // auth token is invalid
          localStorage.removeItem(LOCAL_STORAGE_KEY);
          return;
        }
      }

      // put persisted token into store
      Object.apply(authStore, state as never);
    }
  }
}
