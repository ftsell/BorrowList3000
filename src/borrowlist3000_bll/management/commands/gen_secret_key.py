from django.core.management.base import BaseCommand
from django.core.management.utils import get_random_secret_key


class Command(BaseCommand):
    help = 'Generate a new random Django secret key'

    def handle(self, *args, **options):
        key = get_random_secret_key()
        self.stdout.write("The secret key is " + self.style.SUCCESS(key))
        self.stdout.write()
        self.stdout.write(
            "Apply it by setting the environment variable " + self.style.SQL_KEYWORD(f"BL_SECRET_KEY={key}"))
