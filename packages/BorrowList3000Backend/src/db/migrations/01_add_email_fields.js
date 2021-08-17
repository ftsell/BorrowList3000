import { DataTypes } from "sequelize";

export default {
    name: "01_add_email_fields",
    /**
     * Apply the migration
     *
     * @param query {QueryInterface}
     */
    async up(query) {
        await query.addColumn("Users", "email", {
            type: DataTypes.STRING,
            allowNull: true,
            validate: {
                isEmail: true,
            },
        });
    },

    /**
     * Reverse the migration
     *
     * @param query {QueryInterface}
     */
    async down(query) {
        await query.removeColumn("Users", "email");
    },
};
