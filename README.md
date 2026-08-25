# Talis Web Management

基于 Spring Boot 3 + MyBatis + MySQL 的教育培训机构学员管理系统，提供员工、部门、班级、学员管理及数据报表统计功能，内置 JWT 认证、AOP 操作日志、全局异常处理等企业级特性。

## 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 基础框架 | Spring Boot | 3.4.1 |
| ORM 框架 | MyBatis | 3.0.4 |
| 数据库 | MySQL | 8.0+ |
| 分页插件 | PageHelper | 1.4.7 |
| 认证方案 | JWT (jjwt) | 0.9.1 |
| 文件存储 | 阿里云 OSS | 3.17.4 |
| 构建工具 | Maven | 3.9+ |
| Java 版本 | JDK | 17+ |

## 项目结构

```
src/main/java/com/itcast/taliswebmanagement
├── TalisWebManagementApplication.java   # 启动类
├── controller/                          # 控制层
│   ├── LoginController.java             # 登录认证
│   ├── EmpController.java               # 员工管理
│   ├── DeptController.java              # 部门管理
│   ├── ClazzController.java             # 班级管理
│   ├── StudentController.java           # 学员管理
│   ├── ReportController.java             # 报表统计
│   ├── OperateLogController.java         # 操作日志
│   └── UploadController.java            # 文件上传
├── service/                             # 业务层
│   ├── impl/                            # 业务实现
│   ├── DeptService.java
│   ├── EmpService.java
│   ├── ClazzService.java
│   ├── StudentService.java
│   ├── ReportService.java
│   ├── OperateLogService.java
│   └── EmpLogService.java
├── mapper/                              # 数据访问层
│   ├── DeptMapper.java
│   ├── EmpMapper.java
│   ├── EmpExprMapper.java
│   ├── EmpLogMapper.java
│   ├── ClazzMapper.java
│   ├── StudentMapper.java
│   └── OperateLogMapper.java
├── pojo/                                # 实体与 DTO
│   ├── Dept.java / Emp.java / Clazz.java / Student.java
│   ├── EmpExpr.java / EmpLog.java / OperateLog.java
│   ├── LoginInfo.java
│   ├── Result.java / PageResult.java   # 统一响应封装
│   └── *QueryParam.java / *Option.java  # 查询参数与统计结果
├── filter/
│   └── TokenFilter.java                 # JWT 认证过滤器
├── aop/
│   └── OperationLogAspect.java          # 操作日志切面
├── anno/
│   └── LogOperation.java                # 日志注解
├── exception/
│   ├── BusinessException.java          # 业务异常
│   └── GlobalExceptionHandler.java      # 全局异常处理器
└── utils/
    ├── JwtUtils.java                    # JWT 工具类
    ├── CurrentHolder.java              # ThreadLocal 用户上下文
    ├── AliyunOSSOperator.java           # OSS 文件操作
    └── AliyunOSSProperties.java         # OSS 配置

src/main/resources
├── application.yml                       # 应用配置
├── logback.xml                           # 日志配置
└── com/itcast/taliswebmanagement/mapper/ # MyBatis XML
    ├── EmpMapper.xml
    ├── EmpExprMapper.xml
    ├── ClazzMapper.xml
    └── StudentMapper.xml

src/test/java/com/itcast/taliswebmanagement
├── service/
│   ├── DeptServiceTest.java             # 部门 Service 单元测试
│   └── StudentServiceTest.java          # 学员 Service 单元测试
└── controller/
    ├── DeptControllerTest.java          # 部门 Controller 接口测试
    └── LoginControllerTest.java         # 登录 Controller 接口测试
```

## 功能模块

### 1. 登录认证

- 员工登录验证，签发 JWT 令牌
- `TokenFilter` 全局拦截，除 `/login` 外所有接口需携带 token
- 通过 `ThreadLocal` 维护请求级用户上下文

### 2. 员工管理

- 员工 CRUD、多条件分页查询（姓名/性别/入职日期范围）
- 新增/更新员工时联动管理工作经历子表（批量增删）
- 员工删除支持批量操作
- 员工日志独立事务记录

### 3. 部门管理

- 部门 CRUD
- 删除前校验部门下是否有员工，有则拒绝删除

### 4. 班级管理

- 班级 CRUD、分页查询
- 根据开课/结课日期动态计算班级状态（未开班 / 在读中 / 已结课）

### 5. 学员管理

- 学员 CRUD、分页查询
- 违纪处理：自动累加违纪次数和扣分

### 6. 报表统计

| 接口 | 说明 | SQL 技术 |
|------|------|----------|
| GET /report/empJobData | 各职位员工人数 | GROUP BY + CASE WHEN |
| GET /report/empGenderData | 员工性别比例 | GROUP BY |
| GET /report/studentCountData | 各班级学员人数 | JOIN + GROUP BY |
| GET /report/studentDegreeData | 学员学历分布 | GROUP BY + CASE WHEN |

### 7. 操作日志

- 基于 AOP + 自定义注解 `@LogOperation` 自动记录写操作
- 日志内容：操作人、类名、方法名、参数、返回值、耗时
- 支持分页查询历史日志

### 8. 文件上传

- 集成阿里云 OSS 实现文件上传
- 按日期目录 + UUID 组织存储路径

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.9+
- MySQL 8.0+

### 数据库准备

1. 创建数据库：

```sql
CREATE DATABASE tlias DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

2. 创建数据表（共 7 张表）：

```sql
-- 部门表
CREATE TABLE dept (
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(10) NOT NULL UNIQUE,
    create_time DATETIME,
    update_time DATETIME
);

-- 员工表
CREATE TABLE emp (
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(10) NOT NULL,
    gender      TINYINT UNSIGNED,
    phone       VARCHAR(11),
    job         TINYINT UNSIGNED,
    salary      DECIMAL(8,2),
    dept_id     INT UNSIGNED,
    entry_date DATE,
    create_time DATETIME,
    update_time DATETIME,
    CONSTRAINT fk_emp_dept FOREIGN KEY (dept_id) REFERENCES dept(id)
);

-- 员工工作经历表
CREATE TABLE emp_expr (
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    emp_id      INT UNSIGNED,
    company     VARCHAR(50),
    title       VARCHAR(20),
    start_date  DATE,
    end_date    DATE,
    CONSTRAINT fk_expr_emp FOREIGN KEY (emp_id) REFERENCES emp(id)
);

-- 班级表
CREATE TABLE clazz (
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(20),
    room        VARCHAR(10),
    begin_date  DATE,
    end_date    DATE,
    subject     VARCHAR(20),
    master_id   INT UNSIGNED,
    CONSTRAINT fk_clazz_emp FOREIGN KEY (master_id) REFERENCES emp(id)
);

-- 学员表
CREATE TABLE student (
    id              INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(10),
    no              VARCHAR(20),
    gender          TINYINT UNSIGNED,
    phone           VARCHAR(11),
    degree          TINYINT UNSIGNED,
    violation_count TINYINT UNSIGNED DEFAULT 0,
    violation_score TINYINT UNSIGNED DEFAULT 0,
    clazz_id        INT UNSIGNED,
    create_time     DATETIME,
    update_time     DATETIME,
    CONSTRAINT fk_student_clazz FOREIGN KEY (clazz_id) REFERENCES clazz(id)
);

-- 操作日志表
CREATE TABLE operate_log (
    id            INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    operate_time  DATETIME,
    operate_emp_id INT UNSIGNED,
    class_name    VARCHAR(100),
    method_name   VARCHAR(100),
    method_params VARCHAR(1000),
    return_value  VARCHAR(1000),
    cost_time     BIGINT
);

-- 员工操作日志表
CREATE TABLE emp_log (
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    operate_time DATETIME,
    emp_id      INT UNSIGNED,
    class_name  VARCHAR(100),
    method_name VARCHAR(100),
    method_params VARCHAR(1000),
    return_value VARCHAR(1000),
    cost_time   BIGINT
);
```

3. 修改 `src/main/resources/application.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/tlias
    username: root
    password: 你的密码
```

4. （可选）配置阿里云 OSS。在 `application.yml` 中修改：

```yaml
aliyun:
  oss:
    endpoint: https://oss-cn-beijing.aliyuncs.com
    bucketName: 你的bucket名称
    region: cn-beijing
```

并在 `AliyunOSSProperties.java` 中填入你的 AccessKey ID 和 Secret。

### 启动项目

```bash
# 克隆项目
git clone https://github.com/你的用户名/talis-web-management.git

# 进入项目目录
cd talis-web-management

# 使用 Maven 启动（需配置 Maven 环境变量）
mvn spring-boot:run

# 或使用 Maven Wrapper（无需本地安装 Maven）
./mvnw.cmd spring-boot:run
```

项目默认运行在 `http://localhost:8080`。

### 运行测试

```bash
mvn test
```

测试覆盖：
- Service 层单元测试（JUnit 5 + Mockito）：部门删除校验、学员违纪处理
- Controller 层接口测试（MockMvc）：部门 CRUD、登录认证

## API 接口概览

| 模块 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 登录 | POST | /login | 员工登录，返回 JWT |
| 部门 | GET | /depts | 查询全部部门 |
| 部门 | GET | /depts/{id} | 按 ID 查询部门 |
| 部门 | POST | /depts | 新增部门 |
| 部门 | PUT | /depts | 修改部门 |
| 部门 | DELETE | /depts?id= | 删除部门（校验员工） |
| 员工 | GET | /emps | 分页条件查询员工 |
| 员工 | GET | /emps/{id} | 查询员工详情（含工作经历） |
| 员工 | POST | /emps | 新增员工（含工作经历） |
| 员工 | PUT | /emps | 修改员工（含工作经历） |
| 员工 | DELETE | /emps?ids= | 批量删除员工 |
| 班级 | GET | /clazzs | 分页查询班级 |
| 班级 | POST | /clazzs | 新增班级 |
| 班级 | PUT | /clazzs | 修改班级 |
| 班级 | DELETE | /clazzs/{id} | 删除班级 |
| 学员 | GET | /students | 分页查询学员 |
| 学员 | POST | /students | 新增学员 |
| 学员 | PUT | /students | 修改学员 |
| 学员 | PUT | /students/violation/{id}/{score} | 违纪处理 |
| 学员 | DELETE | /students/{ids} | 批量删除学员 |
| 报表 | GET | /report/empJobData | 职位人数统计 |
| 报表 | GET | /report/empGenderData | 性别比例统计 |
| 报表 | GET | /report/studentCountData | 班级人数统计 |
| 报表 | GET | /report/studentDegreeData | 学历分布统计 |
| 日志 | GET | /log | 分页查询操作日志 |
| 上传 | POST | /upload | 文件上传至 OSS |

## 统一响应格式

```json
// 成功
{
  "code": 1,
  "msg": "success",
  "data": {}
}

// 失败
{
  "code": 0,
  "msg": "操作失败",
  "data": null
}

// 分页
{
  "total": 100,
  "rows": []
}
```

## 技术亮点

- **JWT 认证**：TokenFilter 全局拦截，JwtUtils 签发/解析令牌，CurrentHolder 基于 ThreadLocal 维护用户上下文，12 小时有效期
- **AOP 操作日志**：自定义 @LogOperation 注解 + OperationLogAspect 环绕通知，自动采集操作人、方法、参数、返回值、耗时，业务代码零侵入
- **全局异常处理**：@RestControllerAdvice 统一捕获 SQLException、DuplicateKeyException 等，规范化错误响应
- **事务管理**：员工新增涉及 emp + emp_expr 多表事务，emp_log 使用 REQUIRES_NEW 独立事务
- **动态 SQL**：MyBatis XML 中使用 `<if>`、`<where>`、`<set>` 实现条件查询和动态更新
- **班级状态计算**：SQL 层使用 CASE WHEN 根据日期动态计算未开班/在读中/已结课状态
