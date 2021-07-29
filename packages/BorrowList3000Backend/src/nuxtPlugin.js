export default ({app}, inject) => {
    if (process.server) {
        const { db } = require("borrowlist3000backend");
        inject("db", db)
    }
}
