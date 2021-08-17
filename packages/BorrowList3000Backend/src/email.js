import Nodemailer from "nodemailer";
import { createEmailUndoToken } from "./tokens";
import consola from "consola";

function getMailConfig() {
    let config = {
        enabled: process.env.BL_MAIL_HOST != null,
        nodemailerConfig: {
            host: process.env.BL_MAIL_HOST,
            port: process.env.BL_MAIL_PORT || 25,
            secure: process.env.BL_MAIL_SECURE === "true",
        },
    };

    if (
        process.env.BL_MAIL_USER != null ||
        process.env.BL_MAIL_PASSWORD != null
    ) {
        config.nodemailerConfig.auth = {
            type: "login",
            user: process.env.BL_MAIL_USER,
            pass: process.env.BL_MAIL_PASSWORD,
        };
    }

    return config;
}

export const mailConfig = getMailConfig();
const mailTransporter = Nodemailer.createTransport(mailConfig.nodemailerConfig);

export async function sendEmailUpdatedNotification(
    user,
    newAddress,
    oldAddress
) {
    if (oldAddress != null) {
        const token = await createEmailUndoToken(user, oldAddress);
        consola.info(
            `Sending verification mail with link ${process.env.BL_BASE_URL}/app/undoSetEmail?code=${token}`
        );
    }

    if (mailConfig.enabled) {
        if (oldAddress != null) {
            // send to old address
            mailTransporter.sendMail({
                from: process.env.BL_MAIL_FROM,
                to: oldAddress,
                subject: "Your EMail address has been changed",
                html: `
<html lang="en">
<body>
    <h1>Your EMail address has been changed to ${newAddress}</h1>

    <a href="${process.env.BL_BASE_URL}/app/undoSetEmail?code=${token}">
        <button>Undo</button>
    </a>

    <p>AuthCode is ${token}</p>
</body>
</html>`,
            });
        }

        // send to new address
        mailTransporter.sendMail({
            from: process.env.BL_MAIL_FROM,
            to: newAddress,
            subject: "Your EMail address has been changed",
            html: `
<html lang="en">
<body>
    <h1>Your EMail address has been changed to ${newAddress}</h1>
</body>
</html>`,
        });
    }
}

export async function sendEmailResetNotification(user) {
    if (mailConfig.enabled) {
        mailTransporter.sendMail({
            from: process.env.BL_MAIL_FROM,
            to: user.email,
            subject: "Your EMail address change has been reverted",
            html: `
<html lang="en">
<body>
    <h1>Your Email address has been reset to ${user.email}</h1>
</body>
</html>
            `
        })
    }
}
