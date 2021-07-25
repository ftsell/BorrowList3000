import { BorrowedItemModel, BorrowerModel, UserModel } from './models'

export class UserRepository {
    static async listUsers() {
        return await UserModel.findAll({ include: { all: true, nested: true, required: true } })
    }

    static async getUserByUsername(username) {
        return await UserModel.findOne({
            include: { all: true, nested: true, required: true }, where: {
                username: username
            }
        })
    }

    static async createUser(username, password, email) {
        return await UserModel.create({ username, password, email })
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
