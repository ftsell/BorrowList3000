<template>
  <v-form ref="form" v-model="isValid" @submit.prevent="onSubmit">
    <v-container>
      <v-row>
        <v-col>
          <v-text-field v-model="formData.name" label="Name" required :rules="formRules.name" />
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
import gql from "graphql-tag"

export default {
  name: "CreateBorrowerForm",
  data: () => ({
    isValid: true,
    formData: {
      name: ""
    },
    formRules: {
      name: [
        v => !!v || "Name is required"
      ]
    }
  }),
  methods: {
    async onSubmit() {
      this.$refs.form.validate()
      if (this.isValid) {
        const result = await this.$apollo.mutate({
          mutation: gql`mutation ($name: String!) {
            createBorrower(name:$name) {
              success
              message
              borrower {
                name
              }
            }
          }`,
          variables: {
            name: this.formData.name
          }
        })

        if (result.data.createBorrower.success) {
          this.$refs.form.reset()
        }

        this.$store.commit("addBorrower", {
          ...result.data.createBorrower.borrower,
          borrowedItems: []
        })
        this.$emit("onBorrowerCreated", result.data.createBorrower)
      }
    }
  }
};
</script>
