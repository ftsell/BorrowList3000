<template>
  <v-form ref="form" v-model="isValid" @submit.prevent="onSubmit">
    <v-container>
      <v-row>
        <v-col>
          <v-text-field v-model="formData.specifier" label="Specifier" required :rules="formRules.specifier" />
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-text-field v-model="formData.description" label="Description" />
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-date-picker v-model="formData.dateBorrowed" />
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-btn @click="onSubmit" color="primary" outlined>Create</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>

<script>
import gql from "graphql-tag";

function formatDate(date) {
  return `${date.getFullYear()}-${date.getMonth()}-${date.getDay()}`;
}

export default {
  name: "CreateBorrowedItemForm",
  data: () => ({
    isValid: true,
    formData: {
      specifier: "",
      description: "",
      dateBorrowed: formatDate(new Date())
    },
    formRules: {
      specifier: [
        v => !!v || "Item specifier is required"
      ]
    }
  }),
  props: {
    borrowerName: {
      type: String,
      required: true
    }
  },
  methods: {
    async onSubmit() {
      this.$refs.form.validate();
      if (this.isValid) {
        const result = await this.$apollo.mutate({
          mutation: gql`mutation ($specifier: String!, $borrower:String!, $description:String, $dateBorrowed:Date!) {
            createBorrowedItem(specifier:$specifier,borrower:$borrower,dateBorrowed:$dateBorrowed,description:$description) {
              success
              message
              borrowedItem {
                specifier
                description
                dateBorrowed
              }
            }
          }`,
          variables: {
            specifier: this.formData.specifier,
            borrower: this.borrowerName,
            description: this.formData.description,
            dateBorrowed: this.formData.dateBorrowed
          }
        });

        if (result.data.createBorrowedItem.success) {
          this.$refs.form.reset();
        }


        this.$store.commit("addBorrowedItem", {
          borrowerName: this.borrowerName,
          newItem: result.data.createBorrowedItem.borrowedItem
        })
        this.$emit("onItemCreated", result.data.createBorrowedItem);
      }
    }
  }
};
</script>
