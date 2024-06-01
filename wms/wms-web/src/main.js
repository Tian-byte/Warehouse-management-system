import Vue from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css'
import axios from "axios";
Vue.prototype.$httpUrl='http://localhost:8090'

Vue.config.productionTip = false
Vue.prototype.$axios=axios
// Vue.use(ElementUI);
Vue.use(ElementUI,{size:'small'})
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
