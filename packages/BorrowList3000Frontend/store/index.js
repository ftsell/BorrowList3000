import assert from "assert";

export const state = () => ({
    user: null,
});

export const mutations = {
    updateUser(state, newUser) {
        state.user = newUser;
    },
    addBorrower(state, newBorrower) {
        assert(state.user != null);
        state.user.borrowers.push(newBorrower);
    },
    addBorrowedItem(state, { borrowerName, newItem }) {
        assert(state.user != null);

        const borrower = state.user.borrowers.find(
            (b) => b.name === borrowerName
        );
        assert(borrower != null);

        borrower.borrowedItems.push(newItem);
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
            return state.user.borrowers.find(b => b.name === borrowerName);
        }
        return null;
    }
};
