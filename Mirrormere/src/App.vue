<template>
  <div id="app">
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/messaging">Messaging</router-link> |
      <router-link to="/about">About</router-link>
    </div>
    <router-view/>
  </div>
</template>

<style lang="stylus">
body
  height: 100vh
  margin: 0
#app
  font-family 'Avenir', Helvetica, Arial, sans-serif
  -webkit-font-smoothing antialiased
  -moz-osx-font-smoothing grayscale
  text-align center
  color #2c3e50
  height: 100%

#nav
  padding 30px
  a
    font-weight bold
    color #2c3e50
    &.router-link-exact-active
      color #42b983

button.button
  padding: .75em 1em
  background-color: #f9f9fb
  border: 3px solid #161414
  outline: none
  border-radius: 3px
  cursor: pointer
  color: #161414
  font-size: .8em
  &:hover
    background-color: #f9f9fb
    box-shadow: inset 0 0 2px rgba(#1f1717, 0.7)
  &:active
    background-color: #f9f9fb
    border-color: #1f1717
    box-shadow: inset 0 0 5px #1f1717
  &:disabled
    cursor: not-allowed
    box-shadow: none
    background-color: #f9f9fb
    color: #161414
    border: 3px solid #161414
    opacity: .6

</style>
