# XiceSystemMessage - 自定义服务器系统消息

[![版本](https://img.shields.io/github/v/release/xice-1201/XiceSystemMessage?include_prereleases&label=版本&color=orange)](https://github.com/xice-1201/XiceSystemMessage/releases)
[![许可证](https://img.shields.io/github/license/xice-1201/XiceSystemMessage?label=许可证&color=green)](LICENSE)
![XiceMCLib](https://img.shields.io/badge/XiceMCLib-1.0--alpha-blue)
![Folia](https://img.shields.io/badge/Folia-支持-brightgreen)

XiceSystemMessage 是**Xice玄冰**系列插件之一，用于自定义服务器系统消息（如玩家加入、离开、达成进度、死亡等）的内容及可见性。

## 特性
- **玩家加入**：可以修改玩家加入服务器时的文本格式，并控制消息的可见范围（隐藏、仅自己或一定范围内的玩家）。
- **其它消息**：开发中。

## 依赖
- **[XiceMCLib](https://github.com/xice-1201/XiceMCLib)**：1.0-beta及以上

## 安装
- **服务器安装**：[Release 列表](https://github.com/xice-1201/XiceSystemMessage/releases)（需确保完成 [XiceMCLib](https://github.com/xice-1201/XiceMCLib) 的安装）
- **开发者安装**：暂无

## 配置
- **配置文件位置**：plugins/xice/systemMessage.yml（若不存在，插件会自动生成一份默认配置）
```yaml
login-message:
  enable: false # 是否启用自定义玩家登录消息
  visibility: global # 可见范围（disable：不可见；self：仅自己；nearby：附近玩家；world：当前维度；global：全服务器）
  content: "<green>欢迎 %player% 加入服务器！" # 玩家登录服务器时发送消息（支持 MiniMessage 格式与占位符）
  range: 10.0 # 若 visibility 为 nearby，设置玩家登录消息可见范围（单位：格）
```
- **占位符**：

| 占位符 | 描述 | 示例       |
|--------|------|----------|
| `%player%` | 玩家名称 | `CNXice` |

## 指令
| 指令 | 描述 | 权限 |
|------|------|------|
| `/xice systemMessage reload` | 重载配置文件 | `xicesystemmessage.reload`（默认 OP） |

## 构建与贡献
### 构建
```bash
# 从源码构建
git clone https://github.com/xice-1201/XiceSystemMessage.git
cd XiceSystemMessage
mvn clean package
```

### 贡献
欢迎提交 issue！
- 问题反馈：[GitHub Issues](https://github.com/xice-1201/XiceSystemMessage/issues)
- 报告 bug 时，提供服务器版本、插件版本与完整日志。

## 许可证
本项目基于 [MIT License](LICENSE) 开源，在保留版权声明的前提下可以自由使用、修改和分发。