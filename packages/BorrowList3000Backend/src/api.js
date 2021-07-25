import { Router } from 'express'
import { graphqlHTTP } from 'express-graphql'
import { graphqlSchema } from 'borrowlist3000common'
import { UserRepository } from './db/repositories'
import session from 'express-session'
import { sessionStore } from './db/sessions'

const router = Router()
export const nuxtMiddleware = router

router.use(session({
    store: sessionStore
}))

// the root provides a resolver function for each API endpoint
const root = {
    async register({ username, password, email }) {
        const user = await UserRepository.createUser(username, password, email)
        return {
            success: true,
            message: 'success',
            code: 200,
            user: user
        }
    }
}

router.use('/graphql', graphqlHTTP({
    schema: graphqlSchema,
    rootValue: root,
    graphiql: true
}))
