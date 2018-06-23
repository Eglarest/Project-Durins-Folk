<template>
  <div class="message-content">
    <h3>Create New Message</h3>
    <h6 v-if="recipient">{{ `Send to ${recipient.name}` }}</h6>
    <input type="text" v-model="subject" placeholder="Subject"/>
    <textarea v-model="content" placeholder="Content..."/>
    <div class="button-bar">
      <button
        class="button"
        :disabled="!subject && !content"
        @click="save"> Save </button>
      <button
        class="button"
        :disabled="!subject || !content || !recipient"
        @click="submit"> Send </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "new-message",
  data() {
    return {
      subject: null,
      content: "",
      recipient: null,
      recipientId: null
    };
  },
  watch: {
    recipientId(id) {
      this.recipient = this.contacts.find(c => c.id === id);
    }
  },
  computed: {
    draft() {
      return this.$store.getters["getDraft"];
    },
    contacts() {
      return this.$store.getters["getContacts"];
    }
  },
  methods: {
    save() {
      this.$store.state.draft.subject = this.subject;
      this.$store.state.draft.content = this.content;
      this.recipientId = this.draft.contactId;
    },
    async submit() {
      try {
        this.save();
        await this.$store.dispatch("sendMessage", this.draft);
      } catch (e) {
        console.error(e);
      } finally {
        this.subject = null;
        this.content = null;
        this.save();
      }
    }
  }
};
</script>

<style lang="stylus">
  .message-content
    display: flex
    flex-direction: column
    justify-content: center
    align-items: center
    > input
      padding: 1rem
      width: 30rem
      font-size: 16px
      margin: 1rem
    > textarea
      min-width: 30rem
      padding: 1rem
      min-height: 30rem
    .button-bar
      margin-top: 1rem
      button
        margin: .2rem 1rem
</style>
