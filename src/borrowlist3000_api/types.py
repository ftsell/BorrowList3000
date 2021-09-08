import graphene
from graphene_django import DjangoObjectType

from borrowlist3000_db import models
from . import resolvers


class AppConfigType(graphene.ObjectType):
    email_enabled = graphene.Field(graphene.Boolean, description="Whether email related features are enabled")


class BorrowerType(DjangoObjectType):
    class Meta:
        model = models.BorrowerModel


class UserType(DjangoObjectType):
    class Meta:
        model = models.UserModel
        fields = ("id", "username", "email", "borrowers")

    borrower = graphene.Field(BorrowerType, resolver=resolvers.resolve_borrower, description=resolvers.resolve_borrower.__doc__, name=graphene.String(), id=graphene.UUID())
    borrowers = graphene.Field(graphene.List(BorrowerType), resolver=resolvers.resolve_borrowers, description=resolvers.resolve_borrowers.__doc__, name_contains=graphene.String())


class BorrowedItemType(DjangoObjectType):
    class Meta:
        model = models.BorrowedItemModel


class UserInput(graphene.InputObjectType):
    username = graphene.String()
    password = graphene.String()
    email = graphene.String()
