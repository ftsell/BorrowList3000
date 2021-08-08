import consola from "consola";
import { Sequelize } from "sequelize";
import Umzug from "umzug";
import migrations from "./migrations";

export function getDbConfig() {
    return {
        host: process.env.BL_DB_HOST || null,
        port: process.env.BL_DB_PORT || null,
        username: process.env.BL_DB_USERNAME || null,
        password: process.env.BL_DB_PASSWORD || null,
        database: process.env.BL_DB_DATABASE || null,
        storage: process.env.BL_DB_DATABASE || null,
        dialect: process.env.BL_DB_DIALECT || null,
        logging:
            process.env.BL_DEBUG === "true"
                ? (msg) => consola.info(msg.toString())
                : false,
    };
}

export const sequelize = new Sequelize(getDbConfig());

export const umzug = new Umzug({
    storage: "sequelize",
    storageOptions: {
        sequelize: sequelize,
        tableName: "Migrations",
    },
    migrations: Umzug.migrationsList(migrations, [
        sequelize.getQueryInterface(),
    ]),
    logging: consola.info
});
