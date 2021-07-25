import { Router } from 'express'
import { graphqlHTTP } from 'express-graphql'
import { graphqlSchema } from 'borrowlist3000common'
import session from 'express-session'
import { sessionStore } from '../db/sessions'
import { register, login, logout, getOwnUser } from './userController'
import { createBorrower } from './borrowerController'
import { createBorrowedItem } from "./borrowedItemController";

const router = Router()
export const nuxtMiddleware = router

router.use(session({
    secret: 'foobar123',
    store: sessionStore,
    cookie: {
        secure: "auto"
    },
    name: "session",
    resave: false,
    rolling: true,
    saveUninitialized: false,
    unset: "destroy"
}))

// the root provides a resolver function for each API endpoint
const root = {
    // queries
    me: getOwnUser,

    // mutations
    register,
    login,
    logout,
    createBorrower,
    createBorrowedItem,
}

router.use('/graphql', graphqlHTTP({
    schema: graphqlSchema,
    rootValue: root,
    graphiql: true,
}))
