// 日期格式化
export function getParseTime (time = new Date().getTime(), pattern) {
  if (arguments.length === 0 || !time) {
    return null
  }
  const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
      time = parseInt(time)
    } else if (typeof time === 'string') {
      time = time.replace(/-/gm, '/').replace('T', ' ').replace(/\.[\d]{3}/gm, '')
    }
    if ((typeof time === 'number') && (time.toString().length === 10)) {
      time = time * 1000
    }
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const timeStr = format.replace(/{([ymdhisa])+}/g, (result, key) => {
    let value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value]
    }
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }
    return value || 0
  })
  return timeStr
}

export function getDateTimeRange () {
  let time = new Date().getTime()
  let currentHour = parseInt(getParseTime(time, '{h}'))
  let currentMinute = parseInt(getParseTime(time, '{i}'))
  if (currentMinute >= 30) {
    if (currentHour === 23) {
      currentHour = '00'
      currentMinute = ':00'
      time += 24 * 60 * 60 * 1000
    } else {
      currentHour = currentHour + 1
      currentMinute = ':00'
    }
  } else {
    currentMinute = ':30'
  }
  currentHour = currentHour < 10 ? '0' + currentHour : currentHour + ''
  const currentTime = currentHour + currentMinute
  return {
    startTime: getParseTime(time, '{y}-{m}-{d}'),
    endTime: getParseTime(time + 24 * 60 * 60 * 1000, '{y}-{m}-{d}'),
    currentTime
  }
}
