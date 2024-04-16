# 声明
该项目仅用于教学用途，不存在商业用途。

# 项目背景
基于社区发展和学员学习进阶需要，C2N社区推出启动台项目。整体项目定位是社区基础项目发行平台。
项目除了满足学习用途，更加鼓励同学在平台贡献自己的智慧和代码。

项目发展一共分为三个阶段：
#### 第一阶段：学习和任务阶段，C2N技术团队和社区同学一起迭代该项目（4-5月份）
#### 第二阶段：社区内部项目孵化阶段，满足社区同学发挥团队的创造力（6月份开始）
#### 第三阶段：外部合作和开源发展阶段（待定）

# 演示地址
https://c2-n-launchpad.vercel.app/

# 产品需求
内部版本没有kyc，注册流程
C2N launchpad是一个区块链上的一个去中心化发行平台，专注于启动和支持新项目。它提供了一个平台，允许新的和现有的项目通过代币销售为自己筹集资金，同时也为投资者提供了一个参与初期项目投资的机会。下面是C2N launchpad产品流程的大致分析：
1. 项目申请和审核
- 申请：项目方需要在C2N launchpad上提交自己项目的详细信息，包括项目介绍、团队背景、项目目标、路线图、以及如何使用筹集的资金等。
- 审核：C2N launchpad团队会对提交的项目进行审核，评估项目的可行性、团队背景、项目的创新性、以及社区的兴趣等。这一过程可能还包括与项目方的面对面或虚拟会议。
2. 准备代币销售
- 设置条款：一旦项目被接受，C2N launchpad和项目方将协商代币销售的具体条款，包括销售类型（如公开销售或种子轮）、价格、总供应量、销售时间等。
- 准备市场：同时，项目方需要准备营销活动来吸引潜在的投资者。C2N launchpad也可能通过其平台和社区渠道为项目提供曝光。
3. KYC和白名单
- KYC验证：为了符合监管要求，参与代币销售的投资者需要完成Know Your Customer（KYC）验证过程。
- 白名单：完成KYC的投资者可能需要被添加到白名单中，才能在代币销售中购买代币。
4. 代币销售
- 销售开启：在预定时间，代币销售开始。根据销售条款，投资者可以购买项目方的代币。
- 销售结束：销售在达到硬顶或销售时间结束时关闭。
5. 代币分发
- 代币分发：销售结束后，购买的代币将根据约定的条款分发给投资者的钱包。

用户质押平台币，获得参与项目IDO的购买权重，后端配置项目信息并操作智能合约生成新的sale，用户在sale开始之后进行购买，项目结束后，用户进行claim

## 平台流程参考
https://medium.com/avalaunch/avalunch-tutorials-platform-overview-1675547b5aff

# 功能操作
## 初始化
要进行下面的流程，需要提前准备sepolia的测试代币作为gas
1. 连接钱包(推荐metamask)
![image](https://github.com/TechPlanB/C2N-Launchpad/assets/24291805/9450d4a1-6f14-41cd-8a24-dc7d550ff820)

2. 切换网络到sepolia，或者可以直接在钱包里面进行切换
![image](https://github.com/TechPlanB/C2N-Launchpad/assets/24291805/deb1d9f7-fe64-416b-9a08-9264535829eb)


## Farm 流程
1. Farm 流程需要用到我们的Erc20测试代币C2N, 可以在首页领取C2N(一个账户只能领取一次),并且添加到我们metamask，添加之后我们可以在metamask 看到我们领取的C2N 代币
![image](https://github.com/TechPlanB/C2N-Launchpad/assets/24291805/f0e78e0d-139e-451f-892b-4b5c797efd85)

2. 在我们farm界面，我们可以质押fc2n 代币获取c2n, (方便大家操作，我们的测试网fc2n，c2n 是在上一步中领取的同一代币)，在这里我们有三个操作，stake:质押，unstake(witthdraw):撤回质押， 以及 claim:领取奖励;
![image](https://github.com/TechPlanB/C2N-Launchpad/assets/24291805/6776475a-0f55-41b4-84ef-b1d190455fe5)

点击stake 或者claim 进入对应的弹窗，切换tab可以进行对应的操作；
3. Stake ，输入要质押的FC2N代币数量，点击stake 会唤起钱包，在钱包中confirm，然后等待交易完成；
![image](https://github.com/TechPlanB/C2N-Launchpad/assets/24291805/e04bba21-cd13-4c97-9163-48ed607e37fe)

我们新增质押了1FC2N,交易完成之后我们会看到，My staked 从0.1 变成1.1;
Total staked 的更新是一个定时任务，我们需要等待一小段时间之后才能看到更新
![image](https://github.com/TechPlanB/C2N-Launchpad/assets/24291805/90e1b5e3-602e-4906-a44b-939257980707)

3. Claim 领取质押奖励的C2N,点击claim 并且在钱包确认
![image](https://github.com/TechPlanB/C2N-Launchpad/assets/24291805/4d19fb08-b594-4691-a1b8-f9ea4cff61f3)

交易完成后我们会看到Available的FC2N数量增加了96，钱包里面C2N的代币数量同样增加了96
![image](https://github.com/TechPlanB/C2N-Launchpad/assets/24291805/cb3f5bd1-414f-413d-afa2-5ad57a4a0413)

4. Unstake(withdraw),输入需要撤回的FC2N 数量(小于已经质押的Balance)，点击withdraw，并且在钱包确认交易
![image](https://github.com/TechPlanB/C2N-Launchpad/assets/24291805/dd67ed78-7b9b-4b8d-b1ff-394897b721cb)

unstake 完成后我们可以看到my staked 的数量变为0
# 技术文档
合约开发

项目核心由两个合约组成，以下列出需要实现的函数功能
## AllocationStaking.sol
关系调用
![image](https://github.com/TechPlanB/C2N-Launchpad/assets/24291805/b2e1857e-ad5b-46fa-a573-cce00c3d2507)

函数说明
暂时无法在飞书文档外展示此内容
## BrewerySale.sol 功能
关系调用
![image](https://github.com/TechPlanB/C2N-Launchpad/assets/24291805/897ded45-185f-4c2e-80cf-cda678667fe9)

函数调用
暂时无法在飞书文档外展示此内容
技术依赖
OpenZeppelin
OpenZeppelin库提供了一些安全的合约实现，如ERC20、SafeMath等。

前端开发
WIP

后端开发
数据库输入项目信息，配合合约sale显示项目进度和用户购买信息

# 学员任务
为了帮助学员逐步完成以太坊智能合约C2N Launchpad开发的学习任务，下面我将根据合约代码，拆分出一系列循序渐进的开发任务，并提供详细的文档。这将帮助学员理解并实践如何构建一个基于以太坊的农场合约（Farming contract），用于分配基于用户质押的流动性证明（LP tokens）的ERC20代币奖励。
## 开发任务拆分
### 任务一：了解基础合约和库的使用
1. 阅读和理解OpenZeppelin库的文档：熟悉IERC20、SafeERC20、SafeMath、Ownable这些库的功能和用途。
2. 创建基础智能合约结构：根据给定的智能合约代码，理解合约结构和主要变量的定义。
### 任务二：用户和池子信息结构定义
1. 定义用户信息结构（UserInfo）：
  - 学习如何在Solidity中定义结构体。
  - 添加注释，解释每个字段的含义。
2. 定义池子信息结构（PoolInfo）：
  - 理解并定义池子信息，包括LP代币地址、分配点、最后奖励时间戳等。
### 任务三：合约构造函数和池子管理
1. 编写合约的构造函数：
  - 初始化ERC20代币地址、奖励生成速率和起始时间戳。
2. 实现添加新的LP池子的功能（add函数）：
  - 学习权限管理，确保只有合约拥有者可以添加池子。
### 任务四：奖励机制的实现
1. 编写更新单个池子奖励的函数（updatePool）：
  - 理解如何计算每个池子的累计ERC20代币每股份额。
2. 实现用户存入和提取LP代币的功能（deposit和withdraw函数）：
  - 理解如何更新用户的amount和rewardDebt。
### 任务五：紧急提款和奖励分配
1. 实现紧急提款功能（emergencyWithdraw函数）：
  - 让用户在紧急情况下提取他们的LP代币，但不获取奖励。
2. 实现ERC20代币转移的内部函数（erc20Transfer）：
  - 确保奖励正确支付给用户。
### 任务六：合约测试和部署
1. 编写测试用例：
  - 使用如Truffle或Hardhat的框架进行合约测试。
2. 部署合约到测试网络（如Ropsten或Rinkeby）：
  - 学习如何在公共测试网络上部署和管理智能合约。
### 任务七：前端集成和交互
1. 开发一个简单的前端应用：
  - 使用Web3.js或Ethers.js与智能合约交互。
2. 实现用户界面：
  - 允许用户通过网页界面存入、提取LP代币，查看待领取奖励。

## 任务重难点分析

在上述的智能合约代码中，奖励机制的核心功能围绕着分配ERC20代币给在不同流动性提供池（LP pools）中质押LP代币的用户。这个过程涉及多个关键步骤和计算，用以确保每个用户根据其质押的LP代币数量公平地获得ERC20代币奖励。下面将详细解释这个奖励机制的实现过程。
### 奖励计算原理
1. 用户信息（UserInfo）和池子信息（PoolInfo）：
  - UserInfo 结构存储了用户在特定池子中质押的LP代币数量（amount）和奖励债务（rewardDebt）。奖励债务表示在最后一次处理后，用户已经计算过但尚未领取的奖励数量。
  - PoolInfo 结构包含了该池子的信息，如LP代币地址、分配点（用于计算该池子在总奖励中的比例）、最后一次奖励时间戳、累计每股分配的ERC20代币数（accERC20PerShare）等。
2. 累计每股分配的ERC20代币（accERC20PerShare）的计算：
  - 当一个池子接收到新的存款、提款或奖励分配请求时，系统首先调用updatePool函数来更新该池子的奖励变量。
  - 计算从上一次奖励到现在的时间内，该池子应分配的ERC20代币总量。这个总量是基于时间差、池子的分配点和每秒产生的奖励量来计算的。
  - 将计算出的奖励按照池子中总LP代币数量平分，更新accERC20PerShare，确保每股的奖励反映了新加入的奖励。
3. 用户奖励的计算：
  - 当用户调用deposit或withdraw函数时，合约首先计算用户在这次操作前的待领取奖励。
  - 待领取奖励是通过将用户质押的LP代币数量乘以池子的accERC20PerShare，然后减去用户的rewardDebt来计算的。这样可以得到自上次用户更新以来所产生的新奖励。
  - 用户完成操作后，其amount（如果是存款则增加，如果是提款则减少）和rewardDebt都将更新。新的rewardDebt是用户更新后的LP代币数量乘以最新的accERC20PerShare。
### 奖励发放
- 在用户进行提款（withdraw）操作时，计算的待领取奖励会通过erc20Transfer函数直接发送到用户的地址。
- 这种奖励分配机制确保了用户每次质押状态变更时，都会根据其质押的时间和数量公平地获得相应的ERC20代币奖励。
通过这种设计，智能合约能够高效且公平地管理多个LP池子中的奖励分配，使得用户对质押LP代币和领取奖励的过程感到透明和公正。
