一个lrc文件的解析器，在项目中发现现有的解析器有些差强人意于是自己实现了一遍，编写的时候以最佳性能为编写目标。

这个解析器更注重对性能的追求

相比初版，这个版本能适应标准lrc格式和各种各样的花式变种，因为国内各个音乐平台下载到的lrc文件花样实在太多了，标准的00:00.00被魔改成了各个样式，导致普通的字符串分割经常出错，所以最后决定选择这样的算法

这个解析器已经确认与JavaFx的MediaPlayer配合正常工作，其他的方面欢迎自行测试


A parser for lrc files, when i try to write a music player,i can't find a suitable parser on the Internet, so i can only write it myself.

This parser pays more attention to the pursuit of performance

Compared with the first version, this version can adapt to the standard lrc format and various fancy variants, because there are too many lrc files downloaded by music platforms. The standard 00:00.00 has been changed to various styles. As a result, ordinary string segmentation often makes mistakes, so I finally decided to choose such an algorithm

This parser has been confirmed to work properly with JavaFx MediaPlayer, other aspects are welcome to test by yourself

Simple Demo:
```
import LyricParser.LyricParser;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * @author Anivie
 * Use:JavaFx17.0.1
 */
public class MainView extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MediaPlayer player = new MediaPlayer(new Media(new File("b.mp3").toURI().toString()));
        LyricParser parser = new LyricParser(new File("b.lrc"), StandardCharsets.UTF_8);
        Duration[] duration = parser.getTimeLineWithDuration();
        String[] lyrics = parser.getLyric();
        final int[] index = {0};
        player.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.toMillis() < duration[index[0]].toMillis()) return;
            System.out.println(lyrics[index[0]++]);
        });
        player.play();
    }
}
```
