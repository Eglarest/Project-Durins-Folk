import Vue from "vue";
import Router from "vue-router";
import Home from "./views/Home.vue";
import About from "./views/About.vue";
import Messaging from "./views/Messaging.vue";
import Inbox from "./components/Inbox.vue";
import Compose from "./components/NewMessage.vue";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "home",
      component: Home
    },
    {
      path: "/about",
      name: "about",
      component: About
    },
    {
      path: "/messaging",
      name: "messaging",
      component: Messaging,
      children: [
        {
          path: "inbox",
          name: "inbox",
          component: Inbox
        },
        {
          path: "compose",
          name: "new message",
          component: Compose
        }
      ]
    }
  ]
});
