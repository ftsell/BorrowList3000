import session from "express-session";
import { sessionStore } from "./db/sessions";

export const sessionMiddleware = session({
    secret: process.env.BL_SESSION_SECRET,
    store: sessionStore,
    cookie: {
        secure: "auto",
        httpOnly: true,
        sameSite: "Lax",
        maxAge: process.env.BL_SESSION_MAX_AGE || 30 * 24 * 60 * 60,
    },
    name: "session",
    resave: false,
    rolling: true,
    saveUninitialized: false,
    unset: "destroy",
});
