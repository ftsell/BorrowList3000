import Auth from "~/components/forms/AuthForm";

export default {
    title: "Forms/Auth",
    component: Auth,
}

const Template = (args, {argTypes}) => ({
    props: Object.keys(argTypes),
    components: {Auth},
    template: "<Auth v-bind='$props' v-on='$props' />",
})

export const Simple = Template.bind({})
