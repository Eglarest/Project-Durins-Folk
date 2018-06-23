<template>
  <div class="messaging">
    <div class="right-bar">
      <ul>
        <li><router-link to="/messaging/inbox">Inbox</router-link></li>
        <li><router-link to="/messaging/compose">New Message</router-link></li>
      </ul>
    </div>
    <div class="center-content">
      <router-view/>
    </div>
    <left-bar/>
  </div>
</template>

<script>
import LeftBar from "../components/LeftBar";
export default {
  name: "messaging",
  components: { LeftBar },
  data() {
    return {
      payload: {}
    };
  }
};
</script>

<style lang="stylus">
.messaging
  display: flex
  height: 100%
  border-top: .25em solid #505c6a
  .right-bar
    flex: .25
    display: flex
    flex-direction: column
    border-right: .5em solid #505c6a
    height: 100%
    ul
      list-style-type: none
      text-align: left
      li
        padding: 0.36em

  .center-content
    flex: 1
    display: flex
    flex-direction: column

</style>
