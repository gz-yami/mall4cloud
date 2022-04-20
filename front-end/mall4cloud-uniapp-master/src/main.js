import Vue from 'vue'
import App from './App'
import confirmPop from './components/ConfirmPop'
Vue.component('ConfirmPop', confirmPop)

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
  ...App
})
app.$mount()
