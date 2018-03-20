<template>
  <div class="hello">
    
  </div>
</template>

<script>
export default {
  name: 'Reserve',
  data () {
    return {
      msg: 'Welcome to Reserve'
    }
  },
  mounted () {
    this.$http({
        url: 'http://127.0.0.1:8081/reserve',
        method: 'post',
        params: {
                    userId: '3140602026',
                    seatId: '101-1001-10002',
                    reserveTime: '2018-01-05'
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
