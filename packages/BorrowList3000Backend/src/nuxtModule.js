import {sequelize} from './db/models'
import {apiMiddleware} from './api'
import { sessionMiddleware } from "./session";
import path from "path";
import consola from "consola";

export function nuxtModule() {
    this.addServerMiddleware(sessionMiddleware)
    this.addServerMiddleware({path: "/api", handler: apiMiddleware})

    this.addPlugin({
        src: path.resolve(__dirname, "nuxtPlugin.js"),
        mode: "server"
    })

    this.nuxt.hook("ready", async () => {
        consola.info("Connecting to database")
        await sequelize.authenticate()

        if (process.env.BL_DB_SYNC === "true") {
            consola.info("Syncing database schema to database")
            await sequelize.sync()
        }
    })
}
