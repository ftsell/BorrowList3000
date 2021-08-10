import { apiMiddleware } from "./src/api/index";
import express from "express";
import { sequelize, umzug } from "./src/db";
import { sessionMiddleware } from "./src/session";
import { sessionStore } from "./src/db/sessions";
import { getProxyTrust } from './src';

const app = express();
app.use("trust proxy", getProxyTrust());
app.use(sessionMiddleware);
app.use("/api", apiMiddleware);

sequelize
    .authenticate()
    .then(async () => {
        if (process.env.BL_DB_MIGRATE === "true") {
            await sessionStore.sync();
            await umzug.up();
        }
    })
    .then(() => {
        app.listen(8000, () => {
            console.log("API server listening on port http://localhost:8000");
        });
    })
    .catch(console.log);
