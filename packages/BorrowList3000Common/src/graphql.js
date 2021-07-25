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
`

const errors = `
enum ResultCodes {
    OK
    ERR_USER_ALREADY_EXISTS
    ERR_LOGIN_FAILED
    ERR_LOGIN_REQUIRED
    ERR_BORROWER_ALREADY_EXISTS
}
`

const query = `
type Query {
    me: User!,
}
`

const mutations = `
interface MutationResponse {
    success: Boolean!,
    message: String!,
    code: ResultCodes!,
}

type RegisterMutationResponse implements MutationResponse {
    success: Boolean!,
    message: String!,
    code: ResultCodes!,
    user: User,
}

type VerifyEmailMutationResponse implements MutationResponse {
    success: Boolean!,
    message: String!,
    code: ResultCodes!,
    user: User!,
}

type LoginMutationResponse implements MutationResponse {
    success: Boolean!,
    message: String!,
    code: ResultCodes!,
    user: User,
}

type LogoutMutationResponse implements MutationResponse {
    success: Boolean!,
    message: String!,
    code: ResultCodes!
}

type CreateBorrowerMutationResponse implements MutationResponse {
    success: Boolean!,
    message: String!,
    code: ResultCodes!,
    borrower: Borrower,
}

type CreateBorrowedItemMutationResponse implements MutationResponse {
    success: Boolean!,
    message: String!,
    code: ResultCodes!,
    borrowedItem: BorrowedItem
}

type Mutation {
    # user management
    register(username: String!, password: String!, email: String): RegisterMutationResponse
    verifyEmail(username: String!, verificationCode: String!): VerifyEmailMutationResponse
    login(username: String!, password: String!): LoginMutationResponse
    logout: LogoutMutationResponse

    # standard object interactions
    createBorrower(name: String!): CreateBorrowerMutationResponse
}
`

/**
 * Complete GraphQL schema definition
 *
 * @type {GraphQLSchema}
 */
export const graphqlSchema = buildSchema(`
    ${types}
    ${errors}
    ${query}
    ${mutations}
`)
