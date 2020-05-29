# pojo

## 说明

 - BaseDTO
 - BasePO

### BaseDTO

传输类，用于Service to Facade, Service to Web Api, Manager to Service

### BasePO

与数据表字段一一对应

## 备注

toString 需要重写，使用org.apache.commons.lang3.builder.ReflectionToStringBuilder性能差，但是要用

对DTO和PO进行充血模式操作，建立一个Wrapper类，并做数据转化