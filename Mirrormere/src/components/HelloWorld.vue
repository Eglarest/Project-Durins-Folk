<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h2>Essential Links</h2>
    <ul>
      <li>
        <a
          href="https://github.com/NickMcSweeney/Project-Durins-Folk"
          target="_blank"
        >
          Core Docs
        </a>
      </li>
      <li>
        <a
          href="https://github.com/NickMcSweeney/Project-Durins-Folk/issues"
          target="_blank"
        >
          Issues
        </a>
      </li>
      <li>
        <a
          href="https://github.com/NickMcSweeney/Project-Durins-Folk/wiki"
          target="_blank"
        >
          Wiki
        </a>
      </li>
      <li>
        <a
          href="https://github.com/NickMcSweeney/Project-Durins-Folk/tree/master/Mirrormere"
          target="_blank"
        >
          This Projects Home
        </a>
      </li>
    </ul>
    <h2>Ecosystem</h2>
    <ul>
      <li>
        <a
          href="https://github.com/NickMcSweeney/Project-Durins-Folk/tree/master/Celebdil"
          target="_blank"
        >
          Celebdil
        </a>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Project Durins Folk'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
