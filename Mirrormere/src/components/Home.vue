<template>
  <div class="home-page">
    <div class="home-header">
      <h1>{{ `Welcome ${user.name}`}}</h1>
    </div>
    <div class="home-contents-container">
      <div class="user-container">
      </div>
      <div class="nav-container">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'home',
  data () {
    return {
      user: {}
    }
  },
  created () {
    this.user = {
      name: 'nick'
    }
  }
}
</script>

<style lang="stylus">
.home-page
  width: 100%
  height: 100%
  padding: 1rem
  display: flex
  justify-content: space-between
  flex-direction: column
  align-items: center
  .home-header
    background-color: black
    color: white
    padding: 1rem
  .home-contents-container
    height: 50rem
    width: 50rem
    .user-container
      width: $width=50%
      height: $width
      border-radius: 100%
      border: 2px solid black
</style>
