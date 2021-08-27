import graphene
from graphene_django import DjangoObjectType

from borrowlist3000_db import models


class UserType(DjangoObjectType):
    class Meta:
        model = models.UserModel
        fields = ("id", "username", "email", "borrowers")


class BorrowerType(DjangoObjectType):
    class Meta:
        model = models.BorrowerModel


class BorrowedItemType(DjangoObjectType):
    class Meta:
        model = models.BorrowedItemModel


class UserInput(graphene.InputObjectType):
    username = graphene.String()
    password = graphene.String()
    email = graphene.String()
