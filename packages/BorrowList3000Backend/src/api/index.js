import { Router } from "express";
import { ApolloServer } from "apollo-server-express";
import { ApolloServerPluginLandingPageGraphQLPlayground } from "apollo-server-core";
import { graphqlSchema } from "./graphql";
import {
    register,
    login,
    logout,
    getOwnUser,
    isRequesterLoggedIn,
} from "./userController";
import { createBorrower, deleteBorrower } from "./borrowerController";
import {
    createBorrowedItem,
    returnBorrowedItem,
} from "./borrowedItemController";
import { resetDb } from "./devController";

const router = Router();
export const apiMiddleware = router;

// the root provides a resolver function for each API endpoint
// noinspection JSUnusedGlobalSymbols
const resolvers = {
    Query: {
        me: getOwnUser,
        loggedIn: isRequesterLoggedIn,
    },
    Mutation: {
        register,
        login,
        logout,
        createBorrower,
        createBorrowedItem,
        deleteBorrower,
        returnBorrowedItem,

        // dev only operations
        resetDb,
    },
};

const server = new ApolloServer({
    typeDefs: graphqlSchema,
    resolvers,
    context({ req }) {
        return { req };
    },
    plugins: [ApolloServerPluginLandingPageGraphQLPlayground()],
});
server.start().then(() => {
    server.applyMiddleware({
        app: router,
    });
});
