## SpringBoot + Vue  前后端分离 ---仓库管理系统

### 一. 创建后端项目

1. 创建文件夹
2. 打开文件夹创建spring boot 
3. 创建测试类并测试

### 二.加入mybatis-plus

1.加入依赖

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.1</version>
</dependency>
```

2.创建数据库，用户表，插入默认数据

```sql
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `no` varchar(20) DEFAULT NULL COMMENT '账号',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `age` int DEFAULT NULL,
  `sex` int DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `role_id` int DEFAULT NULL COMMENT '角色 0超级管理员 1 管理员 2普通账号',
  `isValid` varchar(4) DEFAULT 'Y' COMMENT '是否有效，y有效,其他无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
```

3.yml 文件配置

端口的数据源的配置

```yml
server:
    port: 8090
spring:
    datasource:
        url: jdbc:mysql://localhost:3306/wms?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
        driver-class-name: com.mysql.jdbc.Driver
        username: root
        password: tian
```

4.编写测试代码

### 三.代码生成器生成代码

1.加入依赖

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.5.1</version>
</dependency>
<dependency>
    <groupId>org.freemarker</groupId>
    <artifactId>freemarker</artifactId>
    <version>2.3.30</version>
</dependency>
<dependency>
    <groupId>com.spring4all</groupId>
    <artifactId>spring-boot-starter-swagger</artifactId>
    <version>1.5.1.RELEASE</version>
</dependency>
```

2.代码生成器

```java
package com.wms.common;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;



import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("tian");
        gc.setOpen(false);
        gc.setSwagger2(true);
        gc.setBaseColumnList(true);
        gc.setServiceImplName("%Service");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/wms?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("tian");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.wms")
                        .setEntity("entity")
                                .setMapper("mapper")
                                        .setService("service")
                                                .setServiceImpl("service.imp")
                                                        .setController("controller");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
       // strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
```

### 四.实现增删改查 并用postman或者 Apifox 测试

```java
@GetMapping("/list")
public List<User> list(){
    return userService.list();
}

//新增
@PostMapping("/save")
public boolean save(@RequestBody User user){
    return userService.save(user);
}
//修改
@PostMapping("/mod")
public boolean mod(@RequestBody User user){
    return userService.updateById(user);
}
//新增或修改
@PostMapping("/saveOrMod")
public boolean saveOrMOd(@RequestBody User user){
    return userService.saveOrUpdate(user);
}
//删除
@GetMapping("/delete")
public boolean delete(Integer id){
    return userService.removeById(id);
}
//查询（模糊,匹配）
@PostMapping("/listP")
public List<User> listP(@RequestBody User user){
    LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
    lambdaQueryWrapper.eq(User::getName,user.getName());
    return userService.list(lambdaQueryWrapper);
}
```

### 五.分页的处理

- 入参的分装

```java
package com.wms.common;

import lombok.Data;

@Data
public class QueryPageParam {
    //默认
    private static int PAGE_SIZE=20;
    private static int PAGE_NUM=1;

    private int pageSize;
    private int PageNum;
}
```

- 添加分页拦截器

  ```java
  package com.wms.common;
  
  import com.baomidou.mybatisplus.annotation.DbType;
  import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
  import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  
  @Configuration
  public class MybatisPlusConfig {
      @Bean
      public MybatisPlusInterceptor mybatisPlusInterceptor(){
          MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
          interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
          return interceptor;
      }
  }
  ```

- 编写分页的mapper方法
- 自定义sql使用wapper

### 六.返回前端数据的封装

------

[^让前端收到统一的数据，方便出路]: 

```json
{
    Code:200//400,
    Msg:"成功，失败",
    Total:10,
    Data:[]
}


```



### 七.创建前端项目 vue

```js
vue create xxx(名字)
```



### 八.导入Element-ui

*补充，vue脚手架（注意版本冲突） npm install -g @vue/cli*

1.安装命令

npm i element-ui -S

2.mian.js全局引入

```js
import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';

Vue.use(ElementUI);

new Vue({
  el: '#app',
  render: h => h(App)
});
```

### 九.页面布局

使用Container 布局容器

### 十.编写Hearder 头页面

1. dropdown 下表
2. 菜单伸缩图标
3. 欢迎字样
4. 去除背景，加入下边框

### 十一.菜单导航页面的编写

​	一级菜单
### 十二.菜单东航页面的伸缩

伸缩的思路

hearder 点击图标---提交 -- 》 父组件 父组件在去改变--》aside子组件（collapse)

### 十三.安装axios与处理跨域

**安装axios**

npm install axios --save

在main.js 全局引入axios

import axios from 'axios';

Vue.prototype.$axios = axios;

**request.js配置文件封装如下：**

```js
// 接口请求封装：Vue基于axios封装request接口请求——request.js文件
// Axios是一个基于promise的Ajax网络请求库，作用于node.js和浏览器中，在服务端它使用原生node.js http模块, 而在客户端 (浏览器端) 则使用XMLHttpRequests（XHR）
import axios from 'axios'
import router from '../router'

// 1. 创建axios实例request
const request = axios.create({
    baseURL: "/api",  // 注意：这里是全局统一加上了'/api'前缀，即所有接口都会加上'/api'前缀，页面里面写接口的时候就不要加'/api'了，否则会出现2个'/api'，类似'/api/api/user'这样的报错，切记！！
    timeout: 5000  // 请求超时时间
})

// 2. request添加请求拦截器：可以自请求发送前对请求做一些处理，比如统一加token，对请求参数统一加密
request.interceptors.request.use(
    config => {
        config.headers['Content-Type'] = 'application/json;charset=utf-8';
        config.headers["token"] = sessionStorage.getItem("token")
        // config.headers['token'] = user.token;  // 设置请求头

        // 取出sessionStorage里面缓存的用户信息，是json字符串
        /*let userJson = sessionStorage.getItem("user")
        if (!userJson) {
            router.push("/login")  // 若登录用户信息为空则跳转至登录页面
        }*/
        return config
    },
    error => {
        return Promise.reject(error)
    }
);

// 3. response添加响应拦截器：可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;  // 使用axios动态获取response里的data数据。存储token到localStorage
        if (res.code == 401) {
            router.push("/")
            localStorage.clear()
            sessionStorage.clear()
        }
        // 如果是返回的文件（blob）
        if (response.config.responseType === 'blob') {
            return response
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log('err' + error)  // for debug
        return Promise.reject(error)
    }
)

export default request

```



### 十四.列表展示

![image-20240531223009221](F:\Warehouse-management-system\img\image-20240531223009221.png)

1. 列表数据
2. 用tag 转换列
3. header-cell-style设置表头样式
4. 加上边框
5. 按钮（编辑和删除）
6. 后端返回结果分装（Result)

### 十五.分页处理

1. 页面加上分页代码
2. 修改查询方式和参数
3. 处理翻页，设置条数逻辑（注意一个问题）

### 十六.查询处理

1. 查询的布局（包含 查询，清空按钮）
2. 输入框
3. 下拉表
4. 回车事件（查询）
5. 重置处理

### 十七.新增

1. 新增按钮

2. 弹出窗口

3. 编写表单

4. 提交数据(提示信息，列表刷新)

5. 数据的提交

   数据的校验

   ```JS
   rules: {
     no: [
       {required: true, message: '请输入账号', trigger: 'blur'},
       {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'}
     ],
       name: [
         {required: true, message: '请输入姓名', trigger: 'blur'},
       ],
     password: [
       {required: true, message: '请输入密码', trigger: 'blur'},
       {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'}
     ],
     age: [
       { validator: checkAge, trigger: 'blur' }
     ],
     phone:[
       { validator: checkPhone, trigger: 'blur' }
     ]
   }
   ```

6.账号的唯一验证

7.表单重置

### 十八.编辑和删除操作

编辑：

1. 传递数据提交表单
2. 提交数据到后台
3. 表单重置

删除

1. 获取数据id
2. 提交到后台

### 十九.登录功能的实现

1. 登录页面
2. 后台查询代码
3. 登录页面的路由

- 安装路由插件（npm  | vue-router@3.5.4)
- 创建路由文件



