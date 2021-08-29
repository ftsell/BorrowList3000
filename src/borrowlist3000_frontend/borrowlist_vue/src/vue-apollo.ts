import ApolloClient from "apollo-boost";
import Vue from "vue";
import VueApollo from "vue-apollo";

Vue.use(VueApollo);

const apolloClient = new ApolloClient({
    uri: "/api/graphql",
});

export const apolloProvider = new VueApollo({
    defaultClient: apolloClient,
});
