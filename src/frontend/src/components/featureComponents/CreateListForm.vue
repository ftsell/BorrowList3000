<script setup lang="ts">
import { useField, useForm } from "vee-validate";
import type { Problem, ThingListDto } from "@/apiClient";
import { string } from "yup";
import { useListStore } from "@/stores/listStore";
import TextField from "@/components/componentLibrary/TextField.vue";
import CustomButton from "@/components/componentLibrary/CustomButton.vue";

const emit = defineEmits<{
  (e: "listCreated", list: ThingListDto): void;
}>();

const form = useForm({
  initialValues: {
    listName: "",
  },
});
const nameField = useField<string>("listName", string().required());

const listStore = useListStore();

const onSubmit = form.handleSubmit(async (values) => {
  try {
    const list = await listStore.createList(values.listName);
    form.resetForm();
    emit("listCreated", list);
  } catch (e) {
    // only handle response exceptions that indicate a conflict
    if (!(e instanceof Response) || e.status != 409) {
      throw e;
    }

    const problem = (await e.json()) as Problem;
    nameField.setErrors(problem.title);
  }
});
</script>

<template>
  <form @submit="onSubmit">
    <span>Create a new List</span>

    <TextField
      v-model="nameField.value.value"
      :error-message="nameField.errorMessage.value"
      id="create-thing-list--name"
      label="Name"
      placeholder="e.g. Borrowlist"
      class="no-space-below flex-grow-large"
    />

    <CustomButton text="Create" type="submit" class="flex-grow-medium" />
  </form>
</template>

<style scoped>
form {
  display: flex;
  flex-direction: row;
  gap: 32px;
  align-items: center;
}

.no-space-below {
  margin-bottom: 0;
}

.flex-grow-large {
  flex-grow: 3;
}

.flex-grow-medium {
  flex-grow: 1;
}
</style>
