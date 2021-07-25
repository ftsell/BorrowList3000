import { sequelize } from './db'
import {nuxtMiddleware} from './nuxtMiddleware'

export function nuxtModule() {
    this.addMiddleware({path: "/api", handler: nuxtMiddleware})

    this.nuxt.hook('listen', async () => {
        await sequelize.authenticate()
    })
}
