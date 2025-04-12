# 风格规范
## checkstyle使用方法:
### 安装和配置
idea安装`CheckStyle-IDEA`插件以及`Adapter for Eclipse Code Formatter`插件

`工具`->`Checkstyle`->配置检查规则xml文件为根目录下/style/alibaba.xml

（style/suppressions.xml中为checkstyle跳过检查文件，当前配置为跳过所有资源文件，可自行配置，代码中风格问题可SuppressWarning注解抑制风格问题）

`其他工具`->`Adapter for Eclipse Code Formatter`->`Use Eclipse's Code Formatter`->配置xml文件为根目录下/style/ecipse-codestyle.xml

### 格式检查

#### 1.直接使用
idea安装后左下角有checkstyle按钮，选择配置的代码风格对全局扫描即可

#### 2.maven使用
终端输入 mvn checkstyle:check -D checkstyle.skip=false

(文件长度问题无法使用suppressWarning解决，设置级别为warning，测试环境是否配置成功方法可尝试注释掉alibaba.xml第19行，输入上述命名报错则表明配置正确，未注释情况下build seccess)

### 自动规范
idea已经自动整合规范文件 ctrl+alt+l即可规范选中代码或右键项目->重新设置代码格式规范整个项目

# 代码约定
1. 表现层业务成功返回R.ok,可预见错误返回R.failed。用户异常操作时（如直接HTTP请求访问而非前端调用）均通过异常统一管理
2. 返回R中不带额外数据时，使用R<Boolean>,需要明确设置true,失败使用false
3. 业务异常均使用中文，其余使用英文
4. 持久层类均与PO或DictItem结尾，对应Manager类
5. Manager层负责可复用业务，Service层实现具体业务，原则上每个服务只能有一个service，且其函数与对应接口同名
6. 所有接口传入对象只能为dto中定义对象，且只有dto中对象添加jsr校验
7. 所有dto对象转po对象方法必须定义在dto对象中，使po专注于与数据库交互，且方法固定为newXX明确为两个不同对象
8. manager层不对mapper的异常进行特殊处理，抛出统一捕获
9. 每个服务RPC接口均命名为APIController且统一以/api路径开头
10. 通过@Cacheable注解value为命名空间,统一使用KeyGenerator进行管理。使用RedisTemplate则需要统一使用定义在RedisKey文件中的String.format/String形式
# 项目约定
1. 同一手机号可注册多种身份账号（每种身份最多一个）用户，唯一约束:用户名、用户ID
2. 用户登录方式 用户名+密码 / 手机号+身份+验证码
3. 重置密码方式 用户名+新密码+验证码（单一角色重置） / 手机号+验证码（查询该手机号下所有用户名及对应身份）

# 业务文档
## 用户服务
1. 用户登录校验优先使用缓存数据，当用户数据更新时需要及时更新缓存
