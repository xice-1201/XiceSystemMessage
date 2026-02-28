# XiceSystemMessage - 自定义服务器系统消息

[![版本](https://img.shields.io/badge/版本-1.1--alpha-orange)](https://github.com/xice-1201/XiceSystemMessage/releases)
[![许可证](https://img.shields.io/github/license/xice-1201/XiceSystemMessage?label=许可证&color=green)](LICENSE)
![XiceMCLib](https://img.shields.io/badge/XiceMCLib-1.1--alpha-blue)
![Folia](https://img.shields.io/badge/Folia-支持-brightgreen)

XiceSystemMessage 是**Xice玄冰**系列插件之一，用于自定义服务器系统消息（如玩家加入、离开、达成进度、死亡等）的内容及可见性。

## 特性
- **系统消息自定义**：可以修改游戏中部分系统消息的文本内容与格式，并控制消息的可见范围（隐藏、仅自己、一定范围内的玩家或全体玩家）。
- **消息监听**：可自定义的消息包括玩家登录、玩家退出。
- **灵活可见性控制**：支持多种可见范围（disable：不可见；self：仅自己；nearby：附近玩家；world：当前世界；global：全服务器）
- **MiniMessage 支持**：支持 MiniMessage 格式的彩色文本和样式
- **占位符系统**：支持动态占位符替换

## 依赖
- **[XiceMCLib](https://github.com/xice-1201/XiceMCLib)**：1.1-alpha及以上

## 安装
- **服务器安装**：[Release 列表](https://github.com/xice-1201/XiceSystemMessage/releases)（需确保完成 [XiceMCLib](https://github.com/xice-1201/XiceMCLib) 的安装）
- **开发者安装**：请参考下方的"构建与开发"部分

## 配置
- **配置文件位置**：plugins/xice/systemMessage.yml（若不存在，插件会自动生成一份默认配置）
```yaml
login-message:
  # 是否启用自定义玩家登录消息
  enable: false
  # 可见范围（disable：不可见；self：仅自己；nearby：附近玩家；world：当前世界；global：全服务器）
  visibility: global
  # 玩家登录服务器时发送消息（支持 MiniMessage 格式与占位符）
  content: "<green>欢迎 %player% 加入服务器！"
  # 若 login-message.visibility 为 nearby，设置玩家登录消息可见范围（单位：格）
  range: 10.0
quit-message:
  # 是否启用自定义玩家退出消息
  enable: false
  # 可见范围（disable：不可见；self：仅自己；nearby：附近玩家；world：当前世界；global：全服务器）
  visibility: global
  # 玩家退出服务器时发送消息（支持 MiniMessage 格式与占位符）
  content: "<red>%player% 离开了服务器！"
  # 若 quit-message.visibility 为 nearby，设置玩家退出消息可见范围（单位：格）
  range: 10.0
```
- **占位符**：

| 占位符             | 描述      | 示例                    |
|-----------------|---------|-----------------------|
| `%player%`      | 玩家名称    | `CNXice`              |
| `%currentTime%` | 当前服务器时间 | `2026-01-01 00:00:00` |

## 指令
| 指令 | 描述 | 权限 |
|------|------|------|
| `/xice systemMessage reload` | 重载配置文件 | `xicesystemmessage.reload`（默认 OP） |

## 构建与开发

### 构建要求
- **Java**: 版本 21 或更高
- **Maven**: 3.6.0 或更高
- **依赖**: 需要先构建并安装 XiceMCLib 到本地 Maven 仓库

### 构建步骤
```bash
# 1. 确保 XiceMCLib 已安装到本地仓库
cd XiceMCLib
mvn clean install

# 2. 构建 XiceSystemMessage
cd ../XiceSystemMessage
mvn clean package
```

构建成功后，JAR 文件将位于 `target/XiceSystemMessage-1.1-alpha.jar`

### 开发者安装
对于开发者，可以使用以下命令将插件安装到本地 Maven 仓库：
```bash
mvn clean install
```

### 贡献
欢迎提交 issue 和 pull request！
- 问题反馈：[GitHub Issues](https://github.com/xice-1201/XiceSystemMessage/issues)
- 报告 bug 时，请提供：
  - 服务器版本（Paper/Folia 版本）
  - 插件版本
  - XiceMCLib 版本
  - 完整的服务器日志
  - 复现步骤

## 许可证
本项目基于 [MIT License](LICENSE) 开源，在保留版权声明的前提下可以自由使用、修改和分发。