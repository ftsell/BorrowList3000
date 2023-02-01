import {
  AuthControllerApi,
  PeopleControllerApi,
  ThingControllerApi,
  ThingListControllerApi,
  UserControllerApi,
} from "./apis";
import { Configuration } from "./runtime";
import type { ConfigurationParameters, HTTPHeaders } from "./runtime";
import { useAuthStore } from "@/stores/authStore";
import { ref, watchEffect } from "vue";
import type { Ref, UnwrapRef } from "vue";

export interface ApiCollection {
  auth: AuthControllerApi;
  user: UserControllerApi;
  people: PeopleControllerApi;
  things: ThingControllerApi;
  lists: ThingListControllerApi;
}

function createApiCollection(
  authStore: ReturnType<typeof useAuthStore>
): ApiCollection {
  const configParams: ConfigurationParameters = {
    basePath: "",
  };

  if (authStore.isAuthenticated != null) {
    configParams.headers = {
      Authorization: authStore.authToken,
    } as HTTPHeaders;
  }

  const config = new Configuration(configParams);

  return {
    auth: new AuthControllerApi(config),
    user: new UserControllerApi(config),
    people: new PeopleControllerApi(config),
    things: new ThingControllerApi(config),
    lists: new ThingListControllerApi(config),
  };
}

/**
 * Use a reactive bundle of all defined APIs.
 *
 * This bundle is reactive on the current authentication state.
 */
export function useRestApi(): Ref<UnwrapRef<ApiCollection>> {
  const authStore = useAuthStore();
  const result = ref(createApiCollection(authStore));

  watchEffect(() => {
    result.value = createApiCollection(authStore);
  });

  return result;
}
