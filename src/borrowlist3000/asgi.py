"""
ASGI config for borrowlist3000 project.

It exposes the ASGI callable as a module-level variable named ``application``.

For more information on this file, see
https://docs.djangoproject.com/en/3.2/howto/deployment/asgi/
"""

import os

from configurations import importer

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'borrowlist3000.settings')
importer.install()

from django.core.asgi import get_asgi_application

app = get_asgi_application()
