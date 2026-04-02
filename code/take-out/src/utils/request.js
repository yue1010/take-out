import axios from 'axios'

// 创建实例
const request = axios.create({
  // 这里换成你后端真实地址
  baseURL: '/api',
  timeout: 5000
})

// 请求拦截器：自动带 token
request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = token
  }
  return config
})

// 响应拦截
request.interceptors.response.use(
  res => res.data,
  err => {
    alert(err.response?.data?.msg || '请求失败')
    return Promise.reject(err)
  }
)

export default request