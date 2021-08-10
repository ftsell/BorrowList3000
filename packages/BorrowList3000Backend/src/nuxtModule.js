import { sequelize, umzug } from "./db";
import { apiMiddleware } from "./api";
import { sessionMiddleware } from "./session";
import path from "path";
import consola from "consola";
import { sessionStore } from "./db/sessions";
import express from "express";

export function getProxyTrust() {
    if (process.env.BL_TRUST_PROXY === "true") {
        return true;
    } else if (
        typeof process.env.BL_TRUST_PROXY == "string" &&
        process.env.BL_TRUST_PROXY !== ""
    ) {
        return process.env.BL_TRUST_PROXY;
    } else {
        return false;
    }
}

const app = express();
app.set("trust proxy", getProxyTrust());
app.use(sessionMiddleware);
app.use("/api", apiMiddleware);

export function nuxtModule() {
    this.addServerMiddleware(app);

    this.addPlugin({
        src: path.resolve(__dirname, "nuxtPlugin.js"),
        mode: "server",
    });

    this.nuxt.hook("ready", async () => {
        consola.info("Connecting to database");
        await sequelize.authenticate();

        if (process.env.BL_DB_MIGRATE === "true") {
            consola.info("Performing database migration");
            await sessionStore.sync();
            await umzug.up();
        }
    });
}
