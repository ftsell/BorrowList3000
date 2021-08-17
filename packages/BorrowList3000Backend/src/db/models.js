import { compare, genSaltSync, hashSync } from "bcrypt";
import { sequelize } from "./index";
import { Model, DataTypes, Sequelize } from "sequelize";

const commonScopes = {
    apiPublic: {
        attributes: { exclude: ["createdAt", "updatedAt"] },
    },
};

export class UserModel extends Model {
    async verifyPassword(password) {
        return await compare(password, this.password);
    }
}

UserModel.init(
    {
        username: {
            type: DataTypes.CITEXT,
            primaryKey: true,
        },
        password: {
            type: DataTypes.STRING(60),
            allowNull: false,
            set(value) {
                this.setDataValue("password", value);
                this.setDataValue("password", hashSync(value, genSaltSync()));
            },
        },
        email: {
            type: DataTypes.STRING,
            allowNull: true,
            validate: {
                isEmail: true,
            },
        },
    },
    {
        sequelize,
        tableName: "Users",
        scopes: {
            ...commonScopes,
            apiPublic: {
                ...commonScopes.apiPublic,
                attributes: {
                    exclude: [
                        ...commonScopes.apiPublic.attributes.exclude,
                        "password",
                    ],
                },
            },
        },
    }
);

export class BorrowerModel extends Model {}

BorrowerModel.init(
    {
        id: {
            type: DataTypes.UUID,
            defaultValue: Sequelize.UUIDV4,
            primaryKey: true,
        },
        name: {
            type: DataTypes.CITEXT,
            allowNull: false,
        },
    },
    {
        sequelize,
        tableName: "Borrowers",
        indexes: [
            {
                unique: true,
                fields: ["name", "lenderUsername"],
                name: "unique_lenderName_per_user",
            },
        ],
        scopes: {
            ...commonScopes,
        },
    }
);
UserModel.hasMany(BorrowerModel, {
    onUpdate: "CASCADE",
    onDelete: "CASCADE",
    foreignKey: {
        allowNull: false,
        name: "lenderUsername",
    },
    as: "borrowers",
});
BorrowerModel.belongsTo(UserModel, {
    onDelete: "CASCADE",
    onUpdate: "CASCADE",
    foreignKey: {
        allowNull: false,
        name: "lenderUsername",
    },
    as: "lender",
});

export class BorrowedItemModel extends Model {}

BorrowedItemModel.init(
    {
        id: {
            type: DataTypes.UUID,
            defaultValue: Sequelize.UUIDV4,
            primaryKey: true,
        },
        specifier: {
            type: DataTypes.STRING,
            allowNull: false,
        },
        description: {
            type: DataTypes.STRING,
            allowNull: true,
        },
        dateBorrowed: {
            type: DataTypes.DATE,
            allowNull: false,
            validate: {
                isDate: true,
            },
        },
    },
    {
        sequelize,
        tableName: "BorrowedItems",
        scopes: {
            ...commonScopes,
        },
    }
);
BorrowerModel.hasMany(BorrowedItemModel, {
    onDelete: "CASCADE",
    onUpdate: "CASCADE",
    foreignKey: {
        allowNull: false,
        name: "borrowerId",
    },
    as: "borrowedItems",
});
BorrowedItemModel.belongsTo(BorrowerModel, {
    onDelete: "CASCADE",
    onUpdate: "CASCADE",
    foreignKey: {
        allowNull: false,
        name: "borrowerId",
    },
    as: "borrower",
});
