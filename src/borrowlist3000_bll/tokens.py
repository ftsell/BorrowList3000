from datetime import datetime, timedelta
from typing import *
from uuid import UUID

import cbor2
from django.conf import settings
from paseto.protocol import version4 as paseto


def generate_token_secret() -> str:
    return paseto.create_symmetric_key().decode("UTF-8")


def create_email_restore_token(user_id: UUID, old_email: str) -> str:
    payload = {
        "user_id": user_id.bytes,
        "old_email_address": old_email,
        "valid_until": (datetime.utcnow() + timedelta(days=5)).timestamp()
    }

    key = settings.TOKEN_SECRET.encode("UTF-8")
    token = paseto.encrypt(cbor2.dumps(payload), key)
    return token.decode("UTF-8")


def verify_email_restore_token(token: str) -> Optional[Tuple[UUID, str]]:
    """
    Verify and decode a given email restore token

    :return: The id of the user whose email address should be restored and what it should be restored to.
        Or None if the token is not valid.
    """
    key = settings.TOKEN_SECRET.encode("UTF-8")

    try:
        token = cbor2.loads(paseto.decrypt(token.encode("UTF-8"), key))  # type: Dict

        if token["valid_until"] < datetime.utcnow().timestamp():
            return None

        return UUID(bytes=token["user_id"]), token["old_email_address"]
    except Exception:
        return None
