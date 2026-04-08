import './assets/main.scss'
import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router'  // 引入路由
import App from './App.vue'

const app = createApp(App)
app.use(ElementPlus)
app.use(router)  // 👈 关键：挂载路由！
app.mount('#app')