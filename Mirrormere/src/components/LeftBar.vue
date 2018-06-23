<template>
  <div class="context-pannel">
    <h5> Select Contact </h5>
    <select v-model="selectedContact">
      <option v-for="contact in contacts" :key="contact.id" :value="contact">{{ contact.name }}</option>
    </select>
    <button class="button" :disabled="!selectedContact" @click="setContactId"> Select </button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedContact: null,
      contacts: []
    };
  },
  created() {
    this.contacts = this.$store.getters["getContacts"];
  },
  methods: {
    setContactId() {
      this.$store.commit("updateDraft", {
        item: "contactId",
        value: this.selectedContact.id
      });
    }
  }
};
</script>

<style lang="stylus">
  .context-pannel
    display: flex
    flex: .25
    flex-direction: column
    border-left: .25em solid #505c6a
    height: 100%
    padding: 1rem
    button
      margin: 1rem 2rem


</style>
