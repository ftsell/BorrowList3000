import Paseto from "paseto.js";

const key = new Paseto.PrivateKey(new Paseto.V2());
(async () => {
    await key.hex(process.env.BL_SECRET)
})();

export async function createEmailUndoToken(user, oldEmailAddress) {
    const data = {
        username: user.username,
        oldEmailAddress,
        validUntil:
            new Date().getTime() +
            (process.env.BL_EMAIL_TOKEN_MAX_AGE || 48 * 60 * 60 * 1000),
    };

    return await key.protocol().sign(JSON.stringify(data), key);
}

export async function verifyEmailUndoToken(token) {
    const publicKey = await key.public();
    return JSON.parse(await publicKey.protocol().verify(token, publicKey));
}
