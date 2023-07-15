import service from "@/utils/request";

export function DataFlowSlicer(id, lineNumber, variable) {
    return service({
        url: '/slicer/dataflow?id=' + id + '&lineNumber=' + lineNumber + '&variable=' + variable,
        method: 'get',
    })
}

export function PDGSlicer(id, lineNumber, variable) {
    return service({
        url: '/slicer/pdg?id=' + id + '&lineNumber=' + lineNumber + '&variable=' + variable,
        method: 'get',
    })
}

export function DynamicSlicer(id, lineNumber, input) {
    return service({
        url: '/slicer/dynamic?id=' + id + '&lineNumber=' + lineNumber + '&input=' + input,
        method: 'get',
    })
}
