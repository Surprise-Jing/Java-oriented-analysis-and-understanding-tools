import service from '@/utils/request'

export function getList(params) {
  return service({
    url: '/vue-admin-template/table/list',
    method: 'get',
    params
  })
}
