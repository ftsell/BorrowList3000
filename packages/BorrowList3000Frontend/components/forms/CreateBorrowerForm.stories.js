import CreateBorrowerForm from "~/components/forms/CreateBorrowerForm";

export default {
    title: "Forms/CreateBorrower",
    component: CreateBorrowerForm,
}

const Template = (args, {argTypes}) => ({
    props: Object.keys(argTypes),
    components: {CreateBorrowerForm},
    template: "<CreateBorrowerForm v-bind='$props' v-on='$props' />",
})

export const Simple = Template.bind({})
