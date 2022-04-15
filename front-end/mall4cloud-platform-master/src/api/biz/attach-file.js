import request from '@/utils/request'

// 保存上传文件记录
export function save(data) {
  return request({
    url: '/mall4cloud_biz/m/attach_file',
    method: 'post',
    data
  })
}

// 获取上传文件记录表列表
export function page(pageParam) {
  return request({
    url: '/mall4cloud_biz/m/attach_file/page',
    method: 'get',
    params: pageParam
  })
}

// 更新文件记录
export function updateFileName(data) {
  return request({
    url: '/mall4cloud_biz/m/attach_file/update_file',
    method: 'put',
    data
  })
}

// 删除上传文件记录
export function deleteFile(param) {
  return request({
    url: '/mall4cloud_biz/m/attach_file',
    method: 'delete',
    params: param
  })
}
