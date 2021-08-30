<template>
  <v-form ref="form" lazy-validation @submit.prevent="onFormSubmit">
    <v-container class="d-flex flex-row align-center">
      <p>Lend</p>
      <v-text-field
        v-model="formData.item"
        class="mx-4"
        label="item"
        :rules="formRules.itemSpecifier"
        outlined
        hint="Book (my favourite one)"
      />
      <p>to</p>
      <v-autocomplete
        v-model="formData.borrowerName"
        class="mx-4"
        label="person"
        :rules="formRules.borrowerName"
        :items="autocompleteItems"
        :search-input.sync="borrowerSearchInput"
        outlined
        hide-no-data
      />
      <v-btn outlined type="submit">Submit</v-btn>
    </v-container>
  </v-form>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import gql from "graphql-tag";
import { FormValidator } from "@/utils";

const DESCRIPTION_REGEX = /\((.+)\)$/;

const GRAPHQL_QUERIES = {
  createBorrowedItem: gql`
    mutation (
      $borrowerId: UUID!
      $itemSpecifier: String!
      $itemDescription: String
    ) {
      createBorrowedItem(
        borrower: $borrowerId
        specifier: $itemSpecifier
        description: $itemDescription
      ) {
        success
        message
        borrowedItem {
          id
          specifier
          description
          dateBorrowed
        }
      }
    }
  `,
  createBorrower: gql`
    mutation ($borrowerName: String!) {
      createBorrower(name: $borrowerName) {
        success
        borrower {
          id
          name
        }
      }
    }
  `,
};

@Component({
  apollo: {
    allBorrowers: {
      query: gql`
        {
          me {
            borrowers {
              id
              name
            }
          }
        }
      `,
      update: (result) => result.me.borrowers,
    },
  },
})
export default class SmartForm extends Vue {
  /** The verified data that results from entering data into the form and selecting a borrower **/
  formData = {
    borrowerName: "",
    item: "",
  };

  /**
   * Rules for all form fields.
   *
   * They are initialized to be empty so that a non-submitted form does not irritate the user.
   * Upon pressing submit, these validators get replaced by their real counterparts.
   **/
  formRules: { [x: string]: FormValidator[] } = {
    borrowerName: [() => true],
    itemSpecifier: [() => true],
  };

  /**
   * The current input into the borrower autocomplete field that is used to search for a borrower.
   */
  borrowerSearchInput = "";

  /**
   * Names of all existing borrowers.
   */
  allBorrowers: any[] = [];

  /**
   * Borrower names that should be listed in the borrower autocomplete field based on the current search input.
   */
  get autocompleteItems(): string[] {
    if (this.borrowerSearchInput) {
      return [
        this.borrowerSearchInput,
        ...this.allBorrowers
          .map((b) => b.name)
          .filter((name) => name.includes(this.borrowerSearchInput)),
      ];
    } else {
      return this.allBorrowers.map((b) => b.name);
    }
  }

  /**
   * The v-form component
   *
   * { @link https://vuetifyjs.com/en/api/v-form/ }
   */
  get formElement(): any {
    return this.$refs.form as Element;
  }

  /**
   * The specifier part of the borrowed item input.
   *
   * `formData.item` allows inputting something like "Book (my favourite one)" in which case this getter returns only
   * the "Book" part.
   */
  get itemSpecifier(): string {
    const match = DESCRIPTION_REGEX.exec(this.formData.item);
    if (match) {
      return this.formData.item.replace(match[0], "").trim();
    } else {
      return this.formData.item.trim();
    }
  }

  /**
   * The description part of the borrowed item input.
   *
   * `formData.item` allows inputting something like "Book (my favourite one)" in which case this getter returns only
   * the "my favourite one" part.
   */
  get itemDescription(): string | null {
    const match = DESCRIPTION_REGEX.exec(this.formData.item);
    if (match) {
      return match[1].trim();
    } else {
      return null;
    }
  }

  /**
   * Event listener that gets called when the form is submitted.
   *
   * It:
   *  1) Validates the form properly
   *  2) Submits changes to the API
   *  3) Resets the form
   */
  onFormSubmit() {
    this.formRules = {
      borrowerName: [(v: string) => !!v || "Borrower name is required"],
      itemSpecifier: [(v: string) => !!v || "Item is required"],
    };

    this.$nextTick(async () => {
      if (this.formElement.validate()) {
        this.$emit("onSubmit", this.formData);
        await this.doApiSubmit();
        this.resetForm();
      }
    });
  }

  /**
   * Reset the form by clearing all user input and clearing validation rules.
   */
  resetForm() {
    this.formRules = {
      borrowerName: [() => true],
      itemSpecifier: [() => true],
    };
    this.formElement.reset();
  }

  doesBorrowerExist(name: string): boolean {
    return Boolean(this.allBorrowers.find((b) => b.name === name));
  }

  async doApiSubmit() {
    let borrowerId: string;
    if (!this.doesBorrowerExist(this.formData.borrowerName)) {
      const result = await this.$apollo.mutate({
        mutation: GRAPHQL_QUERIES.createBorrower,
        variables: {
          borrowerName: this.formData.borrowerName,
        },
      });
      borrowerId = result.data.createBorrower.borrower.id;
      this.$emit("borrowerAdded");
    } else {
      borrowerId = this.allBorrowers.find(
        (b) => b.name === this.formData.borrowerName
      ).id;
    }

    await this.$apollo.mutate({
      mutation: GRAPHQL_QUERIES.createBorrowedItem,
      variables: {
        borrowerId,
        itemSpecifier: this.itemSpecifier,
        itemDescription: this.itemDescription,
      },
    });
    this.$emit("borrowedItemAdded");
  }
}
</script>
