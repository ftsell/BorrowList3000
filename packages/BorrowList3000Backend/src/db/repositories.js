import { BorrowedItemModel, BorrowerModel, UserModel } from "./models";

export class UserRepository {
    static async getUserByUsername(username, limitFieldsToApiPublic = false) {
        if (limitFieldsToApiPublic) {
            return await UserModel.scope("apiPublic").findOne({
                include: {
                    model: BorrowerModel.scope("apiPublic"),
                    as: "borrowers",
                    include: {
                        model: BorrowedItemModel.scope("apiPublic"),
                        as: "borrowedItems"
                    }
                },
                where: {
                    username: username
                }
            });
        } else {
            return await UserModel.findOne({
                include: { all: true, nested: true },
                where: {
                    username: username
                }
            });
        }
    }

    static async createUser(username, password) {
        return await UserModel.create({ username, password });
    }
}

export class BorrowerRepository {
    static async createBorrower(name, lenderUsername) {
        return await BorrowerModel.create({ name, lenderUsername });
    }

    static async getBorrower(name, lenderUsername, loadEager = false) {
        return await BorrowerModel.findOne({
            include: loadEager ? { all: true, nested: true } : null,
            where: {
                name,
                lenderUsername
            }
        });
    }
}

export class BorrowedItemRepository {
    static async createBorrowedItem(specifier, description, dateBorrowed, borrowerId) {
        return await BorrowedItemModel.create({ specifier, description, dateBorrowed, borrowerId });
    }
}
