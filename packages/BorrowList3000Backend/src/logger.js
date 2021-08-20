import Consola, { LogLevel } from "consola";

export const logger = Consola.create({
    level: process.env.BL_DEBUG === "true" ? LogLevel.Debug : LogLevel.Info,
});
