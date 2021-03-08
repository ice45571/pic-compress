# pic-compress

[下载链接](res/pic-compress-1.0.zip)

[demo演示视频](res/demo演示.mov)

<!-- Plugin description -->
利用 `https://tinypng.com/` 实现的图片压缩插件
<!-- Plugin description end -->

## v1.0
- 支持多个文件选中右键压缩
- 支持多个文件夹选中右键压缩
- 压缩采用 https://tinypng.com/ 的api，所以需要一个key，默认已配置一个，但是最好自己申请一个key插入到配置key文件的第一行，否则可能导致压缩份额满了后压缩失败
- 为考虑性能，暂不支持文件夹深层次遍历
- 目前仅支持png、jpeg、jpg三种图片压缩，不支持的文件将默认压缩失败

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "pic-compress"</kbd> >
  <kbd>Install Plugin</kbd>
- Manually:

  Download the [latest release](res/pic-compress-1.0.zip) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>
  
---
