一个lrc文件的解析器，在实现音乐播放器的时候因为网上找不到合适的解析器就只能自己写了。

这个解析器更注重对性能的追求

相比初版，这个版本能适应标准lrc格式和各种各样的花式变种，因为国内各个音乐平台下载到的lrc文件花样实在太多了，标准的00:00.00被魔改成了各个样式，导致普通的字符串分割经常出错，所以最后决定选择这样的算法

这个解析器已经确认与JavaFx的MediaPlayer配合正常工作，其他的方面欢迎自行测试


A parser for lrc files, when i try to write a music player,i can't find a suitable parser on the Internet, so i can only write it myself.

This parser pays more attention to the pursuit of performance

Compared with the first version, this version can adapt to the standard lrc format and various fancy variants, because there are too many lrc files downloaded by music platforms. The standard 00:00.00 has been changed to various styles. As a result, ordinary string segmentation often makes mistakes, so I finally decided to choose such an algorithm

This parser has been confirmed to work properly with JavaFx MediaPlayer, other aspects are welcome to test by yourself
