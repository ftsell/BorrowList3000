import { assertLoggedIn } from "./userController";
import { BorrowerRepository } from "../db/repositories";

export async function createBorrower({ name }, { session }) {
    assertLoggedIn(session);

    if (
        (await BorrowerRepository.getBorrower(name, session.username)) != null
    ) {
        return {
            code: "ERR_BORROWER_ALREADY_EXISTS",
            message: `a borrower with that name already exists`,
            success: false,
        };
    }

    const borrower = await BorrowerRepository.createBorrower(
        name,
        session.username
    );
    return {
        code: "OK",
        message: `successfully created new borrower ${name}`,
        success: true,
        borrower,
    };
}

export async function deleteBorrower({ name }, { session }) {
    assertLoggedIn(session);

    const borrower = await BorrowerRepository.getBorrower(
        name,
        session.username
    );
    if (borrower == null) {
        return {
            code: "ERR_BORROWER_DOES_NOT_EXIST",
            message: `borrower ${name} does not exist`,
            success: false,
        };
    }

    await borrower.destroy();
    return {
        code: "OK",
        message: `successfully deleted borrower ${name}`,
        success: true,
    }
}
