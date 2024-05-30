<template>
  <el-menu
      background-color="#535c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      style="height: 100vh;"
      default-active="/Home"
      :collapse="isCollapse"
      :collapse-transition="false"
  >
    <el-menu-item index="/Home">
      <i class="el-icon-s-home"> </i>
      <span slot="title">首页</span>
    </el-menu-item>

    <el-menu-item index="/One">
      <i class="el-icon-s-fold"> </i>
      <span slot="title">导航1</span>
    </el-menu-item>
    <el-menu-item index="/Two">
      <i class="el-icon-s-claim"> </i>
      <span slot="title">导航2</span>
    </el-menu-item>

    <el-menu-item index="/Three">
      <i class="el-icon-s-home"> </i>
      <span slot="title">导航3</span>
    </el-menu-item>


  </el-menu>
</template>

<script>
export default{
  name: "Aside",
  data(){
    return{
      // isCollapse: false
    }
  },
  props:{
    isCollapse:Boolean
  }
}

</script>

<style scoped>

</style>