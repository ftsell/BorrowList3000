import { sequelize, umzug } from "./db";
import { apiMiddleware } from "./api";
import { sessionMiddleware } from "./session";
import path from "path";
import consola from "consola";

export function nuxtModule() {
    this.addServerMiddleware(sessionMiddleware);
    this.addServerMiddleware({ path: "/api", handler: apiMiddleware });

    this.addPlugin({
        src: path.resolve(__dirname, "nuxtPlugin.js"),
        mode: "server",
    });

    this.nuxt.hook("ready", async () => {
        consola.info("Connecting to database");
        await sequelize.authenticate();

        if (process.env.BL_DB_MIGRATE === "true") {
            consola.info("Performing database migration");
            await umzug.up()
        }
    });
}
