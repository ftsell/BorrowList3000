import { Router } from 'express'
import { graphqlHTTP } from 'express-graphql'
import { graphqlSchema } from './graphql'
import { register, login, logout, getOwnUser, isRequesterLoggedIn } from "./userController";
import { createBorrower, deleteBorrower } from './borrowerController'
import { createBorrowedItem, returnBorrowedItem } from "./borrowedItemController";
import { resetDb } from "./devController";

const router = Router()
export const apiMiddleware = router

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

router.use('/graphql', graphqlHTTP({
    schema: graphqlSchema,
    rootValue: root,
    graphiql: true,
}))
