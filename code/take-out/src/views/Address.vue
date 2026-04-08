<template>
  <div class="address-page">
    <!-- 顶部导航栏（完全还原你的原布局） -->
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
        <a @click="toProfile" class="sidebar-link">个人信息管理</a>
        <a href="#" class="sidebar-link">修改密码</a>
        <a href="/address" class="sidebar-link active">收货地址管理</a>
      </div>

      <!-- 右侧主内容区 -->
      <div class="main-content">
        <div class="tab-header">
          <a href="#" class="tab-link active">查看收货地址</a>
        </div>

        <!-- 新增地址按钮（靠右，还原你的设计） -->
        <div class="action-bar">
          <el-button type="primary" @click="addVisible = true">+ 新增地址</el-button>
        </div>

        <!-- 地址列表 -->
        <div class="address-list" v-loading="loading">
          <div class="address-card" v-for="item in addressList" :key="item.id">
            <div class="address-info">
              <div class="address-receiver">
                {{ item.receiver }} <span class="address-phone">{{ item.reTel }}</span>
              </div>
              <div class="address-detail">{{ item.province }} {{ item.city }} {{ item.region }} {{ item.detailedAdd }}</div>
            </div>
          </div>
          <div class="text-center text-muted py-5" v-if="!loading && addressList.length === 0">
            暂无收货地址，快去添加第一个吧~
          </div>
        </div>
      </div>
    </div>

    <!-- 新增地址弹窗：仅修改cascader加了:props -->
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

        <!-- ✅ 新增：收货人性别 -->
        <el-form-item label="性别" prop="reSex">
          <el-radio-group v-model="form.reSex">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入11位手机号" />
        </el-form-item>

        <el-form-item label="省市区" prop="region">
          <el-cascader
            v-model="form.region"
            :options="regionOptions"
            :props="cascaderProps"
            placeholder="请选择省/市/区"
            style="width: 100%"
            separator=""
          />
        </el-form-item>

        <el-form-item label="详细地址" prop="address">
          <el-input
            v-model="form.address"
            type="textarea"
            :rows="3"
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

const toProfile = () => {
  router.push('/profile')
}

const cascaderProps = ref({
  emitPath: true,
  value: 'label',
  label: 'label',
  children: 'children'
})

const regionOptions = ref([
  { value: '440000', label: '广东省', children: [{ value: '440100', label: '广州市', children: [
    { value: '440103', label: '荔湾区' },{ value: '440104', label: '越秀区' },{ value: '440105', label: '海珠区' },
    { value: '440106', label: '天河区' },{ value: '440111', label: '白云区' },{ value: '440112', label: '黄埔区' },
    { value: '440113', label: '番禺区' },{ value: '440114', label: '花都区' },{ value: '440115', label: '南沙区' },
    { value: '440117', label: '从化区' },{ value: '440118', label: '增城区' }]
  },{ value: '440300', label: '深圳市', children: [
    { value: '440301', label: '罗湖区' },{ value: '440303', label: '福田区' },{ value: '440304', label: '南山区' },
    { value: '440305', label: '宝安区' },{ value: '440306', label: '龙岗区' },{ value: '440307', label: '龙华区' },
    { value: '440308', label: '坪山区' },{ value: '440309', label: '光明区' },{ value: '440310', label: '大鹏新区' }]
  }]},
  { value: '110000', label: '北京市', children: [{ value: '110100', label: '北京市', children: [
    { value: '110101', label: '东城区' },{ value: '110102', label: '西城区' },{ value: '110105', label: '朝阳区' },
    { value: '110106', label: '丰台区' },{ value: '110107', label: '石景山区' },{ value: '110108', label: '海淀区' }]
  }]},
  { value: '310000', label: '上海市', children: [{ value: '310100', label: '上海市', children: [
    { value: '310101', label: '黄浦区' },{ value: '310115', label: '浦东新区' }]
  }]}
])

// ✅ 增加 reSex 性别
const form = reactive({
  receiver: '',
  reSex: '男',
  phone: '',
  region: [],
  address: ''
})

const rules = {
  receiver: [{ required: true, message: '请输入收货人', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' },{ pattern: /^1[3-9]\d{9}$/, message: '格式错误' }],
  region: [{ required: true, message: '请选择省市区', trigger: 'change' }],
  address: [{ required: true, message: '请输入详细地址', trigger: 'blur' }]
}

// ✅ 修复：判断 code === 0
const getList = async () => {
  loading.value = true
  try {
    const res = await getAddressList()
    if (res.code === 0) {
      addressList.value = res.data || []
    } else {
      ElMessage.info(res.message || "暂无地址")
    }
  } catch (err) {
    console.error(err)
  } finally {
    loading.value = false
  }
}

// ✅ 修复：提交时带上 reSex
const handleAdd = async () => {
  if (!formRef.value) return
  try {
    await formRef.value.validate()
    const [province, city, region] = form.region

    const params = new URLSearchParams()
    params.append("receiver", form.receiver)
    params.append("reSex", form.reSex)
    params.append("reTel", form.phone)
    params.append("province", province || "")
    params.append("city", city || "")
    params.append("region", region || "")
    params.append("detailedAdd", form.address)

    const res = await addAddress(params)

    if (res.code === 0) {
      ElMessage.success("地址添加成功！")
      addVisible.value = false
      getList()
      resetForm()
    } else {
      ElMessage.error(res.message || "添加失败")
    }
  } catch (err) {
    console.error("报错：", err)
  }
}

const resetForm = () => {
  if (formRef.value) formRef.value.clearValidate()
  form.receiver = ''
  form.reSex = '男'
  form.phone = ''
  form.region = []
  form.address = ''
}

const logout = () => {
  if (confirm('确定退出登录？')) {
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
  getList()
  document.getElementById('userDropdownBtn').addEventListener('click', toggleDropdown)
  document.addEventListener('click', closeDropdown)
})

onUnmounted(() => {
  document.getElementById('userDropdownBtn').removeEventListener('click', toggleDropdown)
  document.removeEventListener('click', closeDropdown)
})
</script>