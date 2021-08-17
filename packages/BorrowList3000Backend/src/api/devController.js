import consola from "consola";
import { UserModel } from "../db/models";
import { SessionModel } from "../db/sessions";

export async function resetDb() {
    if (process.env.BL_ALLOW_DB_RESET === "true") {
        consola.warn("Truncating all database tables");

        await Promise.all([UserModel.truncate(), SessionModel.truncate()]);

        return true;
    }
    return false;
}
