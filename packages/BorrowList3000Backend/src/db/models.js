import sequelize_pkg from "sequelize";
import { compare, genSaltSync, hashSync } from "bcrypt";

const { DataTypes, Sequelize, Model } = sequelize_pkg;

export function getDbConfig() {
    return {
        host: process.env.BL_DB_HOST || null,
        port: process.env.BL_DB_PORT || null,
        username: process.env.BL_DB_USERNAME || null,
        password: process.env.BL_DB_PASSWORD || null,
        database: process.env.BL_DB_DATABASE || null,
        storage: process.env.BL_DB_DATABASE || null,
        dialect: process.env.BL_DB_DIALECT || null,
        logging: process.env.BL_DEBUG === "true" ? console.log : false
    };
}

export const sequelize = new Sequelize(getDbConfig());

const commonScopes = {
    apiPublic: {
        attributes: { exclude: ["createdAt", "updatedAt"] }
    }
};

export class UserModel extends Model {
    async verifyPassword(password) {
        return await compare(password, this.password);
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
            this.setDataValue("password", value);
            this.setDataValue("password", hashSync(value, genSaltSync()));
        }
    }
}, {
    sequelize,
    tableName: "Users",
    scopes: {
        ...commonScopes,
        apiPublic: {
            ...commonScopes.apiPublic,
            attributes: {
                exclude: [...commonScopes.apiPublic.attributes.exclude, "password"]
            }
        }
    }
});

export class BorrowerModel extends Model {
}

BorrowerModel.init({
    id: {
        type: DataTypes.UUID,
        defaultValue: Sequelize.UUIDV4,
        primaryKey: true
    },
    name: {
        type: DataTypes.CITEXT,
        allowNull: false
    }
}, {
    sequelize,
    tableName: "Borrowers",
    indexes: [
        { unique: true, fields: ["name", "lender"] }
    ],
    scopes: {
        ...commonScopes
    }
});
UserModel.hasMany(BorrowerModel, {
    onUpdate: "CASCADE",
    onDelete: "CASCADE",
    foreignKey: {
        allowNull: false,
        name: "lender"
    },
    as: "borrowers",
});
BorrowerModel.belongsTo(UserModel, {
    onDelete: "CASCADE",
    onUpdate: "CASCADE",
    foreignKey: {
        allowNull: false,
        name: "lender"
    }
});

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
        allowNull: false,
        validate: {
            isDate: true
        }
    }
}, {
    sequelize,
    tableName: "BorrowedItems",
    scopes: {
        ...commonScopes,
    }
});
BorrowerModel.hasMany(BorrowedItemModel, {
    onDelete: "CASCADE",
    onUpdate: "CASCADE",
    foreignKey: {
        allowNull: false,
        name: "borrower"
    },
    as: "borrowedItems"
});
BorrowedItemModel.belongsTo(BorrowerModel, {
    onDelete: "CASCADE",
    onUpdate: "CASCADE",
    foreignKey: {
        allowNull: false,
        name: "borrower"
    }
});
