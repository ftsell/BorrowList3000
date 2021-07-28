import {sequelize} from './db/models'
import {nuxtMiddleware} from './api'

export function nuxtModule() {
    this.addServerMiddleware({path: "/api", handler: nuxtMiddleware})

    this.nuxt.hook("ready", async () => {
        await sequelize.authenticate()

        if (process.env.BL_DB_SYNC === "true") {
            await sequelize.sync()
        }
    })
}
