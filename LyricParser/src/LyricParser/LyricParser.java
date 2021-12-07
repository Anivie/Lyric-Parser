package LyricParser;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Anivie
 */
public class LyricParser {
    private final String[] timeLine;
    private final String[] lyric;

    /**
     *        初始化解析器，这个构造方法将自适应精度
     * @param lyricFile lrc文件
     * @param charset
     */
    @SneakyThrows
    public LyricParser(File lyricFile,Charset charset) {
        List<String> lines = Files.readAllLines(lyricFile.toPath(), charset);

        timeLine = lines.parallelStream().
                map(s -> s.substring(1, s.indexOf(']')))
                .filter(this::isTimeLine)
                .toArray(String[]::new);
        lyric = lines.parallelStream().
                map(s -> s.substring(s.indexOf(']') + 1))
                .toArray(String[]::new);
        lines = null;
    }


    /**
     *        初始化解析器,自定义精度(最高性能)
     *        警告！如果你选择了一个错误的精度，或者歌词文件的时间轴精度不一致，将直接导致解析器甚至整个程序报错退出，
     *        因此，请确认目标文件是安全的，否则请使用自定义精度。
     * @param lyricFile lrc文件
     * @param isHighPrecision 是否启用高精度。低精度：[00:00.00],高精度：[00:00.000]
     * @param charset
     */
    @SneakyThrows
    public LyricParser(File lyricFile, boolean isHighPrecision, Charset charset) {
        List<String> lines = Files.readAllLines(lyricFile.toPath(), charset);

        if (isHighPrecision){
            timeLine = lines.parallelStream().
                    map(s -> s.substring(1, 10))
                    .filter(this::isTimeLine)
                    .toArray(String[]::new);
            lyric = lines.parallelStream().
                    map(s -> s.substring(11))
                    .toArray(String[]::new);
        }else {
            timeLine = lines.parallelStream().
                    map(s -> s.substring(1, 9))
                    .filter(this::isTimeLine)
                    .toArray(String[]::new);
            lyric = lines.parallelStream().
                    map(s -> s.substring(10))
                    .toArray(String[]::new);
        }

        lines = null;
    }


    public int[] getTimeLineWithArray() {
        return Arrays.stream(timeLine)
                .parallel()
                .mapToInt(line -> Integer.parseInt(line.substring(0,2)) * 60000 + Integer.parseInt(line.substring(3,5)) * 1000 + Integer.parseInt(line.substring(6)))
                .toArray();
    }

    public Duration[] getTimeLineWithDuration() {
        return IntStream.of(getTimeLineWithArray()).parallel()
                .mapToObj(Duration::ofMillis)
                .toArray(Duration[]::new);
    }

    public String[] getLyric() {
        return lyric;
    }

    public LinkedHashMap<Duration,String> getLyricWithLinkedHashMap() {
        LinkedHashMap<Duration, String> map = new LinkedHashMap<>(timeLine.length);
        Duration[] durations = getTimeLineWithDuration();
        for (int i = 0; i < timeLine.length; i++) map.put(durations[i],timeLine[i]);
        return map;
    }

    public HashMap<Duration,String> getLyricWithHashMap() {
        HashMap<Duration, String> map = new HashMap<>(timeLine.length);
        Duration[] durations = getTimeLineWithDuration();
        for (int i = 0; i < timeLine.length; i++) map.put(durations[i],timeLine[i]);
        return map;
    }

    /**
     * 去除ID tag
     * Remove ID tag
     */
    private boolean isTimeLine(String line) {
        for (byte b : line.getBytes(StandardCharsets.US_ASCII)) if ((b > 64 && b < 91) || (b > 96 && b < 123)) return false;
        return true;
    }
}
