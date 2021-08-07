<template>
  <v-form ref='form' lazy-validation @submit.prevent='onFormSubmit'>
    <v-container class='d-flex flex-row align-baseline'>
      <p>Lend</p>
      <v-text-field v-model='formData.item' class='mx-4' label='item' :rules='formRules.itemSpecifier'
                    outlined />
      <p>to</p>
      <v-autocomplete v-model='formData.borrowerName' class='mx-4' label='person' :rules='formRules.borrowerName'
                      :items='autocompleteItems' :search-input.sync='borrowerSearchInput' outlined hide-no-data />
      <v-btn outlined type='submit'>Submit</v-btn>
    </v-container>
  </v-form>
</template>

<script>
import gql from 'graphql-tag'

const ITEM_WITH_DESCRIPTION_REGEX = /^(.*)+?( \((.+)\))$/

const GRAPHQL_QUERIES = {
  createBorrowedItem: gql`
    mutation ($borrowerName:String!, $itemSpecifier:String!, $itemDescription: String) {
        createBorrowedItem(borrower: $borrowerName, specifier: $itemSpecifier, description: $itemDescription, dateBorrowed: "2021/01/01") {
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
    mutation ($borrowerName:String!, $itemSpecifier:String!, $itemDescription: String) {
        createBorrower(name: $borrowerName) {
            success
            message
            borrower {
                name
            }
        }
        createBorrowedItem(borrower: $borrowerName, specifier: $itemSpecifier, description: $itemDescription, dateBorrowed: "2021/01/01") {
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
      item: ''
    },
    formRules: {},
    borrowerSearchInput: ''
  }),
  computed: {
    autocompleteItems() {
      if (this.borrowerSearchInput) {
        return [...this.$store.getters.borrowerNames, this.borrowerSearchInput]
      } else {
        return this.$store.getters.borrowerNames
      }
    },
    itemSpecifier() {
      const match = ITEM_WITH_DESCRIPTION_REGEX.exec(this.formData.item)
      if (match) {
        return match[1]
      } else {
        return this.formData.item
      }
    },
    itemDescription() {
      const match = ITEM_WITH_DESCRIPTION_REGEX.exec(this.formData.item)
      if (match) {
        return match[3]
      } else {
        return null
      }
    }
  },
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
          variables: {
            borrowerName: this.formData.borrowerName,
            itemSpecifier: this.itemSpecifier,
            itemDescription: this.itemDescription
          }
        })

        if (result.data.createBorrower.success === true) {
          this.$store.commit('addBorrower', {
            ...result.data.createBorrower.borrower,
            borrowedItems: []
          })
        }
      } else {
        result = await this.$apollo.mutate({
          mutation: GRAPHQL_QUERIES.createBorrowedItem,
          variables: {
            borrowerName: this.formData.borrowerName,
            itemSpecifier: this.itemSpecifier,
            itemDescription: this.itemDescription
          }
        })
      }

      if (result.data.createBorrowedItem.success === true) {
        this.$store.commit('addBorrowedItem', {
          borrowerName: this.formData.borrowerName,
          newItem: result.data.createBorrowedItem.borrowedItem
        })
      }
    },
  }
}
</script>
