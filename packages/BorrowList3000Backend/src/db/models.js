import sequelize_pkg from 'sequelize'

const { DataTypes, Sequelize, Model } = sequelize_pkg

export const sequelize = new Sequelize('sqlite::memory:')

export class UserModel extends Model {
}

UserModel.init({
    username: {
        type: DataTypes.STRING,
        primaryKey: true
    }
}, {
    sequelize,
    tableName: 'Users'
})

export class BorrowerModel extends Model {
}

BorrowerModel.init({
    id: {
        type: DataTypes.UUID,
        defaultValue: Sequelize.UUIDV4,
        primaryKey: true
    },
    name: {
        type: DataTypes.STRING,
        allowNull: false
    }
}, {
    sequelize,
    tableName: 'Borrowers'
})
UserModel.hasMany(BorrowerModel, {
    onUpdate: "CASCADE",
    onDelete: "CASCADE",
    foreignKey: {
        allowNull: false,
        name: 'lender'
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
