import connectSessionSequelize from 'connect-session-sequelize'
import session from 'express-session'
import { sequelize } from './models'

const SequelizeStore = connectSessionSequelize(session.Store)

export const sessionStore = new SequelizeStore({
    db: sequelize,
    tableName: "Sessions"
})
sessionStore.sync()
