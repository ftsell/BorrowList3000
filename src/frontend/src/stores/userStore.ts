import type { UserDto } from "@/apiClient";
import { defineStore } from "pinia";

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
