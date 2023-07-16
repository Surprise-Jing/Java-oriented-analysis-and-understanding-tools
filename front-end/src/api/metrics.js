import service from "@/utils/request";

export function getMetrics(id, data) {
    return service({
        url: '/metrics?id=' + id,
        method: 'post',
        data: data
    })
}

export function getLinesMethod(id, data) {
    return service({
        url: '/metrics/methodlines?id=' + id,
        method: 'post',
        data: data
    })
}

export function getLines(id) {
    return service({
        url: '/metrics/lines?id=' + id,
        method: 'get'
    })
}

export function getCodeMetrics(id) {
    return service({
        url: '/metrics?id=' + id,
        method: 'get'
    })
}