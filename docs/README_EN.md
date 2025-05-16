# AIME Wallet Middle Service
<img src=".assets/LOGO+AIME-light.png" alt="AIME" style="zoom: 50%;" align="center"/>



AIME.DOG: The First Social Multi-Chain DEX | Explore Alpha First

AIME is the first social-driven multi-chain DEX. It aims to create a globally user-friendly decentralized exchange, allowing users to integrate trading, socializing, discussion, and copy trading into one seamless experience.

AIME adopts an MPC wallet structure, ensuring that users truly own their assets.

Want to read this in English? Go here
希望查看 [中文文档](README.md)？

<p align="center">
  <a href="https://docs.aime.dog/">Docs</a> - <a href="https://aime.dog/">AIME</a> - <a href="https://aime.dog/invite">Invite</a> - <a href="https://aime.dog/channel">Channel</a>
</p>

<p align="center">
    <img src ="https://img.shields.io/badge/version-0.1.0-blueviolet.svg"/>
    <img src ="https://img.shields.io/badge/platform-linux|macos-yellow.svg"/>
    <img src ="https://img.shields.io/badge/database-mysql|8.0+-orange.svg"/>
    <img src ="https://img.shields.io/badge/java-17-blue.svg" />
</p>


## Original Intent

AIME is dedicated to providing users with a secure, simple, and easy-to-use DEX. We prioritize the security of users' assets above all else, while ensuring users enjoy an experience similar to that of centralized exchanges.

### Wallet Selection

We conducted a comprehensive comparison of many wallets, using the decentralization philosophy as the baseline to offer the safest wallet for users.

After extensive research and discussion, we ruled out centralized custody methods, Web3Auth-type MPC wallets, and also decided against EOA wallets like Metamask. Ultimately, we chose a wallet like Privy, which allows user control and authorization for operations such as copy trading and order placement. Users also participate in the MPC process.

## Why Choose Privy and JUP

AIME is a DEX that places the highest priority on the security of user assets. The development, construction, and selection of wallets have always been approached with great caution.

Privy is an outstanding company whose wallet adopts an MPC architecture and has passed multiple independent security audits by companies such as Cure53, Zellic, and Doyensec.

It holds SOC2 Type I and II security certifications.

Privy uses a non-custodial architecture, meaning users have complete control over their assets, and no one can directly manipulate them.

When AIME needs to use user assets for trading optimization, copy trading, or other needs, it requires user authorization before initiating transactions on behalf of the user.

This process allows users to revoke authorization at any time, keeping the management of assets entirely in their hands.

Privy also employs the TEE (Trusted Execution Environment) model, where keys are divided and distributed across different security boundaries, and can only be temporarily reconstructed in a secure execution environment to perform specific operations.

------

JUP's core goal is to provide Solana users with the best trade execution and price discovery. As a liquidity aggregator, it connects liquidity from various decentralized exchanges (DEXs) on Solana, allowing users to exchange tokens at the best prices with minimal slippage.

It can aggregate liquidity from multiple DEXs (such as Raydium, Orca, Serum, etc.) in the Solana ecosystem in real-time. Through intelligent routing algorithms, it analyzes prices and liquidity across different DEXs to find the optimal trading path for users.

Features:
Intelligent order routing
Efficient execution speed
Deep integration with the Solana ecosystem
Open API and SDK

------

AIME uses JUP's core API to build the user's transaction into unsignData, then signs it with Privy to obtain signTransaction, and finally broadcasts the transaction directly to the blockchain. This ensures that no one can access any user information in the process. Through JUP, high-quality buildData can also be obtained.

## install

### required
1. MySql(本示例版本8.0.34)


### build & install
1. clone on github
```shell
  git clone git@github.com:AIME-DOG/PrivyIntegration.git
  cd ./PrivyIntegration
```
2. install jar of required 
```shell
  mvn install
```
3. update config bootstrap.yml  
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
      username: # database user name
      password: # database pwd
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

# Privy config
privy:
  account:  # login account
  host: https://auth.privy.io # API Host
  app-id:  # APP ID
  app-secret: # APP Secret
  public-key: # Public Key
  issuer: "privy.io"
  webhook-user-signing-key: # WebHook signing key
```
4. Build Project  
   Return to the project root directory and execute the build command.
```shell
  mvn package
```
5. Run Project
   The file is located in the `target` directory at the root of the project.
```shell
  # goto target dir
  cd ./target
  # run
  java -jar mpc-wallet-1.0.0-SNAPSHOT.jar
```


### 功能特点
1. Authenticate users.
2. Webhook
Supports events: `user.created` (user creation) and `user.wallet_created` (wallet creation).

### 注意事项
1. The Spring Boot version for this project is 3.0.6.
2. The JDK version must be 17.
3. MySQL must be version 8 or above.



## Link

* <img src=".assets/LOGO.png" alt="aime" width="32px" /> - [AIME](https://aime.dog)
* <img src="https://framerusercontent.com/images/oPqxoNxeHrQ9qgbjTUGuANdXdQ.png" alt="Privy" width="32px" /> - [Privy](https://www.privy.io/)
* <img src="https://dev.jup.ag/img/jupiter-logo.svg" alt="JUP" width="32px" /> - [JUP](https://dev.jup.ag/docs/)

