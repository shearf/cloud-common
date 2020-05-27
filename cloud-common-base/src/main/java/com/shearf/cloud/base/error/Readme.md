# 错误码设计

统一错误码规范，简单并可以快速定位错误。

该设计规范参考新浪的错误规范

## 错误码规范

以八位数字来定义错误码，从左到右

第一位 0表示保留全局错误，1表示系统级别错误，2服务级别错误
第2-4位 表示具体服务
第5-8位 服务中的错误


