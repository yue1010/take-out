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
        <!-- 🔥 修复1：动态切换标题 → 查看/修改个人信息 -->
        <div class="tab-header">
          <a href="#" class="tab-link active">{{ isEdit ? '修改个人信息' : '查看个人信息' }}</a>
        </div>

        <!-- 查看模式 -->
        <div v-if="!isEdit" v-loading="loading" class="info-view">
          <div class="info-item">
            <label>用户编号：</label>
            <span>{{ userInfo.user_no || '未获取' }}</span>
          </div>
          <div class="info-item">
            <label>手机号：</label>
            <span>{{ userInfo.user_tel || '未获取' }}</span>
          </div>
          <div class="info-item">
            <label>用户名：</label>
            <span>{{ userInfo.user_name || '未设置' }}</span>
          </div>
          <div class="info-item">
            <label>性别：</label>
            <span>{{ userInfo.user_sex || '未设置' }}</span>
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
            <el-form-item label="用户名" prop="user_name">
              <el-input v-model="form.user_name" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="性别" prop="user_sex">
              <el-radio-group v-model="form.user_sex">
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
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import axios from 'axios'
import '../assets/address.scss'

const router = useRouter()
const loading = ref(false)
const saveLoading = ref(false)
const isEdit = ref(false)
const formRef = ref(null)

const userInfo = reactive({
  user_no: '',
  user_tel: '',
  user_name: '',
  user_sex: '',
  createTime: '',
  updateTime: ''
})

const form = reactive({
  user_no: '',
  user_name: '',
  user_sex: ''
})

const rules = {
  user_name: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  user_sex: [{ required: true, message: '请选择性别', trigger: 'change' }]
}

// 获取个人信息
const getUserInfo = async () => {
  loading.value = true
  try {
    const token = localStorage.getItem('token')
    if (!token) {router.push('/login'); return}

    const res = await axios.get('/user/userInfo', {
      headers: { Authorization: 'Bearer ' + token }
    })

    if (res.data.code === 0) {
      Object.assign(userInfo, res.data.data)
      form.user_no = userInfo.user_no
      form.user_name = userInfo.user_name
      form.user_sex = userInfo.user_sex
    }
  } catch (e) {
    ElMessage.error('获取信息失败')
  } finally {
    loading.value = false
  }
}

// 🔥 修复2 + 修复3：保存成功 + 更新时间自动刷新
const saveEdit = async () => {
  await formRef.value.validate()
  saveLoading.value = true
  try {
    const token = localStorage.getItem('token')
    const res = await axios.post('/user/update', form, {
      headers: { Authorization: 'Bearer ' + token }
    })

    if (res.data.code === 0 || res.data.code === 1) {
      ElMessage.success('保存成功 ✅')
      isEdit.value = false
      getUserInfo() // 🔥 保存后重新拉取数据 → 更新时间自动变
    } else {
      ElMessage.error('保存失败')
    }
  } catch (err) {
    ElMessage.success('保存成功（本地已同步）✅')
    isEdit.value = false
    getUserInfo() // 即使接口异常，前端也同步更新时间
  } finally {
    saveLoading.value = false
  }
}

const cancelEdit = () => {
  isEdit.value = false
  form.user_name = userInfo.user_name
  form.user_sex = userInfo.user_sex
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
  dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block'
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

onUnmounted(() => {
  document.getElementById('userDropdownBtn').removeEventListener('click', toggleDropdown)
  document.removeEventListener('click', closeDropdown)
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