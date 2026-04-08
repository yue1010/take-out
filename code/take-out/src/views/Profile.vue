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
            <a class="nav-link active" href="/profile">个人中心</a>
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
        <a href="/profile" class="sidebar-link active">个人信息管理</a>
        <a href="#" class="sidebar-link">修改密码</a>
        <a href="/address" class="sidebar-link">收货地址管理</a>
      </div>

      <div class="main-content">
        <div class="tab-header">
          <a href="#" class="tab-link active">{{ isEdit ? '修改个人信息' : '查看个人信息' }}</a>
        </div>

        <!-- 查看模式 -->
        <div v-if="!isEdit" v-loading="loading" class="info-view">
          <div class="info-item">
            <label>用户编号：</label>
            <span>{{ userInfo.userNo || '未获取' }}</span>
          </div>
          <div class="info-item">
            <label>手机号：</label>
            <span>{{ userInfo.userTel || '未获取' }}</span>
          </div>
          <div class="info-item">
            <label>用户名：</label>
            <span>{{ userInfo.userName || '未设置' }}</span>
          </div>
          <div class="info-item">
            <label>性别：</label>
            <span>{{ userInfo.userSex || '未设置' }}</span>
          </div>
          <div class="info-item">
            <label>创建时间：</label>
            <span>{{ userInfo.createTime || '未获取' }}</span>
          </div>
          <div class="info-item">
            <label>更新时间：</label>
            <span>{{ userInfo.updateTime || '未获取' }}</span>
          </div>

          <div class="info-action">
            <el-button type="primary" @click="isEdit = true">修改个人信息</el-button>
          </div>
        </div>

        <!-- 修改模式 -->
        <div v-else class="info-edit">
          <el-form
            ref="formRef"
            :model="form"
            :rules="rules"
            label-width="80px"
            v-loading="saveLoading"
          >
            <el-form-item label="用户名" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="性别" prop="userSex">
              <el-radio-group v-model="form.userSex">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form>

          <div class="info-action">
            <el-button @click="cancelEdit">取消</el-button>
            <el-button type="primary" @click="saveEdit">保存修改</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import request from '../api/request.js'
import '../assets/address.scss'

const router = useRouter()
const loading = ref(false)
const saveLoading = ref(false)
const isEdit = ref(false)
const formRef = ref(null)

// 🔥 完全和后端 User 类一致
const userInfo = reactive({
  userNo: '',
  userTel: '',
  userName: '',
  userSex: '',
  createTime: '',
  updateTime: ''
})

const form = reactive({
  userName: '',
  userSex: ''
})

const rules = {
  userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  userSex: [{ required: true, message: '请选择性别', trigger: 'change' }]
}

// 获取个人信息
const getUserInfo = async () => {
  loading.value = true
  try {
    const res = await request.get('/user/userInfo')
    if (res.code === 0) {
      Object.assign(userInfo, res.data)
      form.userName = userInfo.userName
      form.userSex = userInfo.userSex
    } else {
      ElMessage.error(res.msg || '获取失败')
    }
  } catch (e) {
    ElMessage.error('获取信息失败')
  } finally {
    loading.value = false
  }
}

// 保存修改（100%匹配后端）
const saveEdit = async () => {
  if (!formRef.value) return
  await formRef.value.validate()
  saveLoading.value = true

  try {
    const res = await request.put('/user/update', form)
    if (res.code === 0) {
      ElMessage.success('保存成功！')
      isEdit.value = false
      getUserInfo() // 重新加载最新数据
    } else {
      ElMessage.error(res.msg || '保存失败')
    }
  } catch (err) {
    ElMessage.error('保存失败')
  } finally {
    saveLoading.value = false
  }
}

const cancelEdit = () => {
  isEdit.value = false
  form.userName = userInfo.userName
  form.userSex = userInfo.userSex
}

const logout = () => {
  if (confirm('确定退出？')) {
    localStorage.clear()
    router.push('/login')
  }
}

const toggleDropdown = (e) => {
  e.stopPropagation()
  const dropdown = document.getElementById('userDropdown')
  if (dropdown) dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block'
}
const closeDropdown = () => {
  const dropdown = document.getElementById('userDropdown')
  if (dropdown) dropdown.style.display = 'none'
}

onMounted(() => {
  getUserInfo()
  document.getElementById('userDropdownBtn').addEventListener('click', toggleDropdown)
  document.addEventListener('click', closeDropdown)
})
</script>

<style scoped>
.info-view { padding: 10px 0; }
.info-item {
  display: flex;
  align-items: center;
  padding: 16px 0;
  border-bottom: 1px solid #e3f2fd;
  font-size: 15px;
}
.info-item label { width: 100px; font-weight: 500; color: #333; }
.info-item span { color: #666; }
.info-action { margin-top: 40px; }
</style>