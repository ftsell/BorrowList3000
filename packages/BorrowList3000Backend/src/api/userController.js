import { UserRepository } from "../db/repositories";
import { assertLoggedIn } from "./utils";
import {
    sendEmailResetNotification,
    sendEmailUpdatedNotification, sendPasswordUpdatedNotification,
} from "../email";
import { verifyEmailUndoToken } from "../tokens";

export function isRequesterLoggedIn(parent, args, { req: { session } }) {
    return !!session.loggedIn;
}

export async function register(parent, { username, password }) {
    try {
        const user = await UserRepository.createUser(username, password);
        return {
            success: true,
            message: "successfully created a new user account",
            code: "OK",
            user: user,
        };
    } catch (e) {
        console.error(e);
        return {
            success: false,
            message: "a user with that username already exists",
            code: "ERR_USER_ALREADY_EXISTS",
        };
    }
}

export async function login(
    parent,
    { username, password },
    { req: { session } }
) {
    const user = await UserRepository.getUserByUsername(username);
    if (user != null && (await user.verifyPassword(password))) {
        session.loggedIn = true;
        session.username = username;
        return {
            success: true,
            message: `successfully logged in as ${username}`,
            code: "OK",
            user: user,
            sessionId: "TODO",
        };
    } else {
        return {
            success: false,
            message: `could not login as ${username}, either the user does not exist or the provided credentials are incorrect`,
            code: "ERR_LOGIN_FAILED",
        };
    }
}

export async function logout(parent, args, { req }) {
    req.session = null;

    return {
        success: true,
        message: `successfully logged out`,
        code: "OK",
    };
}

export async function getOwnUser(parent, args, { req: { session } }) {
    assertLoggedIn(session);
    return await UserRepository.getUserByUsername(session.username, true);
}

export async function alterUser(parent, args, { req: { session } }) {
    assertLoggedIn(session);

    const user = await UserRepository.getUserByUsername(session.username);

    // handle email change
    if (args.user.email != null) {
        if (args.user.email !== user.email) {
            await sendEmailUpdatedNotification(user, args.user.email, user.email);
        }
        user.email = args.user.email;
    }

    // handle password change
    if (args.user.password != null && !await user.verifyPassword(args.user.password)) {
        user.password = args.user.password
        await sendPasswordUpdatedNotification(user)
    }

    await user.save();
    return user;
}

export async function undoChangeEmail(parent, args) {
    const token = await verifyEmailUndoToken(args.authCode);

    if (token.validUntil < new Date().getTime()) {
        return {
            success: false,
            code: "OK",
            message: `Code has expired`,
        };
    }

    const user = await UserRepository.getUserByUsername(token.username);
    user.email = token.oldEmailAddress;
    await Promise.all([
        user.save(),
        sendEmailResetNotification(user),
    ]);

    return {
        success: true,
        code: "OK",
        message: `Email address was successfully reset to ${token.oldEmailAddress}`,
    };
}
