from django.http import HttpRequest
from django.template.loader import render_to_string
from django.core.mail import send_mail
from django.conf import settings

from borrowlist3000_db.models import UserModel
from . import tokens


def send_email_changed_notifications(request: HttpRequest, user: UserModel, new_email_address: str,
                                     old_email_address: str):
    """
    Send notifications to a users old and new email address that their email has been changed.
    """
    if old_email_address is not None:
        restore_token = tokens.create_email_restore_token(user.id, old_email_address)
        mail_content = render_to_string("mails/email_changed_notification.html", {
            "user": user,
            "new_email_address": new_email_address,
            "restore_token": restore_token,
        }, request)
        send_mail(
            subject="Your email address has been changed",
            message=None,
            from_email=settings.EMAIL_FROM,
            recipient_list=[old_email_address],
            html_message=mail_content,
        )

    mail_content = render_to_string("mails/email_changed_notification.html", {
        "user": user,
        "new_email_address": new_email_address,
    }, request)
    send_mail(
        subject="Your email address has been changed",
        message=None,
        from_email=settings.EMAIL_FROM,
        recipient_list=[new_email_address],
        html_message=mail_content,
    )


def send_email_restored_notification(request: HttpRequest, user: UserModel):
    """
    Send a notification to the user's current email address that it has been reverted via a restore token.

    For this to make sense it is expected that the user has its address already restored so that that is the *current*
    one.
    """
    mail_content = render_to_string("mails/email_restored_notification.html", {
        "user": user
    }, request)
    send_mail(
        subject="Your email address has been restored",
        message=None,
        from_email=settings.EMAIL_FROM,
        recipient_list=[user.email],
        html_message=mail_content
    )


def send_password_changed_notification(request: HttpRequest, user: UserModel):
    """
    Send a notification to the user's email address that their password has been changed.
    """
    mail_content = render_to_string("mails/password_changed_notification.html", {}, request)
    send_mail(
        subject="Your password has been changed",
        message=None,
        from_email=settings.EMAIL_FROM,
        recipient_list=[user.email],
        html_message=mail_content,
    )


def send_account_deleted_notification(request: HttpRequest, user: UserModel):
    """
    Send a notification to the user's email address that their account has been deleted.
    """
    mail_content = render_to_string("mails/account_deleted_notification.html", {
        "user": user
    }, request)
    send_mail(
        subject="Your account has been deleted",
        message=None,
        from_email=settings.EMAIL_FROM,
        recipient_list=[user.email],
        html_message=mail_content
    )


def send_username_changed_notification(request: HttpRequest, user: UserModel):
    """
    Send a notification to the user's email address that their username has been changed.
    """
    mail_content = render_to_string("mails/username_changed_notification.html", {
        "user": user
    }, request)
    send_mail(
        subject="Your username has been changed",
        message=None,
        from_email=settings.EMAIL_FROM,
        recipient_list=[user.email],
        html_message=mail_content
    )
