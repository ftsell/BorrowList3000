import uuid

from django.contrib.auth.hashers import make_password
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager
from django.utils.crypto import get_random_string
from django.db import models


def uuid_default() -> uuid.UUID:
    return uuid.uuid4()


def password_reset_token_default() -> str:
    return get_random_string(60)


class UserManager(BaseUserManager):
    def create_user(self, username: str, password: str, email: str = None) -> 'UserModel':
        username = UserModel.normalize_username(username)
        email = self.normalize_email(email)
        password = make_password(password)

        user = UserModel(username=username, password=password, email=email)
        user.save(using=self.db)
        return user

    def create_superuser(self, username: str, password: str, email: str = None) -> 'UserModel':
        return self.create_user(username, password, email)


class UserModel(AbstractBaseUser):
    id = models.UUIDField(primary_key=True, default=uuid_default)
    username = models.CharField(max_length=60, unique=True)
    email = models.EmailField(blank=True)

    USERNAME_FIELD = "username"
    EMAIL_FIELD = "email"

    objects = UserManager()


class PasswordReset(models.Model):
    token = models.CharField(primary_key=True, max_length=60, default=password_reset_token_default)
    user = models.ForeignKey(to=UserModel, on_delete=models.CASCADE, related_name="password_resets", related_query_name="password_reset")
    created_at = models.DateTimeField(auto_now_add=True)


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
    description = models.CharField(max_length=240, blank=True)
    date_borrowed = models.DateField(auto_now_add=True)
    borrower = models.ForeignKey(BorrowerModel, on_delete=models.CASCADE, related_name="borrowed_items",
                                 related_query_name="borrowed_item")
