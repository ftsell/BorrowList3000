import type { PersonDto } from "@/apiClient";
import { defineStore } from "pinia";
import { useRestApi } from "@/apiClient";
import { useAuthStore } from "@/stores/authStore";
import { watch } from "vue";

export interface PeopleState {
  people: PersonDto[] | null;
}

export const usePeopleStore = defineStore({
  id: "people",
  state: () =>
    ({
      people: null,
    } as PeopleState),
  getters: {
    getPersonByName: (state) => (name: string) =>
      state.people?.find((p) => p.name === name),
    getPersonById: (state) => (id: string) =>
      state.people?.find((p) => p.id === id),
  },
  actions: {
    async fetchFromApi() {
      const api = useRestApi();
      this.people = await api.value.people.listAllPeople();
    },
    async create(name: string): Promise<PersonDto> {
      // create new list in api
      const api = useRestApi();
      const response = await api.value.people.create({
        createPersonRequest: {
          name,
        },
      });

      // append list to store
      if (this.people) {
        this.people.push(response);
      } else {
        this.people = [response];
      }

      return response;
    },
  },
});

/**
 * Enable automatic fetching of the `people` field of the peopleStore whenever
 * authentication parameters change.
 * Also handles clearing of user data when authentication parameters are
 * cleared.
 */
export function useAutomaticPeopleFetching(): void {
  const authStore = useAuthStore();
  const peopleStore = usePeopleStore();

  watch([() => authStore.isAuthenticated], async () => {
    if (authStore.authToken != null) {
      await peopleStore.fetchFromApi();
    } else {
      peopleStore.people = null;
    }
  });
}
