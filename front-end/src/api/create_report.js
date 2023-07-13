import axios from 'axios'
export function getFile (url) {
    return axios.request({
      url: url,
      method: 'get',
      responseType: 'blob'
    })
  }