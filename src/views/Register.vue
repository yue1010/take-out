<template>
  <div class="register-card">
    <div class="card-header">
      <div class="brand-icon">
        <i class="fas fa-utensils"></i>
      </div>
      <h2>开启美食之旅</h2>
    </div>

    <form class="register-form" @submit.prevent="handleSubmit">
      <!-- 用户名 -->
      <div class="input-group-wrapper">
        <div class="input-group">
          <span class="icon"><i class="fas fa-user"></i></span>
          <input
            type="text"
            v-model.trim="form.username"
            placeholder="用户名"
            @input="clearError('username')"
          >
        </div>
        <div class="input-error" :class="{ show: error.username }">{{ error.username }}</div>
      </div>

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

      <!-- 确认密码 -->
      <div class="input-group-wrapper">
        <div class="input-group">
          <span class="icon"><i class="fas fa-lock"></i></span>
          <input
            type="password"
            v-model.trim="form.confirmPassword"
            placeholder="确认密码"
            @input="clearError('confirmPassword')"
          >
        </div>
        <div class="input-error" :class="{ show: error.confirmPassword }">{{ error.confirmPassword }}</div>
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

      <!-- 注册按钮 -->
      <button type="submit" class="register-btn">立即注册</button>

      <!-- 跳转登录 -->
      <div class="login-link">
        已有账号？<a @click="$router.push('/login')">登录</a>
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
import { userRegister } from '../api/user'
const router = useRouter()

// 表单数据
const form = reactive({
  username: '',
  phone: '',
  password: '',
  confirmPassword: '',
  agreeTerms: true
})

// 错误信息
const error = ref({
  username: '',
  phone: '',
  password: '',
  confirmPassword: '',
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

// 提交注册
async function handleSubmit() {
  clearAllErrors()
  let hasError = false

  // 用户名验证
  if (!form.username.trim()) {
    showError('username', '请输入用户名')
    hasError = true
  } else if (form.username.length < 2) {
    showError('username', '用户名至少2个字符')
    hasError = true
  }

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

  // 确认密码验证
  if (!form.confirmPassword) {
    showError('confirmPassword', '请确认密码')
    hasError = true
  } else if (form.confirmPassword !== form.password) {
    showError('confirmPassword', '两次密码不一致')
    hasError = true
  }

  // 协议验证
  if (!form.agreeTerms) {
    showError('terms', '请同意用户协议和隐私政策')
    hasError = true
  }

  // 验证通过，调用注册接口
  if (!hasError) {
    try {
      const res = await userRegister({
        userName: form.username,
        usertel: form.phone,
        password: form.password
      })

      // 关键：打印日志，确认后端返回
      console.log('注册接口返回：', res)
      
      // 根据后端code判断结果
      if (res.code === 0) {
        alert('✅ 注册成功！即将跳转到登录页')
        router.push('/login')
      } else {
        alert(res.message || '注册失败')
      }
    } catch (err) {
      console.error('注册失败：', err)
      alert('注册失败，请重试')
    }
  }
}
</script>

<style scoped>
</style>