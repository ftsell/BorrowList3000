import gql from "graphql-tag";

export default async function(context) {
    if (process.server) {
        if (context.req.session.loggedIn !== true) {
            context.redirect("/auth");
        }
    } else {
        const loggedIn = (await context.app.apolloProvider.defaultClient.query({
            query: gql`{
                loggedIn
            }`
        })).data.loggedIn;
        if (loggedIn !== true) {
            context.redirect("/auth")
        }
    }
}
