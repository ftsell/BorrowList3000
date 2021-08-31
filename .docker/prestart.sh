#!/usr/bin/sh
set -e

if [ -z "$BL_TOKEN_SECRET" ]; then
    echo "\n"
    echo "Generating a new token secret since none is defined."
    export DJANGO_CONFIGURATION=Dev
    ./manage.py gen_token_secret
fi

if [ -z "$BL_SECRET_KEY" ]; then
    echo "\n"
    echo "Generating a new django secret key since none is defined."
    export DJANGO_CONFIGURATION=Dev
    ./manage.py gen_secret_key
    echo ""
fi

if [ -z "$BL_TOKEN_SECRET" -o -z "$BL_SECRET_KEY" ]; then
    exit 1
fi

./manage.py check --deploy
./manage.py migrate
