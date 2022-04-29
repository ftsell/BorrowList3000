import { defineStore } from "pinia";
import { useRestApi } from "@/apiClient";

const LOCAL_STORAGE_KEY = "ThingPeopleListSessionToken";

export enum TokenValidation {
  UNCHECKED = "unchecked",
  PENDING = "pending",
  VALID = "valid",
  INVALID = "invalid",
}

export interface AuthState {
  authToken: string | null;
  tokenValidation: TokenValidation;
}

export const useAuthStore = defineStore({
  id: "auth",
  state: () =>
    ({
      authToken: localStorage.getItem(LOCAL_STORAGE_KEY),
      tokenValidation: TokenValidation.UNCHECKED,
    } as AuthState),
  actions: {
    persistAuth() {
      if (this.authToken != null) {
        localStorage.setItem(LOCAL_STORAGE_KEY, this.authToken);
      } else {
        localStorage.removeItem(LOCAL_STORAGE_KEY);
      }
    },
    async validateAuthToken(removeInvalidToken: boolean) {
      // if there is no token, we cannot validate anything
      if (this.authToken == null) {
        return;
      }

      this.$state.tokenValidation = TokenValidation.PENDING;

      // try to validate the token by using it
      const api = useRestApi();
      try {
        await api.value.user.getMe({
          headers: {
            Authorization: this.authToken,
          },
        });
      } catch (e) {
        if (removeInvalidToken) {
          this.tokenValidation = TokenValidation.UNCHECKED;
          this.authToken = null;
          this.persistAuth();
        } else {
          this.$state.tokenValidation = TokenValidation.INVALID;
        }
        return;
      }

      // if we have not run into an error, the token is valid
      this.tokenValidation = TokenValidation.VALID;
    },
  },
  getters: {
    isAuthenticated: (state) =>
      state.authToken != null &&
      state.tokenValidation === TokenValidation.VALID,
  },
});
