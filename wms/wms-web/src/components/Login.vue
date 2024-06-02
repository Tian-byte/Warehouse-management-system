<template>
  <div class="loginBody">
  <div style="height: 100vh; overflow: hidden; position: relative" >
    <div style="width: 400px; height: 300px; background-color: white; border-radius: 10px;
    margin: 150px auto; padding: 50px ">
      <div style="margin: 30px; text-align: center; font-size: 30px;font-weight: bold;color: dodgerblue">登录</div>
      <el-form :model="loginForm" :rules="rules" ref="loginForm">
        <el-form-item prop="no">
          <el-input placeholder="请输入账号" prefix-icon="el-icon-user" size="medium"
                    v-model="loginForm.no" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" show-password prefix-icon="el-icon-lock" size="medium"
                    v-model="loginForm.password" autocomplete="off" @keyup.enter.native="confirm"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" size="medium" type="primary" @click="confirm">
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
  </div>
</template>
<script>


export default {
  name:"Login",
  data(){
    return {
      confirm_disabled: false,
      loginForm: {
        no: '',
        password: ''
      },
      rules: {
        no: [
          {request: true, message: '请输入账号', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在3-10个字符', trigger: 'blur'}
        ],
        password: [
          {request: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在3-10个字符', trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    confirm() {
      this.confirm_disabled = true;
      this.$refs.loginForm.validate((valid)=>{
        if (valid){
          this.$axios.post(this.$httpUrl + '/user/login',this.loginForm).then(res=>res.data).then(res=>{
            if (res.code === 200){
              // alert('登录成功！')
              this.$notify({
                title: '登录成功',
                message: '欢迎回来！',
                type: 'success',
                duration: 1000 // 设置为 2 秒
              });
              //存储
              sessionStorage.setItem("CurUser",JSON.stringify(res.data))
              //跳转到主页面
              this.$router.replace('/Index');
            }else {
              this.confirm_disabled = false;
              // alert('校验失败，用户名或密码错误！');
              this.$notify({
                title: '用户名或密码错误！',
                message: '请重新输入账号和密码！',
                type: 'warning',
                duration: 1000 // 设置为 2 秒
              });
              return false;
            }
          });
        }else {
          this.confirm_disabled = false;
          console.log('校验失败');
          return false;
        }
      })
    },
  }
}
</script>


<style scoped>
.loginBody{
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #B3C0D1;
}
</style>