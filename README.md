# HiAR recognize
  HiAR SDK 使用的识别接口，需要进行应用的鉴权后，再调用识别接口，识别接口比起Cloud API 的识别更丰富，将返回图片的关联的资源内容，无需另外进行资源的查询。
  HiAR 正在进行 V3.0 的开发，为了良好的后续兼容，开发使用时只需关注 material 和 resource 2部分即可，对外层的 instance 可以不做关注，material 即为识别图，target_id 是识别图的全局唯一标识。


