import { nuxtMiddleware } from './src'
import express from 'express'
import { sequelize } from './src/db/models'
import { UserRepository } from './src/db/repositories'

const app = express()
app.use('/api', nuxtMiddleware)

sequelize.authenticate()
    .then(() => sequelize.sync())
    .then(async () => {
        await UserRepository.createUser("ftsell", "foobar123")
    })
    .then(() => {
        app.listen(8000, () => {
            console.log('API server listening on port http://localhost:8000')
        })
    }).catch(console.log)
