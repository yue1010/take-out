<template>
  <div class="address-page">
    <nav class="navbar">
      <div class="nav-container">
        <a class="navbar-brand" href="/">Take Out</a>
        <ul class="nav-menu">
          <li class="nav-item">
            <a class="nav-link" href="/">首页</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">点餐</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">我的订单</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/profile">个人中心</a>
          </li>
        </ul>
        <div class="user-info" id="userDropdownBtn">
          <div class="user-avatar">👤</div>
          <span>欢迎回来 ▾</span>
          <div class="user-dropdown" id="userDropdown">
            <a href="javascript:;" @click="logout">退出登录</a>
          </div>
        </div>
      </div>
    </nav>

    <div class="profile-container">
      <div class="sidebar">
        <div class="sidebar-avatar">👤</div>
        <span class="welcome-text">欢迎回来</span>
        <a href="/profile" class="sidebar-link">个人信息管理</a>
        <a href="#" class="sidebar-link active">修改密码</a>
        <a href="/address" class="sidebar-link">收货地址管理</a>
      </div>

      <div class="main-content">
        <div class="tab-header">
          <a class="tab-link active">修改密码</a>
        </div>

        <div class="pwd-form">
          <el-form
            ref="formRef"
            :model="form"
            :rules="rules"
            label-width="100px"
            v-loading="loading"
          >
            <el-form-item label="原密码" prop="oldPwd">
              <el-input v-model="form.oldPwd" type="password" placeholder="请输入当前密码" />
            </el-form-item>

            <el-form-item label="新密码" prop="newPwd">
              <el-input v-model="form.newPwd" type="password" placeholder="请输入新密码" />
            </el-form-item>

            <el-form-item label="确认新密码" prop="confirmPwd">
              <el-input v-model="form.confirmPwd" type="password" placeholder="请再次输入新密码" />
            </el-form-item>
          </el-form>

          <div class="info-action">
            <el-button @click="goBack">返回</el-button>
            <el-button type="primary" @click="submit">确认修改</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import request from '../api/request.js'
import '../assets/address.scss'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  oldPwd: '',
  newPwd: '',
  confirmPwd: '',
})

// 校验规则
const rules = {
  oldPwd: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPwd: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度 6-20 位', trigger: 'blur' },
  ],
  confirmPwd: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== form.newPwd) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur',
    },
  ],
}

// 提交修改
const submit = async () => {
  if (!formRef.value) return
  await formRef.value.validate()

  loading.value = true
  try {
    // 👇 100% 匹配你后端需要的 3 个参数
    const res = await request.patch('/user/updatePwd', {
      old_pwd: form.oldPwd,
      new_pwd: form.newPwd,
      re_pwd: form.confirmPwd,
    })

    if (res.code === 0) {
      ElMessage.success('密码修改成功，请重新登录')
      setTimeout(() => {
        localStorage.clear()
        router.push('/login')
      }, 1500)
    } else {
      ElMessage.error(res.msg || '修改失败')
    }
    // eslint-disable-next-line no-unused-vars
  } catch (err) {
    ElMessage.error('网络异常，请重试')
  } finally {
    loading.value = false
  }
}

const goBack = () => {
  router.push('/profile')
}

const logout = () => {
  if (confirm('确定退出？')) {
    localStorage.clear()
    router.push('/login')
  }
}
</script>

<style scoped>
.pwd-form {
  padding: 20px 0;
  width: 500px;
}
.info-action {
  margin-top: 30px;
}
</style>
