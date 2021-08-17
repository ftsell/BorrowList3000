D=$(dirname $(realpath $0))

export BL_BASE_URL=http://localhost:3000
export BL_SECRET=afb018b7aadd84f9676f7b71841aa6007315065b48b2547539b0916e554597b1
export BL_DB_DIALECT=sqlite
export BL_DB_DATABASE=$D/db.sqlite
export BL_DB_MIGRATE=true
export BL_DEBUG=true
export BL_ALLOW_DB_RESET=true
