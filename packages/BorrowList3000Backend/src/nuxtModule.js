import {sequelize} from './db/models'
import {nuxtMiddleware} from './api'

export function nuxtModule() {
    this.addServerMiddleware({path: "/api", handler: nuxtMiddleware})

    this.nuxt.hook('listen', async () => {
        await sequelize.authenticate()
        await sequelize.sync()
    })
}
