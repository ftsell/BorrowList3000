import gql from "graphql-tag";

/**
 * Complete GraphQL schema definition
 */
export const graphqlSchema = gql`
    """
    A date string in YYYY/MM/DD format
    """
    scalar Date

    """
    Information about the currently logged in user
    """
    type User {
        username: String!
        email: String
        borrowers: [Borrower]!
    }

    """
    A person who borrowed something from the current user
    """
    type Borrower {
        name: String!
        borrowedItems: [BorrowedItem]!
    }

    """
    An item that was borrowed by a borrower from the current user
    """
    type BorrowedItem {
        id: String!
        specifier: String!
        description: String
        dateBorrowed: Date!
    }

    enum ResultCodes {
        OK
        ERR_USER_ALREADY_EXISTS
        ERR_LOGIN_FAILED
        ERR_LOGIN_REQUIRED
        ERR_BORROWER_ALREADY_EXISTS
        ERR_BORROWER_DOES_NOT_EXIST
    }

    type Query {
        me: User!
        loggedIn: Boolean!
    }

    input UserInput {
        email: String
        password: String
    }

    interface MutationResponse {
        success: Boolean!
        message: String!
        code: ResultCodes!
    }

    type RegisterMutationResponse implements MutationResponse {
        success: Boolean!
        message: String!
        code: ResultCodes!
        user: User
    }

    type SetEmailMutationResponse implements MutationResponse {
        success: Boolean!
        message: String!
        code: ResultCodes!
        user: User!
    }

    type UndoSetEmailMutationResponse implements MutationResponse {
        success: Boolean!
        message: String!
        code: ResultCodes!
    }

    type LoginMutationResponse implements MutationResponse {
        success: Boolean!
        message: String!
        code: ResultCodes!
        user: User
    }

    type LogoutMutationResponse implements MutationResponse {
        success: Boolean!
        message: String!
        code: ResultCodes!
    }

    type CreateBorrowerMutationResponse implements MutationResponse {
        success: Boolean!
        message: String!
        code: ResultCodes!
        borrower: Borrower
    }

    type CreateBorrowedItemMutationResponse implements MutationResponse {
        success: Boolean!
        message: String!
        code: ResultCodes!
        borrowedItem: BorrowedItem
    }

    type DeleteBorrowerMutationResponse implements MutationResponse {
        success: Boolean!
        message: String!
        code: ResultCodes!
    }

    type ReturnBorrowedItemMutationResponse implements MutationResponse {
        success: Boolean!
        message: String!
        code: ResultCodes!
    }

    type Mutation {
        """
        Create a new user account with the specified username and password
        """
        register(username: String!, password: String!): RegisterMutationResponse

        """
        Set the email address of the currently logged in user.
        A notification is sent to the old address as well as the new one.
        The notification to the old address includes a link to undo the change.
        The link effectively ends up calling the 'undoSetEmail' mutation.
        """
        setEmail(emailAddress: String!): SetEmailMutationResponse

        """
        Undo a previous email update by providing an authCode.
        """
        undoChangeEmail(authCode: String!): UndoSetEmailMutationResponse

        """
        Alter the currently logged in user.

        If "password" is changed, the user is notified of the change if they have an email address set.

        If "email" is changed, the previsouly defined email address is notified and provided a way to undo the change.
        """
        alterUser(user: UserInput!): User

        """
        Login as the specified user using the given password
        """
        login(username: String!, password: String!): LoginMutationResponse

        """
        Logout the current user
        """
        logout: LogoutMutationResponse

        """
        Create a new borrower with the given name
        """
        createBorrower(name: String!): CreateBorrowerMutationResponse

        """
        Create a new borrowed item with the given data
        """
        createBorrowedItem(
            borrower: String!
            specifier: String!
            description: String
            dateBorrowed: Date!
        ): CreateBorrowedItemMutationResponse

        """
        Delete the borrower with the given name and all their borrowed items
        """
        deleteBorrower(name: String!): DeleteBorrowerMutationResponse

        """
        Mark the borrowed item with the given id as being returned
        """
        returnBorrowedItem(id: String!): ReturnBorrowedItemMutationResponse

        # dev only operations
        """
        !!! DANGEROUS OPERATION !!!

        Completely clear all database data.

        Note: This must be manually enabled by the application operator
        """
        resetDb: Boolean
    }
`;
