from graphql import ResolveInfo

from borrowlist3000_db.models import UserModel


def resolve_me(root, info: ResolveInfo):
    user = info.context.user  # type: UserModel
    if not user.is_authenticated:
        return None

    return user


def resolve_logged_in(root, info: ResolveInfo) -> bool:
    return info.context.user.is_authenticated
