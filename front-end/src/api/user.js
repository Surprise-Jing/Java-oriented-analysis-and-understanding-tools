import service from "@/utils/request";

export function login(data) {
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

export function uploadFile(data) {
    return service({
        url: '/file',
        method: 'post',
        data: data
    })
}


export function getInfo(token) {
    return service({
      url: '/user/info',
      method: 'get',
      params: { token }
    })
}

export function logout() {
    return service({
        url: '/user/logout',
        method: 'post'
    })
}

export function getInfoId(id) {
    return service({
      url: '/user?id=' + id,
      method: 'get',
    })
}