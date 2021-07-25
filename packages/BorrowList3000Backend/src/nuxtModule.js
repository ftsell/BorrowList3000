import { sequelize } from './db/models'
import {nuxtMiddleware} from './api'

export function nuxtModule() {
    this.addMiddleware({path: "/api", handler: nuxtMiddleware})

    this.nuxt.hook('listen', async () => {
        await sequelize.authenticate()
    })
}
