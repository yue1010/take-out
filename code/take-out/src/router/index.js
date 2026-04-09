import { createRouter, createWebHistory } from 'vue-router'

import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Address from '../views/Address.vue'

const routes = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
  },
  {
    path: '/address',
    name: 'Address',
    component: Address,
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../views/Profile.vue'),
  },
  {
    path: '/change-pwd',
    name: '修改密码',
    component: () => import('../views/ChangePwd.vue'),
  },
]

const router = createRouter({
  // 👇 只改这一行！！！
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
