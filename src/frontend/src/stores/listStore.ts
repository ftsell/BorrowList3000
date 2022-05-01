import type { ThingListDto } from "@/apiClient";
import { defineStore } from "pinia";
import { useAuthStore } from "@/stores/authStore";
import { useRestApi } from "@/apiClient";
import { watch } from "vue";

export interface ListState {
  lists: ThingListDto[] | null;
}

export const useListStore = defineStore({
  id: "thingLists",
  state: () =>
    ({
      lists: null,
    } as ListState),
  getters: {
    getListByName: (state) => (listName: string) =>
      state.lists?.find((l) => l.name === listName),
    getListById: (state) => (id: string) =>
      state.lists?.find((l) => l.id === id),
  },
  actions: {
    async fetchFromApi() {
      const api = useRestApi();
      this.lists = await api.value.lists.getAll1();
    },
  },
});

/**
 * Enable automatic fetching of the `lists` field of the listStore whenever
 * authentication parameters change.
 * Also handles clearing of user data when authentication parameters are
 * cleared.
 */
export function useAutomaticListFetching(): void {
  const authStore = useAuthStore();
  const listStore = useListStore();

  watch([() => authStore.isAuthenticated], async () => {
    if (authStore.authToken != null) {
      await listStore.fetchFromApi();
    } else {
      listStore.lists = null;
    }
  });
}
