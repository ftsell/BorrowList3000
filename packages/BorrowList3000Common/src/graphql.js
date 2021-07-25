import { buildSchema } from 'graphql'

const types = `
type User {
    username: String!,
    email: String,
    emailVerified: Boolean,
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

interface MutationResponse {
    success: Boolean!,
    message: String!,
    code: Int!,
}
`

const query = `
type Query {
    me: User!,
}
`

const mutations = `
type RegisterMutationResponse implements MutationResponse {
    success: Boolean!,
    message: String!,
    code: Int!,
    user: User!,
}

type VerifyEmailMutationResponse implements MutationResponse {
    success: Boolean!,
    message: String!,
    code: Int!,
    user: User!,
}

type LoginMutationResponse implements MutationResponse {
    success: Boolean!,
    message: String!,
    code: Int!,
    user: User!,
    sessionId: String!,
}

type Mutation {
    register(username: String!, password: String!, email: String): RegisterMutationResponse
    verifyEmail(username: String!, verificationCode: String!): VerifyEmailMutationResponse
    login(username: String!, password: String!): LoginMutationResponse
}
`

/**
 * Complete GraphQL schema definition
 *
 * @type {GraphQLSchema}
 */
export const graphqlSchema = buildSchema(`
    ${types}
    ${query}
    ${mutations}
`)
