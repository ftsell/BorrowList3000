import express from 'express'
import { graphqlHTTP } from 'express-graphql'
import { graphqlSchema } from './graphql'
import { register, login, logout, getOwnUser, isRequesterLoggedIn } from "./userController";
import { createBorrower, deleteBorrower } from './borrowerController'
import { createBorrowedItem, returnBorrowedItem } from "./borrowedItemController";
import { resetDb } from "./devController";

export function getProxyTrust() {
    if (process.env.BL_TRUST_PROXY === "true") {
        return true
    } else if (typeof process.env.BL_TRUST_PROXY == "string" && process.env.BL_TRUST_PROXY !== "") {
        return process.env.BL_TRUST_PROXY
    } else {
        return false
    }
}

const app = express()
app.set("trust proxy", getProxyTrust())
export const apiMiddleware = app

// the root provides a resolver function for each API endpoint
// noinspection JSUnusedGlobalSymbols
const root = {
    // queries
    me: getOwnUser,
    loggedIn: isRequesterLoggedIn,

    // mutations
    register,
    login,
    logout,
    createBorrower,
    createBorrowedItem,
    deleteBorrower,
    returnBorrowedItem,

    // dev only operations
    resetDb,
}

app.use('/graphql', graphqlHTTP({
    schema: graphqlSchema,
    rootValue: root,
    graphiql: true,
}))
