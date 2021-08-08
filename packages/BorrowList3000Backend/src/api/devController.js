import { sequelize } from "../db";
import consola from "consola";

export async function resetDb() {
    if (process.env.BL_ALLOW_DB_RESET === "true") {
        consola.warn("Truncating all database tables")
        await sequelize.truncate()
        return true
    }
    return false
}
