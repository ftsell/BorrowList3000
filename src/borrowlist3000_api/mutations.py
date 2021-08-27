from typing import *
import graphene

from borrowlist3000_api import types


class RegisterMutation(graphene.Mutation):
    """
    Create a new user account with the specified username, password and email address.

    It is possible to specify no email address in which case password reset features are disabled for the account
    until and email address is added.
    """
    class Arguments:
        username = graphene.Argument(graphene.String, required=True)
        password = graphene.Argument(graphene.String, required=True)
        email = graphene.Argument(graphene.String)

    user = graphene.Field(types.UserType, required=True)

    @classmethod
    def mutate(cls, root, info, username: str, password: str, email: Optional[str]):
        # TODO Implement
        pass


class LoginMutation(graphene.Mutation):
    """
    Login as the specified user using the given password
    """
    class Arguments:
        username = graphene.Argument(graphene.String, required=True)
        password = graphene.Argument(graphene.String, required=True)

    user = graphene.Field(types.UserType, required=True)

    @classmethod
    def mutate(cls, root, info):
        # TODO Implement
        pass


class LogoutMutation(graphene.Mutation):
    """
    Logout the currently logged in user
    """

    changed = graphene.Field(graphene.Boolean, required=True)

    @classmethod
    def mutate(cls, root, info):
        # TODO Implement
        pass


class SetEmailMutation(graphene.Mutation):
    """
    Set the email address of the currently logged in user.
    A notification is sent to the old address as well as the new one.
    The notification to the old address includes a link to undo the change.
    The link effectively ends up calling the 'undoSetEmail' mutation.
    """
    class Arguments:
        email_address = graphene.Argument(graphene.String, required=True)

    user = graphene.Field(types.UserType, required=True)

    @classmethod
    def mutate(cls, root, info, email_address):
        # TODO Implement
        pass


class UndoChangeEmailMutation(graphene.Mutation):
    """
    Undo a previous email update by providing an authCode.
    """
    class Arguments:
        auth_code = graphene.Argument(graphene.String, required=True)

    user = graphene.Field(types.UserType, required=True)

    @classmethod
    def mutate(cls, root, info, auth_code):
        # TODO Implement
        pass


class AlterUser(graphene.Mutation):
    """
    Alter the currently logged in user.

    If "password" is changed, the user is notified of the change if they have an email address set.

    If "email" is changed, the previously defined email address is notified and provided a way to undo the change.
    """
    class Arguments:
        user = graphene.Argument(types.UserInput)

    user = graphene.Field(types.UserType, required=True)

    @classmethod
    def mutate(cls, root, info, user):
        # TODO Implement
        pass


class CreateBorrower(graphene.Mutation):
    """
    Create a new borrower with the given name
    """
    class Arguments:
        name = graphene.Argument(graphene.String, required=True)

    borrower = graphene.Field(types.BorrowerType, required=True)

    @classmethod
    def mutate(cls, root, info, name):
        # TODO Implement
        pass


class CreateBorrowedItem(graphene.Mutation):
    """
    Create a new borrowed item with the given data
    """
    class Arguments:
        borrower = graphene.Argument(graphene.UUID, required=True)
        specifier = graphene.Argument(graphene.String, required=True)
        description = graphene.Argument(graphene.String)
        date_borrowed = graphene.Argument(graphene.Date)

    borrowed_item = graphene.Field(types.BorrowedItemType, required=True)

    @classmethod
    def mutate(cls, root, info, borrower, specifier, description, date_borrowed):
        # TODO Implement
        pass


class DeleteBorrower(graphene.Mutation):
    """
    Delete the borrower with the given name and all their borrowed items
    """
    class Arguments:
        name = graphene.Argument(graphene.String, required=True)

    success = graphene.Field(graphene.Boolean, required=True)

    @classmethod
    def mutate(cls, root, info, name):
        # TODO Implement
        pass


class ReturnBorrowedItem(graphene.Mutation):
    """
    Mark the borrowed item with the given id as being returned.

    Currently this simply deletes the borrowed item from the database but that behaviour might change later.
    """
    class Arguments:
        id = graphene.Argument(graphene.UUID, required=True)

    success = graphene.Field(graphene.Boolean, required=True)

    @classmethod
    def mutate(cls, root, info, id):
        # TODO Implement
        pass
