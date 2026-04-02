import { createRouter, createWebHistory } from 'vue-router'

import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Address from '../views/Address.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/address',
    name: 'Address',
    component: Address  // ✅ 这里必须加上！你之前漏了！
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router