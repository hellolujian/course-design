<template>
<div class="dialog">
    <div class="loginPage">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="用户名">
            <el-input v-model="form.userAccount"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input type="password" v-model="form.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">登录</el-button>
          </el-form-item>
        </el-form>           
    </div>
</div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      form: {
          userAccount: '',
          password: ''
        }
    }
  },
  methods: {
    onSubmit() {
      //alert(this.form.userAccount+this.form.password);
      this.$http({
        url: 'http://127.0.0.1:8081/adminLogin',
        method: 'post',
        params: {
                    userAccount: this.form.userAccount,
                    password: this.form.password
                }
      }).then((res) => {
        //alert(this.form.userId+this.form.password);
        console.log(res.data);
        if(res.data.data == true) {
          alert(res.data.msg);
          this.$router.push({name:'Admin'});
          
      }
    })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    html,body{
        margin: 0;
        padding: 0;
        position: relative;
    }
    .dialog{
        width: 100%;
        height: 100%;
        background: rgba(0,0,0,.8);
    }
    .loginPage{
        position: absolute;
        top: 50%;
        left: 50%;
        margin-top: -150px;
        margin-left: -175px;
        width: 350px;
        min-height: 300px;
        padding: 30px 20px 20px;
        border-radius: 8px;
        box-sizing: border-box;
        background-color: #fff;
    }
    .loginPage p{
        color: red;
        text-align: left;
    }
</style>