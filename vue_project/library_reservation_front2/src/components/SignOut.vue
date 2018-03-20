<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
  </div>
</template>

<script>
export default {
  name: 'SignOut',
  data () {
    return {
      msg: 'Welcome to SignOut'
    }
  },
  mounted () {
  alert('sdfsd');
    this.$http({
        url: 'http://127.0.0.1:8081/sign_out/final',
        method: 'post'
      }).then((res) => {
        console.log(res.data);
        alert(res.data.msg);
      })
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
