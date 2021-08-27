import uuid

from django.contrib.auth.models import AbstractBaseUser
from django.db import models


def uuid_default() -> uuid.UUID:
    return uuid.uuid4()


class UserModel(AbstractBaseUser):
    id = models.UUIDField(primary_key=True, default=uuid_default)
    username = models.CharField(max_length=60, unique=True)
    email = models.EmailField(blank=True)

    USERNAME_FIELD = "username"
    EMAIL_FIELD = "email"


class BorrowerModel(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid_default)
    name = models.CharField(max_length=120)
    lender = models.ForeignKey(UserModel, on_delete=models.CASCADE, related_name="borrowers",
                               related_query_name="borrower")

    class Meta:
        constraints = [
            models.UniqueConstraint(fields=["lender", "name"], name="unique_borrowers_per_user"),
        ]


class BorrowedItemModel(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid_default)
    specifier = models.CharField(max_length=120)
    description = models.CharField(max_length=240, blank=True),
    date_borrowed = models.DateField(auto_now_add=True)
    borrower = models.ForeignKey(BorrowerModel, on_delete=models.CASCADE, related_name="borrowed_items",
                                 related_query_name="borrowed_item")
