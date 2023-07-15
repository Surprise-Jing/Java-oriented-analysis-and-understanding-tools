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