import {
    BorrowedItemRepository,
    BorrowerRepository,
    UserRepository,
} from "./db/repositories";

export { nuxtModule } from "./nuxtModule";
export { getDbConfig } from "./db/models";
export { getProxyTrust } from "./api/index";

export const db = {
    users: UserRepository,
    borrowers: BorrowerRepository,
    borrowedItems: BorrowedItemRepository,
};
