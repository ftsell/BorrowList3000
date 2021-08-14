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

    type VerifyEmailMutationResponse implements MutationResponse {
        success: Boolean!
        message: String!
        code: ResultCodes!
        user: User!
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
        Start or complete the email verification process depending on whether 'verificationCode' is given or not.

        If it is not given, an email will be sent to the current user to start the verification process.
        This email will include a link (but also the verification code directly) whic is needed to complete the process.

        If it is given and the code is correct, complete the verification process and mark the users email address as
        verified.
        """
        verifyEmail(verificationCode: String): VerifyEmailMutationResponse

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
