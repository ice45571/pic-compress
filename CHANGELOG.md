<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# pic-compress Changelog

## [Unreleased]
### v1.0
- 支持多个文件选中右键压缩
- 支持多个文件夹选中右键压缩
- 压缩采用 `https://tinypng.com/` 的api，所以需要一个key，默认已配置一个，但是最好自己申请一个key插入到配置key文件的第一行，否则可能导致压缩份额满了后压缩失败
- 为考虑性能，暂不支持文件夹深层次遍历
- 目前仅支持png、jpeg、jpg三种图片压缩，不支持的文件将默认压缩失败
