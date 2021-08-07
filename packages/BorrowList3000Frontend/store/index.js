import assert from "assert";

export const state = () => ({
    user: null,
    alerts: [],
});

function sortBorrowers(state) {
    state.user.borrowers = state.user.borrowers.sort(
        (a, b) => b.borrowedItems.length - a.borrowedItems.length
    );
}

function sortBorrowedItems(state) {
    for (const borrower of state.user.borrowers) {
        borrower.borrowedItems = borrower.borrowedItems.sort((a, b) =>
            a.specifier.localeCompare(b.specifier)
        );
    }
}

export const mutations = {
    clearUserSpecificData(state) {
        state.user = null;
    },
    updateUser(state, newUser) {
        state.user = newUser;
        sortBorrowers(state);
        sortBorrowedItems(state);
    },
    addBorrower(state, newBorrower) {
        assert(state.user != null);
        state.user.borrowers.push(newBorrower);
        sortBorrowers(state);
        sortBorrowedItems(state);
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
        sortBorrowers(state);
        sortBorrowedItems(state);
    },
    removeBorrowedItem(state, { id }) {
        assert(state.user != null);
        for (const borrower of state.user.borrowers) {
            borrower.borrowedItems = borrower.borrowedItems.filter(
                (item) => item.id !== id
            );
        }
        sortBorrowers(state);
    },
    showAlert(state, alert) {
        state.alerts.push(alert);
    },
    hideAlert(state, alert) {
        state.alerts = state.alerts.filter((a) => a !== alert);
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
    showAlertWithTimeout({ commit }, { alert, timeout }) {
        commit("showAlert", alert);
        setTimeout(() => commit("hideAlert", alert), timeout);
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
