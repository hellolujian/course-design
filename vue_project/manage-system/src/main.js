import Vue from 'vue';
import App from './App';
import router from './router';
import axios from 'axios';
import ElementUI from 'element-ui';
import echarts from 'echarts';
import qs from 'qs';
import 'element-ui/lib/theme-default/index.css';    // 默认主题
//import '../static/css/theme-green/index.css';       // 浅绿色主题
import "babel-polyfill";
import iView from 'iview';
import 'iview/dist/styles/iview.css';

Vue.use(iView);

Vue.use(ElementUI);
Vue.prototype.$axios = axios;
Vue.prototype.$http = axios;
Vue.prototype.$echarts = echarts;
new Vue({
    router,
    render: h => h(App)
}).$mount('#app');