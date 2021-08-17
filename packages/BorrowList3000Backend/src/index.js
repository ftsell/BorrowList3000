import {
    BorrowedItemRepository,
    BorrowerRepository,
    UserRepository,
} from "./db/repositories";

export { nuxtModule, getProxyTrust } from "./nuxtModule";
export { getDbConfig } from "./db";
export { mailConfig } from "./email"

export const db = {
    users: UserRepository,
    borrowers: BorrowerRepository,
    borrowedItems: BorrowedItemRepository,
};
