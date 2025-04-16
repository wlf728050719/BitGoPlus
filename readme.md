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
## 全局
### pojo类
1. 所有实体类和常量类必须定义在common-core模块中(否则容易出现循环依赖问题)
2. 持久层类均与PO或DictItem结尾，且要求属性与数据库字段一一对应(统一命名规范)
3. 多表组合数据实体类放dto中
4. 如果dto对象直接与po交互，dto对象转po对象方法必须定义在dto对象中，且方法固定为newXX明确为两个不同对象,以及需要有po生成dto的构造方法(使po专注于与数据库交互)
### 异常
1. 除了业务异常以及继承类msg使用中文，其余使用英文(用户友好)
2. 各模块内部exception定义在各自模块中，继承BizException或SysException(方便ExceptionHandler统一处理)
### 其他
1. Manager层负责可复用业务，Service层实现具体业务，原则上每个服务只能有一个service，且其函数与对应接口同名(分层解耦)
2. 通过@Cacheable注解value为命名空间,统一使用KeyGenerator进行管理。使用RedisTemplate则需要统一使用定义在RedisKey文件中的String.format/String形式(防止魔法值，且相同类型缓存使用类似键生成方式方便查询)
## Controller层
1. 表现层业务成功返回R.ok,可预见错误返回R.failed。用户异常操作时（如未授权访问/参数校验错误）均抛出异常(前后端统一)
2. 返回R中不带额外数据时，使用R<Boolean>,需要明确设置true,失败使用false(前后端统一)
3. 所有接口传入对象只能为dto中定义对象,且只有dto中对象添加jsr校验(使参数校验部分统一由Controller负责)
4. 每个服务RPC接口均命名为APIController且统一以/api路径开头，且该Controller不开启jsr校验以及身份识别(安全配置开放为内部端点,统一鉴权)
## Mapper层
## Manager层
1. 命名为持久层对象去掉尾缀后添加Manager,对应实现类再添加Impl。
2. manager层不对mapper的异常进行特殊处理，抛出统一捕获(便于Service层回滚)
## Service层
1. 所有service层涉及操作manager的方法均需要添加@Transactional(防止数据库脏数据)

# 项目约定
1. 同一手机号可注册多种身份账号（每种身份最多一个）用户，唯一约束:用户名、用户ID
2. 用户登录方式 用户名+密码 / 手机号+身份+验证码
3. 重置密码方式 用户名+新密码+验证码（单一角色重置） / 手机号+验证码（查询该手机号下所有用户名及对应身份）
4. admin>shopkeeper>clerk>customer 低一级的用户能访问则高一级的用户一定也能访问

# 注意事项
## 用户服务
1. 用户登录校验优先使用缓存数据，当用户数据更新时需要及时更新缓存
