<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
  </div>
</template>

<script>
export default {
  name: 'SignIn',
  data () {
    return {
      msg: 'Welcome to SignIn'
    }
  },
  mounted () {
    this.$http({
        url: 'http://127.0.0.1:8081/sign_in',
        method: 'post',
        params: {
                    userId: '3140602023'
                }
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
