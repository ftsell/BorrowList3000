import graphene
from . import resolvers
from . import mutations
from . import types


class Query(graphene.ObjectType):
    me = graphene.Field(types.UserType, resolver=resolvers.resolve_me)


class Mutation(graphene.ObjectType):
    register = mutations.RegisterMutation.Field()
    login = mutations.LoginMutation.Field()
    logout = mutations.LogoutMutation.Field()
    set_email = mutations.SetEmailMutation.Field()
    undo_change_email = mutations.UndoChangeEmailMutation.Field()
    alter_user = mutations.AlterUser.Field()
    create_borrower = mutations.CreateBorrower.Field()
    delete_borrower = mutations.DeleteBorrower.Field()
    create_borrowed_item = mutations.CreateBorrowedItem.Field()
    return_borrowed_item = mutations.ReturnBorrowedItem.Field()


schema = graphene.Schema(query=Query, mutation=Mutation)
