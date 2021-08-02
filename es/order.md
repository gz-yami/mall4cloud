
订单搜索 mapping

PUT order

```json
{
  "mappings": {
    "properties": {
      "orderId": {
        "type": "long"
      },
      "createTime": {
        "type": "date" 
      },
      "shopName": {
        "type": "text",
        "analyzer": "ik_max_word",
        "search_analyzer": "ik_smart"
      },
      "shopId": {
        "type": "long"
      },
      "userId": {
        "type": "long"
      },
      "consignee": {
        "type": "text"
      },
      "mobile": {
        "type": "text"
      },
      "status": {
        "type": "integer"
      },
      "deliveryType": {
        "type": "integer"
      },
      "total": {
        "type": "long"
      },
      "closeType": {
        "type": "integer"
      },
      "payTime": {
        "type": "date"
      },
      "deliveryTime": {
        "type": "date"
      },
      "finallyTime": {
        "type": "date"
      },
      "cancelTime": {
        "type": "date"
      },
      "orderAddrId": {
        "type": "long"
      },
      "isPayed": {
        "type": "integer"
      },
      "deleteStatus": {
        "type": "integer"
      },
      "orderItems": {
        "type": "nested",
        "properties": {
          "pic": {
            "type": "keyword",
            "index": false,
            "doc_values": false
          },
          "spuName": {
            "type": "text",
            "analyzer": "ik_max_word",
            "search_analyzer": "ik_smart"
          },
          "skuName": {
            "type": "keyword",
            "index": false,
            "doc_values": false
          },
          "count": {
            "type": "integer"
          },
          "price": {
            "type": "long"
          },
          "skuId": {
            "type": "long"
          },
          "orderItemId": {
            "type": "long"
          },
          "spuId": {
            "type": "long"
          },
          "shopId": {
            "type": "long"
          },
          "userId": {
            "type": "long"
          },
          "deliveryType": {
            "type": "integer"
          },
          "shopCartTime": {
            "type": "date"
          },
          "spuTotalAmount": {
            "type": "long"
          }
        }
      }
    }
  }
}
```
