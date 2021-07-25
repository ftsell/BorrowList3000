import sequelize_pkg from 'sequelize'
import { compare, genSaltSync, hashSync } from "bcrypt"

const { DataTypes, Sequelize, Model } = sequelize_pkg

const sqlitePath = ":memory:"
//const sqlitePath = "/home/ftsell/Projects/BorrowList3000/db.sqlite"
export const sequelize = new Sequelize(`sqlite:${sqlitePath}`, {
    logging: false,
})

export class UserModel extends Model {
    async verifyPassword(password) {
        return await compare(password, this.password)
    }
}

UserModel.init({
    username: {
        type: DataTypes.CITEXT,
        primaryKey: true
    },
    password: {
        type: DataTypes.STRING(60),
        allowNull: false,
        set(value) {
            this.setDataValue("password", value)
            this.setDataValue("password", hashSync(value, genSaltSync()))
        }
    },
    email: {
        type: DataTypes.STRING,
        allowNull: true,
        validate: {
            isEmail: true,
        }
    }
}, {
    sequelize,
    tableName: 'Users'
})

export class SessionModel extends Model {

}

SessionModel.init({

}, {
    sequelize,
    tableName: "sessions"
})

export class BorrowerModel extends Model {
}

BorrowerModel.init({
    id: {
        type: DataTypes.UUID,
        defaultValue: Sequelize.UUIDV4,
        primaryKey: true,
    },
    name: {
        type: DataTypes.CITEXT,
        allowNull: false,
    }
}, {
    sequelize,
    tableName: 'Borrowers',
    indexes: [
        {unique: true, fields: ["name", "lender"]}
    ]
})
UserModel.hasMany(BorrowerModel, {
    onUpdate: "CASCADE",
    onDelete: "CASCADE",
    foreignKey: {
        allowNull: false,
        name: 'lender',
    },
    as: "borrowers"
})
BorrowerModel.belongsTo(UserModel, {
    onDelete: 'CASCADE',
    onUpdate: 'CASCADE',
    foreignKey: {
        allowNull: false,
        name: 'lender'
    }
})

export class BorrowedItemModel extends Model {
}

BorrowedItemModel.init({
    id: {
        type: DataTypes.UUID,
        defaultValue: Sequelize.UUIDV4,
        primaryKey: true
    },
    specifier: {
        type: DataTypes.STRING,
        allowNull: false
    },
    description: {
        type: DataTypes.STRING,
        allowNull: true
    },
    dateBorrowed: {
        type: DataTypes.DATE,
        allowNull: false
    }
}, {
    sequelize,
    tableName: 'BorrowedItems'
})
BorrowerModel.hasMany(BorrowedItemModel, {
    onDelete: "CASCADE",
    onUpdate: "CASCADE",
    foreignKey: {
        allowNull: false,
        name: "borrower"
    },
    as: "borrowedItems",
})
BorrowedItemModel.belongsTo(BorrowerModel, {
    onDelete: "CASCADE",
    onUpdate: "CASCADE",
    foreignKey: {
        allowNull: false,
        name: "borrower"
    }
})
