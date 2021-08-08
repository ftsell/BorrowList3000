import Sequelize from 'sequelize';

export default {
    name: "00_initial",
    /**
     * Apply the migration
     *
     * @param query {QueryInterface}
     */
    async up(query) {
        const defaultAttributes = {
            createdAt: {
                type: Sequelize.DataTypes.DATE,
                allowNull: false,
            },
            updatedAt: {
                type: Sequelize.DataTypes.DATE,
                allowNull: false,
            },
        };

        await query.createTable("Users", {
            ...defaultAttributes,
            username: {
                type: Sequelize.DataTypes.CITEXT,
                primaryKey: true,
            },
            password: {
                type: Sequelize.DataTypes.STRING(60),
                allowNull: false,
            },
        });

        await query.createTable("Borrowers", {
            ...defaultAttributes,
            id: {
                type: Sequelize.DataTypes.UUID,
                defaultValue: Sequelize.UUIDV4,
                primaryKey: true,
            },
            name: {
                type: Sequelize.DataTypes.CITEXT,
                allowNull: true,
            },
            lenderUsername: {
                type: Sequelize.DataTypes.CITEXT,
                allowNull: false,
                references: {
                    model: "Users",
                    key: "username",
                },
                onDelete: "CASCADE",
                onUpdate: "CASCADE",
            },
        });
        await query.addIndex("Borrowers",  {
            unique: true,
            fields: ["name", "lenderUsername"],
            name: "unique_lenderName_per_user"
        })

        await query.createTable("BorrowedItems", {
            ...defaultAttributes,
            id: {
                type: Sequelize.DataTypes.UUID,
                defaultValue: Sequelize.UUIDV4,
                primaryKey: true,
            },
            specifier: {
                type: Sequelize.DataTypes.STRING,
                allowNull: false,
            },
            description: {
                type: Sequelize.DataTypes.STRING,
                allowNull: true,
            },
            dateBorrowed: {
                type: Sequelize.DataTypes.DATE,
                allowNull: false,
            },
            borrowerId: {
                type: Sequelize.DataTypes.UUID,
                allowNull: false,
                references: {
                    model: "Borrowers",
                    key: "id",
                },
                onDelete: "CASCADE",
                onUpdate: "CASCADE",
            },
        });
    },

    /**
     * Reverse the migration
     *
     * @param query {QueryInterface}
     */
    async down(query) {
        await query.dropAllTables({});
    },
};
