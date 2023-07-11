import service from "@/utils/request";

export function getAST(id){
    return service({
        url: '/graph/ast?id=' + id,
        method: 'get',
    })
}

export function getCFG(id, method){
    return service({
        url: '/graph/cfg?id=' + id + '&method=' + method,
        method: 'get',
    })
}

export function getCG(id){
    return service({
        url: '/graph/cg?id=' + id,
        method: 'get',
    })
}

export function getMethod(id){
    return service({
        url: '/graph/method?id=' + id,
        method: 'get',
    })
}

export function getPDG(id, method){
    return service({
        url: '/graph/pdg?id=' + id + '&method=' + method,
        method: 'get',
    })
}
