# AIME Wallet Middle Service
<img src=".assets/LOGO+AIME-light.png" alt="AIME" style="zoom: 50%;" align="center"/>



AIME.DOG：首个社交多链 DEX | 探索 Alpha First

AIME 是首款的以社交为主导的多链 DEX。至于为打造全球极致好用的去中心化交易所。让用户集成交易、社交、讨论、跟单一体的功能体验。

AIME 采用 MPC 钱包结构，让用户的资产为自己所拥有。





Want to read this in english ? Go [here](./docs/readme_en.md)

<p align="center">
  <a href="https://docs.aime.dog/">Docs</a> - <a href="https://aime.dog/">AIME</a> - <a href="https://aime.dog/invite">Invite</a> - <a href="https://aime.dog/channel">Channel</a>
</p>

<p align="center">
    <img src ="https://img.shields.io/badge/version-0.1.0-blueviolet.svg"/>
    <img src ="https://img.shields.io/badge/platform-linux|macos-yellow.svg"/>
    <img src ="https://img.shields.io/badge/database-mysql|8.0+-orange.svg"/>
    <img src ="https://img.shields.io/badge/java-17-blue.svg" />
</p>


## 初衷

AIME 致力于为用户提供一套安全、简洁、易用的 DEX。我们将用户的资产安全放在第一位。在此前提下，会保障用户拥有类似于中心化交易所的体验。



### 钱包的筛选

我们对很多的钱包做了横向对比，以去中心化思想为基线，为用户提供最安全的钱包。

在我们调研和多次的探讨后，排除了中心化托管的方式、Web3Auth 类型的 MPC 钱包、同时也放弃了 Metamask 这种类型的 EOA 钱包。而最终选择了 Privy 这种即由用户控制，又可以授权来进行满足跟单、挂单等操作。同时用户又持有并参与了 MPC 的过程。



## 为什么选择 Privy 和 JUP

AIME 是一款将用户资产安全放在第一位的款 DEX, 而对于钱包的开发、构建和选择一直是一件非常慎重的事情。

Privy 是一家非常优秀的企业，它们的钱包采用了 MPC 架构，并通过了 Cure53、Zellic 和 Doyensec 等公司的多项独立安全审计。

拥有 SOC2 I 及 II 安全认证。

而 Privy 采用的是非托管架构，这意味着用户完全控制着自己的资产，无人可以直接动手用户的资产。

当 AIME 需要使用用户的资产时，因交易优化、跟单等需求，需要用户授权给 AIME 后，才可以代替用户发起交易。

而这个过程用户是可以随时取消授权，将资产的管理权完全掌控在用户手中的。

同时 Privy 使用了 TEE（可信执行环境） 模式，密钥被分为多份分布在不同的安全边界，只有安全执行环境中，才可以临时重组出来用户的签名私钥，在此时才可以运用于某种特定的操作。

------

JUP 的核心目标是为 Solana 用户提供最佳的交易执行和价格发现。作为一个流动性聚合器，它连接了 Solana 上各种去中心化交易所 (DEX) 的流动性，允许用户以最优的价格和最低的滑点进行代币兑换。

其能够实时聚合 Solana 生态系统中多个 DEX（如 Raydium、Orca、Serum 等）的流动性。它通过智能路由算法，分析不同 DEX 上的价格和流动性，为用户找到最优的交易路径。

其特点：

* 智能订单路由
* 高效的执行速度
* 与 Solana 生态系统的深度集成
* 开放的 API 和 SDK

------

AIME 将用户要签名的交易通过 JUP 的核心 API  build 成为 unsignData 后，再通过 Privy 签名后得到 signTransaction，之后便将交易直接广播到链上。这使得中间没有任何人可以触碰到用户的任何信息。而通过 JUP 也可以得到优质的 buildData。





## 安装

### 必须需要的依赖
1. MySql(本示例版本8.0.34)



### 使用与安装
1. 下载项目
```shell
  # 下载项目
  git clone git@github.com:AIME-DOG/PrivyIntegration.git
  # 进入项目根目录
  cd ./PrivyIntegration
```
2. 安装需要的jar包
```shell
  mvn install
```
3. 修改配置文件 bootstrap.yml  
   bootstrap.yml文件位于src/main/resources目录。
```yaml
spring: 
  server:
  port: 8080
  servlet:
    context-path: /api
  spring:
    application:
      name: mpc-wallet
    messages:
      encoding: UTF-8
      basename: i18n/message
    datasource:
      driverClassName: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://<Host>:<Port>/<DB>?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useAffectedRows=true
      username: # 数据库登录名
      password: # 数据库登录密码
      druid:
        filters: stat
        maxActive: 20
        initialSize: 1
        maxWait: 60000
        minIdle: 1
        timeBetweenEvictionRunsMillis: 60000
        minEvictableIdleTimeMillis: 300000
        validationQuery: select 'x'
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
        poolPreparedStatements: true
        maxOpenPreparedStatements: 20
      type: com.alibaba.druid.pool.DruidDataSource
    profiles:
      active: dev

# Privy相关配置
privy:
  account:  # 登录账号
  host: https://auth.privy.io # API Host
  app-id:  # APP ID
  app-secret: # APP Secret
  public-key: # Public Key
  issuer: "privy.io"
  webhook-user-signing-key: # WebHook signing key
```
4. 编译项目  
   回到项目根目录，执行编译命令
```shell
  mvn package
```
5. 运行项目
   文件在项目根目录下的target目录下
```shell
  # 进入target目录
  cd ./target
  # 运行项目
  java -jar mpc-wallet-1.0.0-SNAPSHOT.jar
```


### 功能特点
1. 认证用户
2. Webhook  
   支持事件user.created(创建用户)、user.wallet_created(创建钱包)

### 注意事项
1. 本项目SpringBoot版本是3.0.6
2. JDK版本必须是17
3. MySQL必须是8及以上



## Link

* <img src=".assets/LOGO.png" alt="aime" width="32px" /> - [AIME](https://aime.dog)
* <img src="https://framerusercontent.com/images/oPqxoNxeHrQ9qgbjTUGuANdXdQ.png" alt="Privy" width="32px" /> - [Privy](https://www.privy.io/)
* <img src="https://dev.jup.ag/img/jupiter-logo.svg" alt="JUP" width="32px" /> - [JUP](https://dev.jup.ag/docs/)

