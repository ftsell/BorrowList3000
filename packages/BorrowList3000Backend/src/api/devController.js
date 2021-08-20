import { UserModel } from "../db/models";
import { SessionModel } from "../db/sessions";
import {logger} from "../logger";

export async function resetDb() {
    if (process.env.BL_ALLOW_DB_RESET === "true") {
        logger.withTag("db").warn("Truncating all database tables");

        await Promise.all([UserModel.truncate(), SessionModel.truncate()]);

        return true;
    }
    return false;
}
