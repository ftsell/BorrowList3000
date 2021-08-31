FROM docker.io/node:16-alpine as node-build
WORKDIR /app
COPY src/borrowlist3000_frontend/borrowlist_vue/ /app/
RUN yarn install --pure-lockfile
RUN yarn run build

FROM docker.io/tiangolo/uvicorn-gunicorn:python3.8-slim

# add system dependencies
#RUN apk add --no-cache libsodium libsodium-dev libsodium-static
RUN apt-get update
RUN apt-get install -y --no-install-recommends python3-pysodium
RUN pip3 install pipenv
WORKDIR /app

# install dependencies
COPY Pipfile Pipfile.lock /app/
RUN pipenv install --system --ignore-pipfile

# add remaining sources
COPY src /app/
COPY .docker/prestart.sh /app/
RUN ln -sf /app/borrowlist3000/asgi.py /app/main.py
COPY --from=node-build /app/dist /app/borrowlist3000_frontend/borrowlist_vue/dist/

# setup recommended container config
RUN mkdir /app/data
ENV DJANGO_CONFIGURATION=Prod
ENV BL_DB_PATH=/app/data/db.sqlite

# add additional metadata
VOLUME /app/data
LABEL org.opencontainers.image.title="BorrowList3000" \
    org.opencontainers.image.description="Simple application to keep track of who borrowed what stuff" \
    org.opencontainers.image.authors="ftsell <dev@finn-thorben.me>" \
    org.opencontainers.image.url="https://github.com/ftsell/BorrowList3000" \
    org.opencontainers.image.source="https://github.com/ftsell/BorrowList3000" \
    org.opencontainers.image.licenses="MIT"

