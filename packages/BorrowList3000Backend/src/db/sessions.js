import connectSessionSequelize from "connect-session-sequelize";
import session from "express-session";
import { sequelize } from "./index";
import { Model } from "sequelize";

export class SessionModel extends Model {
}

SessionModel.init({}, {
    sequelize,
    tableName: "sessions",
});

const SequelizeStore = connectSessionSequelize(session.Store);

export const sessionStore = new SequelizeStore({
    db: sequelize,
    tableName: "Sessions",
    expiration: process.env.BL_SESSION_MAX_AGE || 30 * 24 * 60 * 60,
});
