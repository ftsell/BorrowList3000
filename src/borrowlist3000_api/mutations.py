import datetime
from typing import *
from uuid import UUID

import graphene
from django.contrib.auth import authenticate, login, logout
from graphql import ResolveInfo

from borrowlist3000_api import types
from borrowlist3000_bll.mails import send_email_changed_notifications, send_email_restored_notification, \
    send_password_changed_notification, send_account_deleted_notification
from borrowlist3000_bll.tokens import verify_email_restore_token
from borrowlist3000_db import models
from borrowlist3000_db.models import UserModel, BorrowerModel, BorrowedItemModel


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

    user = graphene.Field(types.UserType)
    success = graphene.Field(graphene.Boolean, required=True)
    message = graphene.Field(graphene.String, required=True)

    @classmethod
    def mutate(cls, root, info, username: str, password: str, email: Optional[str] = None) -> 'RegisterMutation':
        if models.UserModel.objects.get_queryset().filter(username__iexact=username).exists():
            return RegisterMutation(success=False, message=f"User named {username} already exists")

        user = models.UserModel.objects.create_user(username=username, email=email, password=password)
        return RegisterMutation(user=user, success=True, message=f"Successfully created user {username}")


class LoginMutation(graphene.Mutation):
    """
    Login as the specified user using the given password
    """

    class Arguments:
        username = graphene.Argument(graphene.String, required=True)
        password = graphene.Argument(graphene.String, required=True)

    user = graphene.Field(types.UserType)
    success = graphene.Field(graphene.Boolean, required=True)
    message = graphene.Field(graphene.String, required=True)

    @classmethod
    def mutate(cls, root, info: ResolveInfo, username: str, password: str) -> 'LoginMutation':
        user = authenticate(info.context, username=username, password=password)
        if user is not None:
            login(info.context, user)
            return LoginMutation(user=user, success=True, message=f"Successfully authenticated as {username}")

        return LoginMutation(success=False, message="Invalid credentials")


class LogoutMutation(graphene.Mutation):
    """
    Logout the currently logged in user
    """

    success = graphene.Field(graphene.Boolean, required=True)
    message = graphene.Field(graphene.String, required=True)

    @classmethod
    def mutate(cls, root, info: ResolveInfo) -> 'LogoutMutation':
        logout(info.context)
        return LogoutMutation(success=True, message="Successfully logged out")


class SetEmailMutation(graphene.Mutation):
    """
    Set the email address of the currently logged in user.

    A notification is sent to the old address as well as the new one.
    The notification to the old address includes a link to undo the change.
    The link effectively ends up calling the 'undoChangeEmail' mutation.
    """

    class Arguments:
        email_address = graphene.Argument(graphene.String, required=True)

    user = graphene.Field(types.UserType)
    message = graphene.Field(graphene.String, required=True)
    success = graphene.Field(graphene.Boolean, required=True)

    @classmethod
    def mutate(cls, root, info, email_address):
        user = info.context.user  # type: UserModel
        email_address = UserModel.objects.normalize_email(email_address)

        if not user.is_authenticated:
            return SetEmailMutation(success=False, message="You need to be authenticated to do this")

        if email_address != user.email:
            # only actually change the address if the are not equal
            send_email_changed_notifications(info.context, user, email_address, user.email)
            user.email = email_address
            user.save()

        return SetEmailMutation(success=True, message=f"Successfully changed email address to {email_address}",
                                user=user)


class UndoChangeEmailMutation(graphene.Mutation):
    """
    Undo a previous email update by providing an authCode.
    """

    class Arguments:
        auth_code = graphene.Argument(graphene.String, required=True)

    message = graphene.Field(graphene.String, required=True)
    success = graphene.Field(graphene.Boolean, required=True)

    @classmethod
    def mutate(cls, root, info, auth_code: str):
        validated_data = verify_email_restore_token(auth_code)
        if validated_data is None:
            return UndoChangeEmailMutation(success=False, message="Invalid auth code")

        user = UserModel.objects.get(id__exact=validated_data[0])
        restore_address = UserModel.objects.normalize_email(validated_data[1])
        if user.email != restore_address or True:
            user.email = restore_address
            user.save()
            send_email_restored_notification(info.context, user)

        return UndoChangeEmailMutation(success=True,
                                       message=f"Successfully restored email address to {validated_data[1]}")


class DeleteAccount(graphene.Mutation):
    """
    Completely and irreversibly delete the current user's account with all its data from the system.
    """

    class Arguments:
        pass

    message = graphene.Field(graphene.String, required=True)
    success = graphene.Field(graphene.Boolean, required=True)

    @classmethod
    def mutate(cls, root, info: ResolveInfo) -> 'DeleteAccount':
        user = info.context.user  # type: UserModel
        if not user.is_authenticated:
            return DeleteAccount(success=False, message="Authentication required")

        send_account_deleted_notification(info.context, user)
        user.delete()
        return DeleteAccount(success=True, message="Successfully deleted user account")


class AlterUser(graphene.Mutation):
    """
    Alter the currently logged in user.

    If "password" is changed, the user is notified of the change if they have an email address set.

    If "email" is changed, the previously defined email address is notified and provided a way to undo the change.
    """

    class Arguments:
        user_input = graphene.Argument(types.UserInput, name="user")

    user = graphene.Field(types.UserType)
    message = graphene.Field(graphene.String, required=True)
    success = graphene.Field(graphene.Boolean, required=True)

    @classmethod
    def mutate(cls, root, info, user_input: types.UserInput):
        user = info.context.user  # type: UserModel
        if not user.is_authenticated:
            return AlterUser(success=False, message="Authentication required")

        if user.email != user_input.email:
            new_address = UserModel.objects.normalize_email(user_input.email)
            send_email_changed_notifications(info.context, user, new_address, user.email)
            user.email = new_address
            user.save()

        if user_input.password is not None and not user.check_password(user_input.password):
            user.set_password(user_input.password)
            user.save()
            send_password_changed_notification(info.context, user)

        return AlterUser(success=True, message="Successfully altered user account", user=user)


class CreateBorrower(graphene.Mutation):
    """
    Create a new borrower with the given name.

    If a borrower with that name already exists, the failure is hidden and this mutation returns as if the existing
    borrower is the new one.
    """

    class Arguments:
        name = graphene.Argument(graphene.String, required=True)

    borrower = graphene.Field(types.BorrowerType)
    message = graphene.Field(graphene.String, required=True)
    success = graphene.Field(graphene.Boolean, required=True)

    @classmethod
    def mutate(cls, root, info: ResolveInfo, name: str):
        user = info.context.user  # type: UserModel
        if not user.is_authenticated:
            return CreateBorrower(success=False, message="Authentication required")

        (borrower, created) = BorrowerModel.objects.get_or_create(name=name, lender=user)
        return CreateBorrower(success=True, message=f"Successfully created borrower {name}", borrower=borrower)


class CreateBorrowedItem(graphene.Mutation):
    """
    Create a new borrowed item with the given data.
    """

    class Arguments:
        borrower_id = graphene.Argument(graphene.UUID, required=True, name="borrower")
        specifier = graphene.Argument(graphene.String, required=True)
        description = graphene.Argument(graphene.String)
        date_borrowed = graphene.Argument(graphene.Date)

    borrowed_item = graphene.Field(types.BorrowedItemType)
    message = graphene.Field(graphene.String, required=True)
    success = graphene.Field(graphene.Boolean, required=True)

    @classmethod
    def mutate(cls, root, info: ResolveInfo, borrower_id: UUID, specifier: str, description: str = "",
               date_borrowed: datetime.date = None):
        user = info.context.user  # type: UserModel
        if not user.is_authenticated:
            return CreateBorrowedItem(success=False, message="Authentication required")

        borrower = BorrowerModel.objects.get(id=borrower_id)
        borrowed_item = BorrowedItemModel.objects.create(specifier=specifier, description=description,
                                                         date_borrowed=date_borrowed, borrower=borrower)
        return CreateBorrowedItem(success=True, message=f"Successfully borrowed {specifier} to {borrower.name}",
                                  borrowed_item=borrowed_item)


class DeleteBorrower(graphene.Mutation):
    """
    Delete the borrower with the given name and all their borrowed items.
    """

    class Arguments:
        id = graphene.Argument(graphene.UUID, required=True)

    message = graphene.Field(graphene.String, required=True)
    success = graphene.Field(graphene.Boolean, required=True)

    @classmethod
    def mutate(cls, root, info: ResolveInfo, id: UUID):
        user = info.context.user  # type: UserModel
        if not user.is_authenticated:
            return DeleteBorrower(success=False, message="Authentication required")

        BorrowerModel.objects.get(id=id).delete()
        return DeleteBorrower(success=True, message="Successfully deleted borrower")


class ReturnBorrowedItem(graphene.Mutation):
    """
    Mark the borrowed item with the given id as being returned.

    Currently this simply deletes the borrowed item from the database but that behaviour might change later.
    """

    class Arguments:
        id = graphene.Argument(graphene.UUID, required=True)

    message = graphene.Field(graphene.String, required=True)
    success = graphene.Field(graphene.Boolean, required=True)

    @classmethod
    def mutate(cls, root, info: ResolveInfo, id: UUID):
        user = info.context.user  # type: UserModel
        if not user.is_authenticated:
            return DeleteBorrower(success=False, message="Authentication required")

        BorrowedItemModel.objects.get(id=id).delete()
        return ReturnBorrowedItem(success=True, message="Successfully deleted borrowed item")
