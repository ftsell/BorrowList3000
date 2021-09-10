<template>
  <v-avatar color="primary" size="56">
    <avatar variant="beam" :name="name" />
  </v-avatar>
</template>

<script lang="ts">
import { Component, Vue, Prop } from "vue-property-decorator";
import Avatar from "vue2-boring-avatars";

@Component({
  components: { Avatar },
})
export default class BorrowerProfilePicture extends Vue {
  @Prop({ required: true, type: String }) name!: string;
}
</script>
