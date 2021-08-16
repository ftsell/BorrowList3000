export function assertLoggedIn(session) {
    if (!session.loggedIn) {
        throw "you need to be logged in to do this";
    }
}
