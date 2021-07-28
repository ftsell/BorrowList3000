import { BorrowedItemRepository, BorrowerRepository, UserRepository } from "./db/repositories";

export { nuxtModule } from './nuxtModule'
export { getDbConfig } from "./db/models"

export const db = {
    users: UserRepository,
    borrowers: BorrowerRepository,
    borrowedItems: BorrowedItemRepository,
}
