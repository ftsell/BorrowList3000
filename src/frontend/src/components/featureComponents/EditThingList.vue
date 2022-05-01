<script setup lang="ts">
import type { ThingListDto } from "@/apiClient";
import { useField, useForm } from "vee-validate";
import { string } from "yup";
import { useRestApi } from "@/apiClient";
import { useListStore } from "@/stores/listStore";
import TextField from "@/components/componentLibrary/TextField.vue";
import CustomButton from "@/components/componentLibrary/CustomButton.vue";
import CircularProgress from "@/components/componentLibrary/CircularProgress.vue";

const props = defineProps<{
  list: ThingListDto;
}>();

const emit = defineEmits<{
  (e: "finishEdit"): void;
  (e: "cancelEdit"): void;
}>();

const form = useForm({
  initialValues: {
    listName: props.list.name,
  },
});
const nameField = useField<string>("listName", string().required());

const api = useRestApi();
const listStore = useListStore();

const onSubmit = form.handleSubmit(async (values) => {
  await api.value.lists.updateById({
    id: props.list.id,
    patchThingListRequest: {
      name: values.listName,
    },
  });
  await listStore.fetchFromApi();
  emit("finishEdit");
});
</script>

<template>
  <form @submit="onSubmit">
    <TextField
      v-model="nameField.value.value"
      :error-message="nameField.errorMessage.value"
      id="edit-thing-list--name"
      type="text"
      label="Name"
      :placeholder="`originally ${list.name}`"
    />

    <div class="form-bottom">
      <CustomButton
        text="Cancel"
        type="button"
        :outlined="true"
        @click="emit('cancelEdit')"
      />
      <CustomButton text="Save" type="submit" />
      <CircularProgress
        v-show="form.isSubmitting.value"
        :indeterminate="true"
        :size="36"
      />
    </div>
  </form>
</template>

<style scoped>
form {
  margin-top: 16px;
}

.form-bottom {
  display: flex;
  align-items: center;
  justify-content: start;
  gap: 16px;
}
</style>
