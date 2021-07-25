import { buildSchema } from 'graphql'

const types = `
type User {
    username: String!,
    borrowers: [Borrower]!,
}

type Borrower {
    name: String!,
    borrowedItems: [BorrowedItem]!,
}

type BorrowedItem {
    specifier: String!,
    description: String,
    dateBorrowed: String!,
}
`

const query = `
{
    me: User!,
}
`

/**
 * Complete GraphQL schema definition
 *
 * @type {GraphQLSchema}
 */
export const graphqlSchema = buildSchema(`
    ${types}
    type Query ${query}
`)
