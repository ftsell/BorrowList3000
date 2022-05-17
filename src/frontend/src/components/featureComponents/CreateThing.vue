<script setup lang="ts">
import type { ThingDto, ThingListDto } from "@/apiClient";
import { useField, useForm } from "vee-validate";
import { string } from "yup";
import { useRestApi } from "@/apiClient";
import { useThingStore } from "@/stores/thingStore";
import TextField from "@/components/componentLibrary/TextField.vue";
import CustomButton from "@/components/componentLibrary/CustomButton.vue";
import CircularProgress from "@/components/componentLibrary/CircularProgress.vue";
import { usePeopleStore } from "@/stores/peopleStore";

const props = defineProps<{
  list: ThingListDto;
}>();

const emit = defineEmits<{
  (e: "thingCreated", thing: ThingDto): void;
  (e: "canceled"): void;
}>();

const form = useForm({
  initialValues: {
    thingName: "",
    thingDescription: "",
    personName: "",
  },
});
const nameField = useField<string>("thingName", string().required());
const descriptionField = useField<string>("thingDescription", string());
const personField = useField<string>("personName", string().required());

const api = useRestApi();
const thingStore = useThingStore();
const peopleStore = usePeopleStore();

const onSubmit = form.handleSubmit(async (values) => {
  // ensure the person exists
  let person = peopleStore.getPersonByName(values.personName);
  if (person == null) {
    person = await peopleStore.create(values.personName);
  }

  // create new thing
  const thing = await thingStore.create(props.list.id, {
    name: values.thingName,
    description: values.thingDescription,
    personId: person.id,
  });

  emit("thingCreated", thing);
});
</script>

<template>
  <form @submit="onSubmit">
    <TextField
      id="create-thing--name"
      v-model="nameField.value.value"
      :error-message="nameField.errorMessage.value"
      label="Name"
      placeholder="e.g. Book"
    />
    <TextField
      id="create-thing--description"
      v-model="descriptionField.value.value"
      :error-message="descriptionField.errorMessage.value"
      label="Description (optional)"
      placeholder="e.g. The one with the broken cover"
    />
    <TextField
      id="create-thing--person"
      v-model="personField.value.value"
      :error-message="personField.errorMessage.value"
      label="Person"
      placeholder="e.g. Janet"
    />

    <div class="form-bottom">
      <CustomButton
        text="Cancel"
        type="button"
        :outlined="true"
        @click="emit('canceled')"
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
