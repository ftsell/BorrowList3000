import CreateBorrowedItemForm from "~/components/forms/CreateBorrowedItemForm";

export default {
    title: "Forms/CreateBorrowedItem",
    component: CreateBorrowedItemForm
}

const Template= (args, {argTypes}) => ({
    props: Object.keys(argTypes),
    components: {CreateBorrowedItemForm},
    template: "<CreateBorrowedItemForm v-bind='$props' v-on='$props' />"
})

export const Simple = Template.bind({})
