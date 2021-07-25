import sequelize_pkg from "sequelize"
const { DataTypes, Sequelize, Model } = sequelize_pkg

export const sequelize = new Sequelize('sqlite::memory')

export class User extends Model {}
User.init({
    username: {
        type: DataTypes.STRING,
        primaryKey: true,
    }
}, {
    sequelize
})
