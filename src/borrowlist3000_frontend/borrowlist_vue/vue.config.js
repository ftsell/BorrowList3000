module.exports = {
    integrity: true,
    devServer: {
        proxy: {
            "^/api": {
                target: "http://localhost:8000",
            },
        },
    },
};
