import service from "@/utils/request";

export function uploadFile(data) {
    return service({
        url: '/file',
        method: 'post',
        data: data
    })
}

export function getFile(uid) {
    return service({
        url: '/file/user?uid=' + uid,
        method: 'get',
    })
}

export function getFileContext(id) {
    return service({
        url: '/file?id=' + id,
        method: 'get',
    })
}

export function deleteFile(id, uid) {
    return service({
        url: '/file/delete?id=' + id + '&uid=' + uid,
        method: 'get',
    })
}

export function getFileInfo(id) {
    return service({
        url: '/file/info?id=' + id,
        method: 'get',
    })
}