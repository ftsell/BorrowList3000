import {
    BorrowedItemRepository,
    BorrowerRepository,
    UserRepository,
} from "./db/repositories";

export { nuxtModule, getProxyTrust } from "./nuxtModule";
export { getDbConfig } from "./db";

export const db = {
    users: UserRepository,
    borrowers: BorrowerRepository,
    borrowedItems: BorrowedItemRepository,
};
