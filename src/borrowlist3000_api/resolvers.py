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

    If both name and id are given, both must match.
    If none are given, null is returned.
    """
    user = info.context.user    # type: UserModel
    if not user.is_authenticated:
        return None

    if (name is None or name == "") and id is None:
        return None

    query = BorrowerModel.objects.filter(lender=user)
    if name is not None and name != "":
        query = query.filter(name__iexact=name)
    if id is not None:
        query = query.filter(id=id)

    return query.get()


def resolve_borrowers(root, info: ResolveInfo, name_contains: str = None) -> List[BorrowerModel]:
    """
    Search for borrowers using the given criteria.
    If no criteria are given, all borrowers are returned.

    name_contains: The names of borrowers must at least contain this string.
    """
    user = info.context.user    # type: UserModel
    if not user.is_authenticated:
        return []

    query = BorrowerModel.objects.filter(lender=user)
    if name_contains is not None and name_contains != "":
        query.filter(name__icontains=name_contains)

    return list(query)
