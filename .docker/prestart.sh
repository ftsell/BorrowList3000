#!/usr/bin/sh
set -e

if [ -z "$BL_TOKEN_SECRET" ]; then
    echo "Generating a new token secret since none is defined."
    export DJANGO_CONFIGURATION=Dev
    ./manage.py gen_token_secret
    exit 1
fi

./manage.py migrate
