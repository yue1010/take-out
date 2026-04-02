import request from './request'

// 获取地址列表
export function getAddressList(userId) {
  return request({
    url: '/address/list',
    method: 'get',
    params: { userId }
  })
}

// 新增地址
export function addAddress(data) {
  return request({
    url: '/address/add',
    method: 'post',
    data
  })
}