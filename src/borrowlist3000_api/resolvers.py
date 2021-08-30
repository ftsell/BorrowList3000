from typing import *
from uuid import UUID

from graphql import ResolveInfo

from borrowlist3000_db.models import UserModel, BorrowerModel


def resolve_me(root, info: ResolveInfo):
    user = info.context.user  # type: UserModel
    if not user.is_authenticated:
        return None

    return user


def resolve_logged_in(root, info: ResolveInfo) -> bool:
    return info.context.user.is_authenticated


def resolve_borrower(root, info: ResolveInfo, name: str = None, id: UUID = None) -> Optional[BorrowerModel]:
    """
    Get a specific borrower either by name or by id.

    If bot name and id are given, both must match.
    If none are given, null is returned.
    """
    user = info.context.user    # type: UserModel
    if not user.is_authenticated:
        return None

    if (name is None or name == "") and id is None:
        return None

    query = {
        "lender": user
    }
    if name is not None and  name != "":
        query["name__iexact"] = name
    if id is not None:
        query["id"] = id

    return BorrowerModel.objects.get(**query)
