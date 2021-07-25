import { assertLoggedIn } from './userController'
import { BorrowerRepository } from '../db/repositories'

export async function createBorrower({name}, {session}) {
    assertLoggedIn(session)
    const borrower = await BorrowerRepository.createBorrower(name, session.username)

    return {
        code: "OK",
        message: `successfully create new borrower ${name}`,
        success: true,
        borrower
    }
}
