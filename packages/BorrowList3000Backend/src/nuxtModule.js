import {sequelize} from './db/models'
import {apiMiddleware} from './api'
import { sessionMiddleware } from "./session";

export function nuxtModule() {
    this.addServerMiddleware(sessionMiddleware)
    this.addServerMiddleware({path: "/api", handler: apiMiddleware})

    this.nuxt.hook("ready", async () => {
        await sequelize.authenticate()

        if (process.env.BL_DB_SYNC === "true") {
            await sequelize.sync()
        }
    })
}
