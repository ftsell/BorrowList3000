import Auth from "~/components/Auth";

export default {
    title: "Forms/Auth",
    component: Auth,
}

const Template = (args, {argTypes}) => ({
    props: Object.keys(argTypes),
    template: "<Auth v-bind='$props' v-on='$props' />",
})

export const Simple = Template.bind({})
