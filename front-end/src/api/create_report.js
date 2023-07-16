import service from "@/utils/request";


export function AstPNG(id){
  return service({
      url: '/save/ast?id=' + id,
      method: 'get',
      responseType: 'blob',
  })


}

export function CgPNG(id){
  return service({
      url: '/save/cg?id=' + id,
      method: 'get',
      responseType: 'blob',
  })
}

export function CfgPNG(id){
  return service({
      url: '/save/cfg?id=' + id,
      method: 'get',
      responseType: 'blob',
  })
}

export function PdgPNG(id){
  return service({
      url: '/save/pdg?id=' + id,
      method: 'get',
      responseType: 'blob',
  })
}
