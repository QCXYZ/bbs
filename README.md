# 接口文档 - 社交网络服务平台

## 1. 用户管理模块

### 1.1 用户注册与登录

#### POST /api/users/register

注册新用户。

**参数：**

- `username` (string): 用户名
- `password` (string): 密码
- `email` (string): 邮箱

**返回值：**

- `user_id` (int): 新注册用户的唯一标识符
- `message` (string): 注册结果消息

#### POST /api/users/login

用户登录。

**参数：**

- `username` (string): 用户名
- `password` (string): 密码

**返回值：**

- `token` (string): 授权令牌
- `user_id` (int): 用户的唯一标识符
- `message` (string): 登录状态消息

### 1.2 用户资料管理

#### GET /api/users/{user_id}/profile

获取用户资料。

**参数：**

- `user_id` (int): 用户的唯一标识符

**返回值：**

- `username` (string): 用户名
- `avatar` (string): 头像链接
- `nickname` (string): 昵称
- `bio` (string): 个人简介

#### PUT /api/users/{user_id}/profile

更新用户资料。

**参数：**

- `user_id` (int): 用户的唯一标识符
- `avatar` (string, 可选): 头像链接
- `nickname` (string, 可选): 昵称
- `bio` (string, 可选): 个人简介

**返回值：**

- `message` (string): 更新状态消息

### 1.3 安全性与隐私设置

#### PUT /api/users/{user_id}/password

修改密码。

**参数：**

- `user_id` (int): 用户的唯一标识符
- `old_password` (string): 旧密码
- `new_password` (string): 新密码

**返回值：**

- `message` (string): 修改密码的结果消息

## 2. 内容发布与管理模块

### 2.1 发布内容

#### POST /api/content

用户发布内容。

**参数：**

- `user_id` (int): 用户的唯一标识符
- `content_type` (string): 内容类型（text, image, video）
- `content` (string): 内容体
- `tags` ([string], 可选): 标签数组

**返回值：**

- `content_id` (int): 内容的唯一标识符
- `message` (string): 发布结果消息

### 2.2 内容编辑与删除

#### PUT /api/content/{content_id}

编辑内容。

**参数：**

- `content_id` (int): 内容的唯一标识符
- `content` (string): 新的内容体
- `tags` ([string], 可选): 标签数组

**返回值：**

- `message` (string): 编辑结果消息

#### DELETE /api/content/{content_id}

删除内容。

**参数：**

- `content_id` (int): 内容的唯一标识符

**返回值：**

- `message` (string): 删除结果消息

## 3. 社交互动模块

### 3.1 好友系统

#### POST /api/friends

添加好友。

**参数：**

- `user_id` (int): 发起请求的用户的唯一标识符
- `friend_id` (int): 被添加用户的唯一标识符

**返回值：**

- `message` (string): 添加结果消息

### 3.2 消息系统

#### POST /api/messages

发送私信。

**参数：**

- `sender_id` (int): 发送者的唯一标识符
- `receiver_id` (int): 接收者的唯一标识符
- `message` (string): 消息内容

**返回值：**

- `message_id` (int): 消息的唯一标识符
- `status` (string): 发送状态

### 3.3 通知系统

#### GET /api/notifications/{user_id}

获取用户通知。

**参数：**

- `user_id` (int): 用户的唯一标识符

**返回值：**

- `notifications` ([{`type`: string, `message`: string, `timestamp`: string}]): 用户收到的通知列表，包括通知类型、消息和时间戳

## 4. 搜索与推荐模块

### 4.1 搜索功能

#### GET /api/search

执行搜索操作。

**参数：**

- `query` (string): 搜索关键词
- `type` (string, 可选): 搜索类型（"content", "tags", "users"）

**返回值：**

- `results` (array): 搜索结果，具体内容取决于搜索类型

### 4.2 内容推荐

#### GET /api/recommendations/{user_id}

根据用户兴趣推荐内容。

**参数：**

- `user_id` (int): 用户的唯一标识符

**返回值：**

- `recommendations` ([{`content_id`: int, `content_type`: string, `content`: string}]): 推荐内容列表，包括内容ID、类型和内容体

## 5. 后台管理系统

### 5.1 用户管理

#### GET /api/admin/users

管理员获取用户列表。

**返回值：**

- `users` ([{`user_id`: int, `username`: string, `status`: string}]): 用户列表，包括用户ID、用户名和账户状态

#### PUT /api/admin/users/{user_id}

管理员管理特定用户账户。

**参数：**

- `user_id` (int): 用户的唯一标识符
- `action` (string): 执行操作（"disable", "enable", "delete"）

**返回值：**

- `message` (string): 执行结果消息

### 5.2 内容审查

#### GET /api/admin/content

获取需要审查的内容列表。

**返回值：**

- `contents` ([{`content_id`: int, `content`: string, `status`: string}]): 内容列表，包括内容ID、内容体和状态

#### DELETE /api/admin/content/{content_id}

管理员删除违规内容。

**参数：**

- `content_id` (int): 内容的唯一标识符

**返回值：**

- `message` (string): 删除结果消息

### 5.3 数据统计

#### GET /api/admin/stats

获取网站运营的统计数据。

**返回值：**

- `statistics` ({`user_activity`: [{`date`: string, `active_users`: int}], `content_interaction`: [{`content_id`: int, `interactions`: int}]}):
  用户活跃度和内容互动统计数据

## 6. 移动适配

### 6.1 响应式设计检查

#### GET /api/mobile/check

检查网站在不同设备上的显示情况。

**返回值：**

- `device_compatibility` ([{`device_type`: string, `compatible`: boolean}]): 设备类型和兼容性状态列表

此接口文档旨在提供详尽的信息和规范的格式，确保开发者可以依据此文档进行高效、规范的接口开发和集成。
