<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>

import Index from "@/components/Index.vue";

export default {
  name:"App",
  components:{
    Index
  }
}
</script>

<style>
#app{
  height: 100%;
}
</style>