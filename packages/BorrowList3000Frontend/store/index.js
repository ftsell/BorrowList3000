import assert from "assert";

export const state = () => ({
    user: null,
});

function sortUsers(state) {
    state.user.borrowers = state.user.borrowers.sort(
        (a, b) => b.borrowedItems.length - a.borrowedItems.length
    );
}

export const mutations = {
    updateUser(state, newUser) {
        state.user = newUser;
        sortUsers(state);
    },
    addBorrower(state, newBorrower) {
        assert(state.user != null);
        state.user.borrowers.push(newBorrower);
        sortUsers(state);
    },
    deleteBorrower(state, borrowerName) {
        assert(state.user != null);
        assert(
            state.user.borrowers.find((b) => b.name === borrowerName) != null
        );

        state.user.borrowers = state.user.borrowers.filter(
            (b) => b.name !== borrowerName
        );
    },
    addBorrowedItem(state, { borrowerName, newItem }) {
        assert(state.user != null);

        const borrower = state.user.borrowers.find(
            (b) => b.name === borrowerName
        );
        assert(borrower != null);

        borrower.borrowedItems.push(newItem);
        sortUsers(state);
    },
    removeBorrowedItem(state, { id }) {
        assert(state.user != null);
        for (const borrower of state.user.borrowers) {
            borrower.borrowedItems = borrower.borrowedItems.filter(
                (item) => item.id !== id
            );
        }
        sortUsers(state);
    },
};

export const actions = {
    async nuxtServerInit({ commit }, { req, $db }) {
        if (req.session.loggedIn === true) {
            const user = await $db.users.getUserByUsername(
                req.session.username,
                true
            );
            commit("updateUser", user.toJSON());
        }
    },
};

export const getters = {
    getBorrower: (state) => (borrowerName) => {
        if (state.user != null) {
            return state.user.borrowers.find((b) => b.name === borrowerName);
        }
        return null;
    },
    borrowerNames(state) {
        if (state.user != null) {
            return state.user.borrowers.map((b) => b.name);
        }
        return [];
    },
};