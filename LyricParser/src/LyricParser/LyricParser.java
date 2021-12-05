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

    @SneakyThrows
    public LyricParser(File lyricFile, Charset charset) {
        List<String> lines = Files.readAllLines(lyricFile.toPath(), charset);

        timeLine = lines.parallelStream().
                map(s -> s.substring(1, s.indexOf("]")))
                .filter(this::isTimeLine)
                .toArray(String[]::new);
        lyric = lines.parallelStream().
                map(s -> s.substring(s.indexOf("]") + 1))
                .toArray(String[]::new);
        lines = null;
    }

    public int[] getTimeLineWithArray() {
        int[] back = new int[timeLine.length];
        for (int i = 0; i < timeLine.length; i++) back[i] = Integer.parseInt(timeLine[i].substring(0,2)) * 60000 + Integer.parseInt(timeLine[i].substring(3,5)) * 1000 + Integer.parseInt(timeLine[i].substring(6));
        return back;
    }

    public Duration[] getTimeLineWithDuration() {
        Duration[] back = new Duration[timeLine.length];
        int[] time = getTimeLineWithArray();
        for (int i = 0; i < timeLine.length; i++) back[i] = Duration.ofMillis(time[i]);
        return back;
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
