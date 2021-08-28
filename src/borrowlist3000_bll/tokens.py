from uuid import UUID
import cbor2
from django.conf import settings
from paseto.protocol.version4 import create_symmetric_key, encrypt
from datetime import datetime, timedelta


def generate_token_secret() -> str:
    return create_symmetric_key().decode("UTF-8")


def create_email_restore_token(user_id: UUID, old_email: str) -> str:
    payload = {
        "user_id": user_id.hex,
        "old_email_address": old_email,
        "valid_until": (datetime.utcnow() + timedelta(days=5)).timestamp()
    }

    key = settings.TOKEN_SECRET.encode("UTF-8")
    token = encrypt(cbor2.dumps(payload), key)
    return token.decode("UTF-8")


def verify_email_restore_token(token: str):
    # TODO Implement
    raise NotImplementedError()
