
// 原  wxs/number.wxs 文件，使用时在 js中引入
// const wxs = number()

function number () {
  return {
    // 保留两位小数
    floatPrice: (val) => {
      val = val / 100
      if (!val) {
        val = 0
      }
      return val.toFixed(2)
    },
    // 转换为价格的格式
    toPrice: (val) => {
      if (!val) {
        val = 0
      }
      val = Number(val)
      return val.toFixed(2)
    },
    /**
     * 处理价格-以小数点分割价格(分成:整数+小数)
     */
    parsePrice: (val) => {
      val = val / 100
      if (!val) {
        val = 0
      }
      return val.toFixed(2).split('.')
    },

    // 取整
    rounding: (val) => {
      if (!val) {
        val = 0
      }
      return parseInt(val)
    },
    // 满减格式
    parseDiscount: (discountRule, lang) => {
      if (discountRule == 0) {
        return lang == 'zh_CN' ? '满额减' : 'Amount'
      } else if (discountRule == 1) {
        return lang == 'zh_CN' ? '满件减' : 'Pieces'
      } else if (discountRule == 2) {
        return lang == 'zh_CN' ? '满额折' : 'Amount-discount'
      } else if (discountRule == 3) {
        return lang == 'zh_CN' ? '满件折' : 'Pieces-discount'
      } else {
        return ''
      }
    },
    // 满减提示
    parseDiscountMsg: (discountRule, needAmount, discount, lang) => {
      if (discountRule == 0) {
        return lang == 'zh_CN' ? '购满' + needAmount + '元减' + discount + '元' : 'Over ' + needAmount + ' minus ' + discount
      } else if (discountRule == 1) {
        return lang == 'zh_CN' ? '购满' + needAmount + '件减' + discount + '元' : discount + ' less for ' + needAmount + ' pieces'
      } else if (discountRule == 2) {
        return lang == 'zh_CN' ? '购满' + needAmount + '元打' + discount + '折' : discount + '% off over ' + needAmount
      } else if (discountRule == 3) {
        return lang == 'zh_CN' ? '购满' + needAmount + '件打' + discount + '折' : discount + '% off over ' + needAmount + ' pieces'
      } else {
        return ''
      }
    },

    // 优惠券名称
    parseDiscountProd: (discountRule, needAmount, discount, lang) => {
      if (discountRule == 1) {
        return lang == 'zh_CN' ? '满' + needAmount + '元减' + discount + '元' : '￥' + discount + ' off on ' + '￥' + needAmount
      } else if (discountRule == 2) {
        return lang == 'zh_CN' ? '满' + needAmount + '元打' + discount + '折' : (100 - discount * 10) + '% off on ' + '￥' + needAmount
      } else {
        return ''
      }
    },

    // 满减名称
    getCurrDiscountName: (discountId, discounts, lang) => {
      for (let i = 0; i < discounts?.length; i++) {
        if (discounts[i].discountId == discountId) {
          return discounts[i].discountName
        }
      }
      return lang == 'zh_CN' ? '不参与促销' : 'Not participating in promotion'
    },

    /**
     * 裁剪日期  2020-03-20 15:04:40  ->  2020-03-20
     */
    spliceDate: (dateStr) => {
      if (!dateStr) return
      return dateStr.split(' ')[0]
    },
    /**
     * 处理店铺收藏人数 - 保留一位小数，若小数小于1则不显示该小数
     * @param {Number} collectionNum 人数
     */
    parseCollectionNumber: (collectionNum) => {
      collectionNum = Number(collectionNum)
      if (!collectionNum) {
        return 0
      }
      return collectionNum >= 10500 ? (collectionNum / 10000).toFixed(1) + 'w' : collectionNum >= 10000 ? '1w' : collectionNum
    }
  }
}

export default number
