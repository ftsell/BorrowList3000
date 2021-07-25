import { Router } from 'express'
import { graphqlHTTP } from 'express-graphql'
import { graphqlSchema } from 'borrowlist3000common'

const router = Router()
export const nuxtMiddleware = router

// the root provides a resolver function for each API endpoint
const root = {
}

router.use('/graphql', graphqlHTTP({
    schema: graphqlSchema,
    rootValue: root,
    graphiql: true
}))
