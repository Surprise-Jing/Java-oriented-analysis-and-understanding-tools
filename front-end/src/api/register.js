import request from '@/utils/request'

//在注册时根据邮箱名获取验证码
export function getEmailCode(email) {
  return request({
    url: '/vue-admin-template/register/getEmailCode',
    method: 'post',
    email
  })
}

//提交注册
export function register( data) {
  return request({
    url: 'http://172.27.130.2:8080/user/register',
    method: 'post',
    data
  })
}
