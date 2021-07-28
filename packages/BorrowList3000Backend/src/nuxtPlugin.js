import { db } from "borrowlist3000backend";

export default ({app}, inject) => {
    inject("db", db)
}
