<template>
  <div class="register-card">
    <div class="card-header">
      <div class="brand-icon">
        <i class="fas fa-utensils"></i>
      </div>
      <h2>欢迎回来</h2>
    </div>

    <form class="register-form" @submit.prevent="handleLogin">
      <!-- 手机号 -->
      <div class="input-group-wrapper">
        <div class="input-group">
          <span class="icon"><i class="fas fa-phone-alt"></i></span>
          <input
            type="tel"
            v-model.trim="form.phone"
            placeholder="手机号码"
            @input="clearError('phone')"
          >
        </div>
        <div class="input-error" :class="{ show: error.phone }">{{ error.phone }}</div>
      </div>

      <!-- 密码 -->
      <div class="input-group-wrapper">
        <div class="input-group">
          <span class="icon"><i class="fas fa-lock"></i></span>
          <input
            type="password"
            v-model.trim="form.password"
            placeholder="密码"
            @input="clearError('password')"
          >
        </div>
        <div class="input-error" :class="{ show: error.password }">{{ error.password }}</div>
      </div>

      <!-- 协议 -->
      <div class="checkbox-wrapper">
        <input
          type="checkbox"
          id="agreeTerms"
          v-model="form.agreeTerms"
          @change="clearError('terms')"
        >
        <label for="agreeTerms">我已阅读并同意<a href="#">《用户协议》</a>及<a href="#">《隐私政策》</a></label>
      </div>
      <div class="input-error" :class="{ show: error.terms }">{{ error.terms }}</div>

      <!-- 登录按钮 -->
      <button type="submit" class="register-btn">登录</button>

      <!-- 跳转注册 -->
      <div class="login-link">
        没有账号？<a @click="$router.push('/register')">立即注册</a>
      </div>
    </form>

    <div class="decor">
      <i class="fas fa-hamburger"></i>
      <i class="fas fa-pizza-slice"></i>
      <i class="fas fa-motorcycle"></i>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { userLogin } from '../api/user'

// 只声明一次router
const router = useRouter()

// 表单数据
const form = reactive({
  phone: '',
  password: '',
  agreeTerms: true
})

// 错误信息
const error = ref({
  phone: '',
  password: '',
  terms: ''
})

// 显示错误
function showError(field, msg) {
  error.value[field] = msg
}

// 清除错误
function clearError(field) {
  error.value[field] = ''
}

// 清除所有错误
function clearAllErrors() {
  Object.keys(error.value).forEach(key => {
    error.value[key] = ''
  })
}

// 提交登录
async function handleLogin() {
  clearAllErrors()
  let hasError = false

  // 手机号验证
  const phoneVal = form.phone.replace(/\s/g, '')
  if (!phoneVal) {
    showError('phone', '请输入手机号')
    hasError = true
  } else if (!/^1[3-9]\d{9}$/.test(phoneVal)) {
    showError('phone', '请输入正确的11位手机号')
    hasError = true
  }

  // 密码验证
  if (!form.password) {
    showError('password', '请输入密码')
    hasError = true
  } else if (form.password.length < 6) {
    showError('password', '密码至少6位')
    hasError = true
  }

  // 协议验证
  if (!form.agreeTerms) {
    showError('terms', '请同意用户协议和隐私政策')
    hasError = true
  }

  // 验证通过，调用登录接口
  if (!hasError) {
    try {
      const res = await userLogin({
        usertel: phoneVal,
        password: form.password
      })

      console.log('登录接口返回：', res)
      // 登录成功，保存token，跳首页
      if (res.code === 0) {
        localStorage.setItem('token', res.data)
        alert('✅ 登录成功！')
        // 这里换成你项目的首页路由，比如/home
        router.push('/home')
      } else {
        alert(res.message || '登录失败，请检查账号密码')
      }
    } catch (err) {
      console.error('登录失败：', err)
      alert('登录失败，请重试')
    }
  }
}
</script>

<style scoped>
</style>