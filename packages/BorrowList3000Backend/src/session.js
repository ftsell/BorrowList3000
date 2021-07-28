import session from "express-session";
import { sessionStore } from "./db/sessions";

export const sessionMiddleware = session({
    secret: 'foobar123',
    store: sessionStore,
    cookie: {
        secure: "auto"
    },
    name: "session",
    resave: false,
    rolling: true,
    saveUninitialized: false,
    unset: "destroy"
})
