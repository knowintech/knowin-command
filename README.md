# xiot-core

## 使用私有仓库
* 在USER_HOME/.gradle里加入init.gradle，内容如下：

```
allprojects {
    repositories {
        maven {
            url 'https://maven.aliyun.com/repository/public'
        }

        maven {
	    name 'release-mvn'
            credentials {
                username '****'
                password '****'
            }
            url '******'
        }
        maven {
	    name 'snapshot-mvn'
            credentials {
                username '****'
                password '****'
            }
            url '******'
        }
    }
}
```

## 发布
```
./gradlew publish
```

## 应用引入xiot-core相关库


* 导入vertx版本
```
implementation "cn.geekcity.xiot:xiot-spec:0.46.3-SNAPSHOT"
implementation "cn.geekcity.xiot:xiot-spec-codec-vertx:0.46.3-SNAPSHOT"
```

* 导入netty版本
```
implementation "cn.geekcity.xiot:xiot-spec:0.46.3-SNAPSHOT"
implementation "cn.geekcity.xiot:xiot-spec-codec-generic:0.46.3-SNAPSHOT"
```