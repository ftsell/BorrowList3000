import colors from "vuetify/es5/util/colors";
import { nuxtModule as borrowlistBackend, getDbConfig, getProxyTrust } from "borrowlist3000backend";

const baseUrl = process.env.BL_BASE_URL

export default {
    publicRuntimeConfig: {
        debug: process.env.BL_DEBUG === "true" || false,
        baseUrl,
    },
    privateRuntimeConfig: {
        db: getDbConfig(),
        sessionSecret: process.env.BL_SESSION_SECRET,
        trust_proxy: getProxyTrust()
    },

    // Global page headers: https://go.nuxtjs.dev/config-head
    head: {
        titleTemplate: "%s - Borrowlist 3000",
        title: "Borrowlist 3000",
        meta: [
            { charset: "utf-8" },
            { name: "viewport", content: "width=device-width, initial-scale=1" },
            { hid: "description", name: "description", content: "Simple application to keep track of who borrowed what stuff" },
            { name: "format-detection", content: "telephone=no" }
        ],
        link: [
            { rel: "icon", type: "image/x-icon", href: "/favicon.ico" }
        ]
    },

    // Global CSS: https://go.nuxtjs.dev/config-css
    css: [],

    // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
    plugins: [],

    // Auto import components: https://go.nuxtjs.dev/config-components
    components: true,

    // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
    buildModules: [
        // https://go.nuxtjs.dev/eslint
        "@nuxtjs/eslint-module",
        // https://go.nuxtjs.dev/stylelint
        "@nuxtjs/stylelint-module",
        // https://go.nuxtjs.dev/vuetify
        "@nuxtjs/vuetify"
    ],

    // Modules: https://go.nuxtjs.dev/config-modules
    modules: [
        "nuxt-helmet",
        // https://go.nuxtjs.dev/pwa
        "@nuxtjs/pwa",
        "@nuxtjs/apollo",
        borrowlistBackend
    ],

    render: {
        csp: true
    },

    // PWA module configuration: https://go.nuxtjs.dev/pwa
    pwa: {
        manifest: {
            lang: "en"
        }
    },

    // Vuetify module configuration: https://go.nuxtjs.dev/config-vuetify
    vuetify: {
        customVariables: ["~/assets/variables.scss"],
        theme: {
            dark: false,
            themes: {
                dark: {
                    primary: colors.blue.darken2,
                    accent: colors.grey.darken3,
                    secondary: colors.amber.darken3,
                    info: colors.teal.lighten1,
                    warning: colors.amber.base,
                    error: colors.deepOrange.accent4,
                    success: colors.green.accent3
                }
            }
        }
    },

    apollo: {
        clientConfigs: {
            default: {
                httpEndpoint: `${baseUrl}/api/graphql`
            }
        }
    },

    storybook: {
        decorators: [
            "<v-app><story/></v-app>"
        ],
        parameters: {
            actions: {
                argTypesRegex: "^on.*",
            }
        }
    },

    // helmet options
    // @see https://helmetjs.github.io/docs/
    helmet: {
        // use nuxt builtin CSP handling
        contentSecurityPolicy: false
    },

    // Build Configuration: https://go.nuxtjs.dev/config-build
    build: {}
};
