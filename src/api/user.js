import request from '../utils/request'
import qs from 'qs'

// 注册接口
export function userRegister(data) {
  return request({
    url: '/user/register',
    method: 'POST',
    data: qs.stringify(data)
  })
}

// 登录接口
export function userLogin(data) {
  return request({
    url: '/user/login',
    method: 'POST',
    data: qs.stringify(data)
  })
}