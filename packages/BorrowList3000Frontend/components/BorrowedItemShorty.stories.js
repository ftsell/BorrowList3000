import BorrowedItemShorty from "~/components/BorrowedItemShorty";

export default {
    title: "Shorties/BorrowedItem",
    component: BorrowedItemShorty,
}
const Template= (args, {argTypes}) => ({
    props: Object.keys(argTypes),
    components: {BorrowerShorty: BorrowedItemShorty},
    template: "<BorrowedItemShorty v-bind='$props' v-on='$props' />"
})

export const WithoutDescription = Template.bind({})
WithoutDescription.args = {
    item: {
        specifier: "Book",
        dateBorrowed: "yesterday",
    }
}

export const WithDescription = Template.bind({})
WithDescription.args = {
    item: {
        specifier: "Book",
        description: "My favourite book and I want it back! <3",
        dateBorrowed: "yesterday",
    }
}
