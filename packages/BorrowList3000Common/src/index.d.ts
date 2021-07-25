interface User {
    username: string,
    borrowers: Borrower[],
}

interface Borrower {
    name: string
    borrowedItems: BorrowedItem,
}

interface BorrowedItem {
    specifier: string,
    description?: string,
    dateBorrowed: string,
}
