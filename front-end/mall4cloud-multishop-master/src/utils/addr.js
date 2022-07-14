/**
 * 通过地址id获取省市区的地址名
 * @param {*} s
 */
export function areaNameList(areaData, cityList) {
  // 插入数据
  cityList.forEach(element => {
    var city = element
    // 省
    for (var i = 0; i < areaData.length; i++) {
      // 市
      const cityIndex = areaData[i].areaIds.indexOf(city.parentId)
      if (cityIndex > -1) {
        // 区
        const areaIndex = areaData[i].areas[cityIndex].areaIds.indexOf(city.areaId)
        if (areaIndex > -1) {
          areaData[i].areas[cityIndex].areas[areaIndex].check = true
        }
      }
    }
  })
  // 获取区域名
  var addrNameList = []
  // 城市名
  var cityNameList = []
  // 地区名
  var areaNameList = []
  areaData.forEach(province => {
    var cityList = []
    var cityAll = true
    // 市
    province.areas.forEach(city => {
      var areaList = []
      var areaAll = true
      // 区
      city.areas.forEach(area => {
        if (area.check) {
          areaList.push(area.areaName)
        } else if (areaAll) {
          areaAll = false
          cityAll = false
        }
      })
      // 区 END
      if (areaAll) {
        // 该市下的区已全部勾选，则添加城市名
        cityList.push(city.areaName)
        city.check = true
      } else if (areaList.length > 0) {
        // 仅勾选部分区，把勾选的区添加到地区名名的数组中
        areaNameList = areaNameList.concat(areaList)
      }
      if (!city.check) {
        cityAll = false
      }
    })
    // 市 END
    if (cityAll) {
      // 所有的城市都已勾选，则添加省名
      addrNameList.push(province.areaName)
      province.check = true
    } else if (cityList.length > 0) {
      // 只勾选部分城市，把勾选的城市添加到城市名的数组中
      cityNameList = cityNameList.concat(cityList)
    }
  })
  // 拼接省->市->区
  if (cityNameList.length > 0) {
    addrNameList = addrNameList.concat(cityNameList)
  }
  if (areaNameList.length > 0) {
    addrNameList = addrNameList.concat(areaNameList)
  }
  return addrNameList
}
