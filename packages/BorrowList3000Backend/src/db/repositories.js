import { BorrowedItemModel, BorrowerModel, UserModel } from './models'

export class UserRepository {
    static async listUsers() {
        return await UserModel.findAll({ include: { all: true, nested: true, required: true } })
    }

    static async getUserById(id) {
        return null
    }

    static async createUser(username) {
        return await UserModel.create({ username })
    }
}

export class BorrowerRepository {
    static async createBorrower(name, lender) {
        return await BorrowerModel.create({ name, lender: lender.username })
    }
}

export class BorrowedItemRepository {
    static async createBorrowedItem(specifier, description, dateBorrowed, borrower) {
        return await BorrowedItemModel.create({ specifier, description, dateBorrowed, borrower: borrower.id })
    }
}
