import graphene
from . import resolvers
from . import mutations
from . import types


class Query(graphene.ObjectType):
    logged_in = graphene.Field(graphene.Boolean, resolver=resolvers.resolve_logged_in, required=True)
    me = graphene.Field(types.UserType, resolver=resolvers.resolve_me)
    borrower = graphene.Field(types.BorrowerType, resolver=resolvers.resolve_borrower, description=resolvers.resolve_borrower.__doc__, name=graphene.String(), id=graphene.UUID())


class Mutation(graphene.ObjectType):
    register = mutations.RegisterMutation.Field()
    login = mutations.LoginMutation.Field()
    logout = mutations.LogoutMutation.Field()
    set_email = mutations.SetEmailMutation.Field(deprecation_reason="superseded by 'undo_change_email'")
    undo_change_email = mutations.UndoChangeEmailMutation.Field()
    alter_user = mutations.AlterUser.Field()
    create_borrower = mutations.CreateBorrower.Field()
    delete_borrower = mutations.DeleteBorrower.Field()
    create_borrowed_item = mutations.CreateBorrowedItem.Field()
    return_borrowed_item = mutations.ReturnBorrowedItem.Field()


schema = graphene.Schema(query=Query, mutation=Mutation)
