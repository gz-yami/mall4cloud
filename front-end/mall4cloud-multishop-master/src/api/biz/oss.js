import request from '@/utils/request'

export function ossInfo(fileNum) {
  return request({
    url: '/mall4cloud_biz/oss/info',
    method: 'get',
    params: { fileNum: fileNum }
  })
}

export function pageFileGroup() {
  return request({
    url: '/mall4cloud_biz/m/attach_file_group/list',
    method: 'get'
  })
}

export function getFileGroup(param) {
  return request({
    url: '/mall4cloud_biz/m/attach_file_group',
    method: 'get',
    params: param
  })
}

export function saveFileGroup(data) {
  return request({
    url: '/mall4cloud_biz/m/attach_file_group',
    method: 'post',
    data
  })
}

export function updateFileGroup(data) {
  return request({
    url: '/mall4cloud_biz/m/attach_file_group',
    method: 'put',
    data
  })
}

export function deleteFileGroup(param) {
  return request({
    url: '/mall4cloud_biz/m/attach_file_group',
    method: 'delete',
    params: param
  })
}

// minIo上传文件
export function minIoImgUpdate(url, data) {
  return request({
    headers: {
      'Content-Type': data.type
    },
    url: url,
    method: 'put',
    data
  })
}

// aliOss上传文件
export function aliImgUpdate(url, data) {
  return request({
    headers: {
      'Content-Type': data.type
    },
    url: url,
    method: 'post',
    data
  })
}
