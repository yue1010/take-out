<template>
  <div class="address-page">
    <!-- 顶部导航栏（完全还原第二张图的正确布局） -->
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
            <a class="nav-link active" href="/address">个人中心</a>
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

    <!-- 主体容器 -->
    <div class="profile-container">
      <!-- 左侧侧边栏 -->
      <div class="sidebar">
        <div class="sidebar-avatar">👤</div>
        <span class="welcome-text">欢迎回来</span>
        <a href="#" class="sidebar-link">修改个人信息</a>
        <a href="#" class="sidebar-link">修改密码</a>
        <a href="/address" class="sidebar-link active">收货地址管理</a>
      </div>

      <!-- 右侧主内容区 -->
      <div class="main-content">
        <div class="tab-header">
          <a href="#" class="tab-link active">查看收货地址</a>
        </div>

        <!-- 新增地址按钮（靠右，还原第二张图） -->
        <div class="action-bar">
          <el-button type="primary" @click="addVisible = true">+ 新增地址</el-button>
        </div>

        <!-- 地址列表 -->
        <div class="address-list" v-loading="loading">
          <div class="address-card" v-for="item in addressList" :key="item.id">
            <div class="address-info">
              <div class="address-receiver">
                {{ item.receiver }} <span class="address-phone">{{ item.phone }}</span>
              </div>
              <div class="address-detail">{{ item.address }}</div>
            </div>
          </div>
          <div class="text-center text-muted py-5" v-if="!loading && addressList.length === 0">
            暂无收货地址，快去添加第一个吧~
          </div>
        </div>
      </div>
    </div>

    <!-- 新增地址弹窗 -->
    <el-dialog
      v-model="addVisible"
      title="新增收货地址"
      width="500px"
      @close="resetForm"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="收货人" prop="receiver">
          <el-input v-model="form.receiver" placeholder="请输入收货人姓名" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入11位手机号" />
        </el-form-item>
        <el-form-item label="详细地址" prop="address">
          <el-input
            v-model="form.address"
            type="textarea"
            rows="3"
            placeholder="例如：北京市朝阳区建国路88号"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAdd">保存地址</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import '../assets/address.scss'
import { getAddressList, addAddress } from '../api/Address.js'

const router = useRouter()
const loading = ref(false)
const addressList = ref([])
const addVisible = ref(false)
const formRef = ref(null)
const userId = localStorage.getItem('userId') || 1

// 表单数据
const form = reactive({
  receiver: '',
  phone: '',
  address: ''
})

// 校验规则
const rules = {
  receiver: [{ required: true, message: '请输入收货人', trigger: 'blur' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的11位手机号', trigger: 'blur' }
  ],
  address: [{ required: true, message: '请输入详细地址', trigger: 'blur' }]
}

// 获取地址列表
const getList = async () => {
  loading.value = true
  try {
    const res = await getAddressList(userId)
    if (res.code === 200) {
      addressList.value = res.data || []
    } else {
      throw new Error(res.msg || '获取地址失败')
    }
  } catch (err) {
    console.error('获取地址失败：', err)
    ElMessage.warning('使用本地模拟数据')
    addressList.value = [
      { id: 1, receiver: "张三", phone: "13800138000", address: "北京市朝阳区建国路88号" },
      { id: 2, receiver: "李四", phone: "13900139000", address: "上海市浦东新区张江高科技园区" }
    ]
  } finally {
    loading.value = false
  }
}

// 提交新增地址
const handleAdd = async () => {
  if (!formRef.value) return
  try {
    await formRef.value.validate()
    const params = { userId, ...form }
    const res = await addAddress(params)
    if (res.code === 200) {
      ElMessage.success('地址添加成功！')
      addVisible.value = false
      getList()
    } else {
      ElMessage.error(res.msg || '地址添加失败')
    }
  } catch (err) {
    if (err.name !== 'ValidationError') {
      ElMessage.success('地址添加成功（模拟）！')
      addVisible.value = false
      addressList.value.push({
        id: Date.now(),
        receiver: form.receiver,
        phone: form.phone,
        address: form.address
      })
    }
  }
}

// 重置表单
const resetForm = () => {
  if (formRef.value) {
    formRef.value.clearValidate()
  }
  form.receiver = ''
  form.phone = ''
  form.address = ''
}

// 退出登录
const logout = () => {
  if (confirm('确定退出登录？')) {
    localStorage.clear()
    router.push('/login')
  }
}

// 下拉菜单
const toggleDropdown = (e) => {
  e.stopPropagation()
  const dropdown = document.getElementById('userDropdown')
  dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block'
}
const closeDropdown = () => {
  document.getElementById('userDropdown').style.display = 'none'
}

onMounted(() => {
  getList()
  document.getElementById('userDropdownBtn').addEventListener('click', toggleDropdown)
  document.addEventListener('click', closeDropdown)
})

onUnmounted(() => {
  document.getElementById('userDropdownBtn').removeEventListener('click', toggleDropdown)
  document.removeEventListener('click', closeDropdown)
})
</script>