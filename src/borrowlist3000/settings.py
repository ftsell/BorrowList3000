"""
Django settings for borrowlist3000 project.

Generated by 'django-admin startproject' using Django 3.2.6.

For more information on this file, see
https://docs.djangoproject.com/en/3.2/topics/settings/

For the full list of settings and their values, see
https://docs.djangoproject.com/en/3.2/ref/settings/
"""
import os

from configurations import Configuration, values
from pathlib import Path

# Build paths inside the project like this: BASE_DIR / 'subdir'.

BASE_DIR = Path(__file__).resolve().parent.parent


class Base(Configuration):
    ALLOWED_HOSTS = []

    # Application definition

    INSTALLED_APPS = [
        'django.contrib.auth',
        'django.contrib.contenttypes',
        'django.contrib.sessions',
        'django.contrib.messages',
        'django.contrib.staticfiles',
        'graphene_django',
        'borrowlist3000_db',
        'borrowlist3000_api',
    ]

    MIDDLEWARE = [
        'django.middleware.security.SecurityMiddleware',
        'django.contrib.sessions.middleware.SessionMiddleware',
        'django.middleware.common.CommonMiddleware',
        'django.contrib.auth.middleware.AuthenticationMiddleware',
        'django.contrib.messages.middleware.MessageMiddleware',
        'django.middleware.clickjacking.XFrameOptionsMiddleware',
    ]

    ROOT_URLCONF = 'borrowlist3000.urls'

    TEMPLATES = [
        {
            'BACKEND': 'django.template.backends.django.DjangoTemplates',
            'DIRS': [BASE_DIR / 'templates']
            ,
            'APP_DIRS': True,
            'OPTIONS': {
                'context_processors': [
                    'django.template.context_processors.debug',
                    'django.template.context_processors.request',
                    'django.contrib.auth.context_processors.auth',
                    'django.contrib.messages.context_processors.messages',
                ],
            },
        },
    ]

    WSGI_APPLICATION = 'borrowlist3000.wsgi.application'

    # Password validation
    # https://docs.djangoproject.com/en/3.2/ref/settings/#auth-password-validators

    AUTH_PASSWORD_VALIDATORS = [
        {
            'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
        },
        {
            'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
        },
        {
            'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
        },
        {
            'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
        },
    ]

    # Internationalization
    # https://docs.djangoproject.com/en/3.2/topics/i18n/

    LANGUAGE_CODE = 'en-us'

    TIME_ZONE = 'UTC'

    USE_I18N = True

    USE_L10N = True

    USE_TZ = True

    # Static files (CSS, JavaScript, Images)
    # https://docs.djangoproject.com/en/3.2/howto/static-files/

    STATIC_URL = '/static/'

    # Default primary key field type
    # https://docs.djangoproject.com/en/3.2/ref/settings/#default-auto-field

    DEFAULT_AUTO_FIELD = 'django.db.models.BigAutoField'

    AUTH_USER_MODEL = 'borrowlist3000_db.UserModel'

    GRAPHENE = {
        'SCHEMA': 'borrowlist3000_api.schema.schema'
    }

    ###
    # Computed settings
    ###
    @property
    def DATABASES(self):
        # Database
        # https://docs.djangoproject.com/en/3.2/ref/settings/#databases
        return {
            'default': {
                'ENGINE': 'django.db.backends.sqlite3',
                'NAME': self.DB_PATH,
            }
        }

    ###
    # Runtime customizable settings
    ###
    DB_PATH = values.Value(environ_prefix="BL", environ_required=True)


class Dev(Base):
    SECRET_KEY = 'django-insecure-w=wf5uo!qsp=--f18j_wq_uc48813i(7f=ik913*j0j+t-0m5c'
    DEBUG = True

    @classmethod
    def pre_setup(cls):
        os.environ.setdefault("BL_DB_PATH", str(BASE_DIR.absolute().parent / "db.sqlite"))


class Prod(Base):
    SECRET_KEY = values.SecretValue(environ_prefix="BL")
    DEBUG = False
