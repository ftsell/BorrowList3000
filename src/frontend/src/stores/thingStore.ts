import type { ThingDto } from "@/apiClient";
import { defineStore } from "pinia";
import { useRestApi } from "@/apiClient";
import { useAuthStore } from "@/stores/authStore";
import { useListStore } from "@/stores/listStore";
import { watch } from "vue";

export interface ThingState {
  // A map of list-ids to things in that list
  things: Record<string, ThingDto[]> | null;
}

export const useThingStore = defineStore({
  id: "things",
  state: () =>
    ({
      things: null,
    } as ThingState),
  getters: {
    getThingsForList: (state) => (listId: string) =>
      state.things ? state.things[listId] : null,
  },
  actions: {
    async fetchFromApiForList(listId: string) {
      const api = useRestApi();
      if (this.things == null) {
        this.things = {};
      }
      this.things[listId] = await api.value.things.getAll({ listId });
    },
  },
});

/**
 * Enable automatic fetching of the `things` field of the thingStore whenever
 * authentication parameters or list of the listStore change.
 * Also handles clearing of user data when authentication parameters are
 * cleared.
 */
export function useAutomaticThingFetching(): void {
  const authStore = useAuthStore();
  const listStore = useListStore();
  const thingStore = useThingStore();

  watch(
    [() => authStore.isAuthenticated, () => listStore.lists],
    async () => {
      // clear complete store if we are not authenticated
      if (authStore.authToken == null) {
        thingStore.things = null;
      } else {
        // otherwise, if the store is uninitialized, initialize it
        if (thingStore.things == null) {
          thingStore.things = {};
        }

        if (listStore.lists != null) {
          // construct promises for the things of all lists that have not yet
          // been fetched and then fetch them
          const promises = listStore.lists
            .filter(
              (list) => !Object.keys(thingStore.things ?? {}).includes(list.id)
            )
            .map((list) => thingStore.fetchFromApiForList(list.id));
          await Promise.all(promises);
        }
      }
    },
    {
      immediate: true,
    }
  );
}
