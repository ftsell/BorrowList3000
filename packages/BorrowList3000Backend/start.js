import { nuxtMiddleware } from './src'
import express from 'express'
import { sequelize } from './src/db/models'

const app = express()
app.use('/api', nuxtMiddleware)

sequelize.authenticate()
    .then(async () => {
        if (process.env.BL_DB_SYNC === "true") {
            await sequelize.sync()
        }
    })
    .then(() => {
        app.listen(8000, () => {
            console.log('API server listening on port http://localhost:8000')
        })
    }).catch(console.log)
