import session from "express-session";
import { sessionStore } from "./db/sessions";

export const sessionMiddleware = session({
    secret: process.env.BL_SESSION_SECRET,
    store: sessionStore,
    cookie: {
        secure: "auto",
        httpOnly: true,
        sameSite: "Lax",
    },
    name: "session",
    resave: false,
    rolling: true,
    saveUninitialized: false,
    unset: "destroy",
});
