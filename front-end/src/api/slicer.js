import service from "@/utils/request";

export function DataFlowSlicer(id, lineNumber, variable) {
    return service({
        url: '/slicer/dataflow?id=' + id + '&lineNumber=' + lineNumber + '&varibale' + variable,
        method: 'get',
    })
}

export function PDGSlicer(id, lineNumber, variable) {
    return service({
        url: '/slicer/pdg?id=' + id + '&lineNumber=' + lineNumber + '&varibale' + variable,
        method: 'get',
    })
}