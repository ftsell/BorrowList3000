import { apiMiddleware } from './src/api/index'
import express from 'express'
import { sequelize } from './src/db'
import { sessionMiddleware } from "./src/session";

const app = express()
app.use(sessionMiddleware)
app.use('/api', apiMiddleware)

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
