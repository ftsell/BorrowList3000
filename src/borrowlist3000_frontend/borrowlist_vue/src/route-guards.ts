import { NavigationGuard } from "vue-router";
import VueApollo from "vue-apollo";
import gql from "graphql-tag";

export function loginRequired(apolloProvider: VueApollo): NavigationGuard {
    return async function (to, from, next) {
        const result = await apolloProvider.defaultClient.query({
            query: gql`
                query {
                    loggedIn
                }
            `,
        });

        if (result.data.loggedIn === true) {
            next();
        } else {
            console.info(
                `Canceled navigation to ${to.fullPath} because the user is not logged in`
            );
            next({ name: "Auth", query: { next: to.fullPath } });
        }
    };
}
