import { BorrowedItemRepository, BorrowerRepository } from "../db/repositories";
import { assertLoggedIn } from "./utils";

async function assertItemIsLentByCurrentUser(item, session) {
    const lender = await (await item.getBorrower()).getLender();
    if (lender.username !== session.username) {
        throw new Error(`Item (${item.id}) is not lent by current user`);
    }
}

export async function createBorrowedItem(
    parent,
    { borrower, specifier, dateBorrowed, description },
    { req: { session } }
) {
    assertLoggedIn(session);

    const borrowerObject = await BorrowerRepository.getBorrower(
        borrower,
        session.username
    );
    if (borrowerObject == null) {
        throw `borrower ${borrower} does not exist`;
    }

    const borrowedItem = await BorrowedItemRepository.createBorrowedItem(
        specifier,
        description,
        dateBorrowed,
        borrowerObject.id
    );
    return {
        code: "OK",
        message: `successfully created borrowed item ${specifier}`,
        success: true,
        borrowedItem: borrowedItem,
    };
}

export async function returnBorrowedItem(parent, { id }, { req: { session } }) {
    assertLoggedIn(session);

    const item = await BorrowedItemRepository.getItemById(id);
    if (item == null) {
        throw `item with id ${id} does not exist`;
    }

    await assertItemIsLentByCurrentUser(item, session);

    await item.destroy();
    return {
        code: "OK",
        message: `successfully returned ${item.specifier}`,
        success: true,
    };
}
