D=$(dirname $(realpath $0))

export BL_BASE_URL=http://localhost:3000
export BL_SESSION_SECRET=DEVELOPMENT_IS_AWESOME
export BL_DB_DIALECT=sqlite
export BL_DB_DATABASE=$D/db.sqlite
export BL_DB_MIGRATE=true
export BL_DEBUG=true
export BL_ALLOW_DB_RESET=true
