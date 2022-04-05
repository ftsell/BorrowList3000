import { defineStore } from "pinia";

const LOCAL_STORAGE_KEY = "ThingPeopleListAuth";

export interface AuthState {
  authToken: string | null;
}

export const useAuthStore = defineStore({
  id: "auth",
  state: () => {
    const persisted = localStorage.getItem(LOCAL_STORAGE_KEY);
    if (persisted != null) {
      return JSON.parse(persisted) as AuthState;
    }
    return {
      authToken: null,
    } as AuthState;
  },
  actions: {
    persistAuth() {
      localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(this.$state));
    },
  },
  getters: {
    isAuthenticated: (state) => state.authToken != null,
  },
});
