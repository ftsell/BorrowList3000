from django.http import HttpRequest
from django.template.loader import render_to_string
from django.core.mail import send_mail

from borrowlist3000_db.models import UserModel
from . import tokens


def send_email_changed_notifications(request: HttpRequest, user: UserModel, new_email_address: str,
                                     old_email_address: str):
    """
    Send notifications to a users old and new email address that their email has been changed.
    """
    if old_email_address is not None:
        restore_token = tokens.create_email_restore_token(user.id, old_email_address)
        mail_content = render_to_string("email_changed_notification.html.j2", {
            "user": user,
            "new_email_address": new_email_address,
            "restore_token": restore_token,
        }, request)
        send_mail(
            subject="Your email address has been changed",
            message=None,
            from_email=None,
            recipient_list=[old_email_address],
            html_message=mail_content,
        )

    mail_content = render_to_string("email_changed_notification.html.j2", {
        "user": user,
        "new_email_address": new_email_address,
    }, request)
    send_mail(
        subject="Your email address has been changed",
        message=None,
        from_email=None,
        recipient_list=[new_email_address],
        html_message=mail_content,
    )
