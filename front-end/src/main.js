import Vue from 'vue'

import axios from 'axios'
import VueAxios from 'vue-axios'
Vue.use(VueAxios, axios)
Vue.prototype.$axios = axios;

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control



  //import VueHighlightJS from 'vue-highlightjs'
//  import 'highlight.js/styles/atom-one-dark.css'
  //Vue.use(VueHighlightJS)


 //import Highlight from './utils/highlight'
// Vue.use(Highlight)


//const app=new App()
// app.directive('highlight', function (el) {
//   const blocks = el.querySelectorAll('pre code');
//   blocks.forEach((block) => {
//     hljs.highlightBlock(block);
//     // 从这开始是设置行号
//     block.innerHTML = `<ol><li>${block.innerHTML.replace(
//       /\n/g,
//       `</li><li class="line">`
//     )}</li></ol>`;
//   });
// });
// app.use(hljs.vuePlugin) 

// import hljs from 'highlight.js'
// //import 'highlight.js/styles/googlecode.css'
// Vue.directive('highlight',function (el) {
//   let blocks = el.querySelectorAll('pre code');
//   setTimeout(() =>{
//       blocks.forEach((block)=>{
//       hljs.highlightBlock(block)
//       block.innerHTML = `<ol><li>${block.innerHTML.replace(
//         /'\n'/g,
//         `</li><li class="line">`
//       )}</li></ol>`;
//       })
//   }, 200)
// })


/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
// Vue.use(ElementUI)

Vue.config.productionTip = false



new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
