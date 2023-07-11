import service from "@/utils/request";

export function Login(data) {
    return service({
        url: '/user/login',
        method: 'post',
        data: data
    })
}

export function Register(data){
    return service({
        url: '/user/register',
        method: 'post',
        data: data
    })
}

export function updateUser(data) {
    return service({
        url: '/user',
        method: 'put',
        data: data
    })
}

export function getInfo(token) {
    return request({
      url: '/vue-admin-template/user/info',
      method: 'get',
      params: { token }
    })
}

export function logout() {
    return request({
        url: '/vue-admin-template/user/logout',
        method: 'post'
    })
}
  