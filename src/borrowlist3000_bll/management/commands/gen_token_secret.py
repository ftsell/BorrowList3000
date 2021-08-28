from django.core.management.base import BaseCommand
from borrowlist3000_bll.tokens import generate_token_secret


class Command(BaseCommand):
    help = 'Generate a new random token secret used for signing email reset tokens'

    def handle(self, *args, **options):
        token = generate_token_secret()
        self.stdout.write("The secret is " + self.style.SUCCESS(token))
        self.stdout.write()
        self.stdout.write("Apply it by setting the environment variable " + self.style.SQL_KEYWORD("BL_TOKEN_SECRET") + " to " + self.style.SQL_KEYWORD(token))
