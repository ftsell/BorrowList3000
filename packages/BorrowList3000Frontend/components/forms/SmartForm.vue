<template>
  <v-form ref='form' lazy-validation @submit.prevent='onFormSubmit'>
    <v-container class='d-flex flex-row align-baseline'>
      <p>Lend</p>
      <v-text-field class='mx-4' v-model='formData.itemSpecifier' label='item' :rules='formRules.itemSpecifier'
                    outlined />
      <p>to</p>
      <v-text-field class='mx-4' v-model='formData.borrowerName' label='person' :rules='formRules.borrowerName'
                    outlined />
      <v-btn outlined type='submit'>Submit</v-btn>
    </v-container>
  </v-form>
</template>

<script>
import gql from 'graphql-tag'

const GRAPHQL_QUERIES = {
  createBorrowedItem: gql`
    mutation ($borrowerName:String!, $itemSpecifier:String!) {
        createBorrowedItem(borrower: $borrowerName, specifier: $itemSpecifier, dateBorrowed: "2021/01/01") {
            success
            message
            borrowedItem {
                specifier
                description
                dateBorrowed
            }
        }
    }`,
  createBorrowerAndBorrowedItem: gql`
    mutation ($borrowerName:String!, $itemSpecifier:String!) {
        createBorrower(name: $borrowerName) {
            success
            message
            borrower {
                name
            }
        }
        createBorrowedItem(borrower: $borrowerName, specifier: $itemSpecifier, dateBorrowed: "2021/01/01") {
            success
            message
            borrowedItem {
                specifier
                description
                dateBorrowed
            }
        }
    }`
}

export default {
  name: 'SmartForm',
  data: () => ({
    formData: {
      borrowerName: '',
      itemSpecifier: ''
    },
    formRules: {}
  }),
  methods: {
    onFormSubmit() {
      this.formRules = {
        borrowerName: [
          v => !!v || 'Borrower name is required'
        ],
        itemSpecifier: [
          v => !!v || 'Item is required'
        ]
      }

      this.$nextTick(async () => {
        if (this.$refs.form.validate()) {
          this.$emit('onSubmit', this.formData)
          await this.doApiSubmit()
          this.resetForm()
        }
      })
    },
    resetForm() {
      this.formRules = {}
      this.$refs.form.reset()
    },
    async doApiSubmit() {
      let result
      if (this.$store.getters.getBorrower(this.formData.borrowerName) == null) {
        result = await this.$apollo.mutate({
          mutation: GRAPHQL_QUERIES.createBorrowerAndBorrowedItem,
          variables: this.formData
        })

        if (result.data.createBorrower.success === true) {
          this.$store.commit("addBorrower", {
            ...result.data.createBorrower.borrower,
            borrowedItems: []
          })
        }
      } else {
        result = await this.$apollo.mutate({
          mutation: GRAPHQL_QUERIES.createBorrowedItem,
          variables: this.formData
        })
      }

      if (result.data.createBorrowedItem.success === true) {
        this.$store.commit("addBorrowedItem", {
          borrowerName: this.formData.borrowerName,
          newItem: result.data.createBorrowedItem.borrowedItem
        })
      }
    }
  }
}
</script>
