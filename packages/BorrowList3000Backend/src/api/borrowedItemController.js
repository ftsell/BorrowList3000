import {BorrowedItemRepository, BorrowerRepository} from "../db/repositories";
import {assertLoggedIn} from "./userController";

export async function createBorrowedItem({borrower, specifier, dateBorrowed, description}, {session}) {
    assertLoggedIn(session)

    const borrowerObject = await BorrowerRepository.getBorrower(borrower, session.username)
    if (borrowerObject == null) {
        throw `borrower ${borrower} does not exist`
    }

    const borrowedItem = await BorrowedItemRepository.createBorrowedItem(specifier, description, dateBorrowed, borrowerObject.id)
    return {
        code: "OK",
        message: `successfully created borrowed item ${specifier}`,
        success: true,
        borrowedItem: borrowedItem
    }
}
