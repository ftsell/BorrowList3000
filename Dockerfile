FROM docker.io/node:16-alpine

# add system dependencies
RUN apk add --no-cache tini

# add sources
WORKDIR /app/src
COPY package.json yarn.lock LICENSE ./
COPY packages ./packages
RUN find \( -name .idea -o -name .nuxt -o -name node_modules \) -exec rm -rf {} +

# build complete project
ENV NUXT_TELEMETRY_DISABLED=1
ENV BL_DB_DIALECT=sqlite
RUN yarn install &&\
    yarn cache clean --all
#WORKDIR /app/src/packages/BorrowList3000Frontend
RUN yarn --cwd ./packages/BorrowList3000Frontend run build
ENV BL_DB_DIALECT=""

# configure runtime information
ENTRYPOINT ["/sbin/tini", "--"]
CMD ["yarn", "--cwd", "./packages/BorrowList3000Frontend", "run", "start", "-H", "0.0.0.0", "-p", "8000"]
EXPOSE 8000

# add additional metadata
LABEL org.opencontainers.image.title="BorrowList3000" \
    org.opencontainers.image.description="Simple application to keep track of who borrowed what stuff" \
    org.opencontainers.image.authors="ftsell <dev@finn-thorben.me>" \
    org.opencontainers.image.url="https://github.com/ftsell/BorrowList3000" \
    org.opencontainers.image.source="https://github.com/ftsell/BorrowList3000" \
    org.opencontainers.image.licenses="MIT"

