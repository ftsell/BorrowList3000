import { UserRepository } from '../db/repositories'

export async function register({ username, password, email }) {
    try {
        const user = await UserRepository.createUser(username, password, email)
        return {
            success: true,
            message: 'successfully created a new user account',
            code: 'OK',
            user: user
        }
    } catch (e) {
        console.error(e)
        return {
            success: false,
            message: 'a user with that username already exists',
            code: 'ERR_USER_ALREADY_EXISTS'
        }
    }
}

export async function login({ username, password }, { session }) {
    const user = await UserRepository.getUserByUsername(username)
    if (user != null && await user.verifyPassword((password))) {
        session.loggedIn = true
        return {
            success: true,
            message: `successfully logged in as ${username}`,
            code: 'OK',
            user: user,
            sessionId: 'TODO'
        }
    } else {
        return {
            success: false,
            message: `could not login as ${username}, either the user does not exist or the provided credentials are incorrect`,
            code: 'ERR_LOGIN_FAILED'
        }
    }
}

export async function logout({}, request) {
    request.session = null

    return {
        success: true,
        message: `successfully logged out`,
        code: 'OK'
    }
}
