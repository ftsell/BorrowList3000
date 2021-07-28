import BorrowerShorty from "~/components/BorrowerShorty";

export default {
    title: "Shorties/Borrower",
    component: BorrowerShorty,
}
const Template= (args, {argTypes}) => ({
    props: Object.keys(argTypes),
    components: {BorrowerShorty},
    template: "<BorrowerShorty v-bind='$props' v-on='$props' />"
})

export const WithoutItems = Template.bind({})
WithoutItems.args = {
    borrower: {
        name: "Ole",
        borrowedItems: [],
    }
}

export const WithItems = Template.bind({})
WithItems.args = {
    borrower: {
        ...WithoutItems.args.borrower,
        borrowedItems: ["Book", "Lamp", "Laptop", "300$"]
    }
}
