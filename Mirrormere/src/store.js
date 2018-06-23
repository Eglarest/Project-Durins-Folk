import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);
import celebdilAPI from "./api/config.json";

export default new Vuex.Store({
  state: {
    user: { id: 99 },
    inbox: [],
    sent: [],
    draft: {
      contactId: null,
      subject: null,
      content: null
    },
    contacts: [
      {
        id: 1,
        name: "user_1"
      },
      {
        id: 2,
        name: "user_2"
      }
    ]
  },
  mutations: {
    updateDraft(state, { item, value }) {
      state.draft[item] = value;
    }
  },
  getters: {
    getContacts(state) {
      return state.contacts;
    },
    getDraft(state) {
      return state.draft;
    }
  },
  actions: {
    // async fetchBetween(payload) {
    //   try {
    //     const id = payload.userId;
    //     const contact = payload.contactId;
    //     const path =
    //       celebdilAPI.development.baseUrl + celebdilAPI.route.between;
    //     const res = await fetch(path, { method: "get", mode: "cors" });
    //     return res.data;
    //   } catch (e) {
    //     console.error(e);
    //   }
    // },
    // async fetchInbox(payload) {
    //   try {
    //   } catch (e) {
    //     console.error(e);
    //   }
    // },
    // async fetchSent(payload) {
    //   try {
    //   } catch (e) {
    //     console.error(e);
    //   }
    // },
    async sendMessage(context, payload) {
      try {
        // TODO: use a real route - update config file
        const message = {
          id: context.state.user.id,
          ...payload
        };
        console.log(celebdilAPI);
        const path =
          celebdilAPI.development.baseUrl + celebdilAPI.route.message.send;
        console.log("SEND", message, path);
        const res = await fetch(path, {
          method: "POST",
          mode: "cors",
          body: message
        });
        return res.data;
      } catch (e) {
        console.error(e);
      }
    }
  }
});
