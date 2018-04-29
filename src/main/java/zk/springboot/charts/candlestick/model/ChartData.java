package zk.springboot.charts.candlestick.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ChartData {
    static List<Object[]> data = new ArrayList<Object[]>();
    static {
        data.add(new Object[] {time(2010, 5, 6, 13, 57, 0), new Double(1116.50), new Double(1119.50), new Double(1115.75), new Double(1118.75),
                new Double(26534)});
        data.add(new Object[] {time(2010, 5, 6, 13, 58, 0), new Double(1118.75), new Double(1118.75), new Double(1113.75), new Double(1115.50),
                new Double(22715)});
        data.add(new Object[] {time(2010, 5, 6, 13, 59, 0), new Double(1115.75), new Double(1116.00), new Double(1110.75), new Double(1112.75),
                new Double(20766)});
        data.add(new Object[] {time(2010, 5, 6, 14, 0, 0), new Double(1112.75), new Double(1112.75), new Double(1103.25), new Double(1106.00),
                new Double(30059)});
        data.add(new Object[] {time(2010, 5, 6, 14, 1, 0), new Double(1105.75), new Double(1115.25), new Double(1105.50), new Double(1113.00),
                new Double(20329)});
        data.add(new Object[] {time(2010, 5, 6, 14, 2, 0), new Double(1112.75), new Double(1116.50), new Double(1110.50), new Double(1116.25),
                new Double(20395)});
        data.add(new Object[] {time(2010, 5, 6, 14, 3, 0), new Double(1116.50), new Double(1121.00), new Double(1115.75), new Double(1120.00),
                new Double(23156)});
        data.add(new Object[] {time(2010, 5, 6, 14, 4, 0), new Double(1120.00), new Double(1121.25), new Double(1117.75), new Double(1119.00),
                new Double(22293)});
        data.add(new Object[] {time(2010, 5, 6, 14, 5, 0), new Double(1119.00), new Double(1123.50), new Double(1116.75), new Double(1123.50),
                new Double(27369)});
        data.add(new Object[] {time(2010, 5, 6, 14, 6, 0), new Double(1123.50), new Double(1124.00), new Double(1120.25), new Double(1121.75),
                new Double(22120)});
        data.add(new Object[] {time(2010, 5, 6, 14, 7, 0), new Double(1121.75), new Double(1126.50), new Double(1121.00), new Double(1126.00),
                new Double(24415)});
        data.add(new Object[] {time(2010, 5, 6, 14, 8, 0), new Double(1126.25), new Double(1131.25), new Double(1125.50), new Double(1131.25),
                new Double(27627)});
        data.add(new Object[] {time(2010, 5, 6, 14, 9, 0), new Double(1131.00), new Double(1131.75), new Double(1125.00), new Double(1126.25),
                new Double(28348)});
        data.add(new Object[] {time(2010, 5, 6, 14, 10, 0), new Double(1126.50), new Double(1128.00), new Double(1125.75), new Double(1127.75),
                new Double(18702)});
        data.add(new Object[] {time(2010, 5, 6, 14, 11, 0), new Double(1128.00), new Double(1128.00), new Double(1117.50), new Double(1118.25),
                new Double(25499)});
        data.add(new Object[] {time(2010, 5, 6, 14, 12, 0), new Double(1118.25), new Double(1122.25), new Double(1117.75), new Double(1120.25),
                new Double(16323)});
        data.add(new Object[] {time(2010, 5, 6, 14, 13, 0), new Double(1120.25), new Double(1122.50), new Double(1119.00), new Double(1121.25),
                new Double(11372)});
        data.add(new Object[] {time(2010, 5, 6, 14, 14, 0), new Double(1121.25), new Double(1121.75), new Double(1119.25), new Double(1119.75),
                new Double(8121)});
        data.add(new Object[] {time(2010, 5, 6, 14, 15, 0), new Double(1119.75), new Double(1121.25), new Double(1118.00), new Double(1120.25),
                new Double(15347)});
        data.add(new Object[] {time(2010, 5, 6, 14, 16, 0), new Double(1120.25), new Double(1122.75), new Double(1120.25), new Double(1122.00),
                new Double(12236)});
        data.add(new Object[] {time(2010, 5, 6, 14, 17, 0), new Double(1121.75), new Double(1125.50), new Double(1121.00), new Double(1125.00),
                new Double(12052)});
        data.add(new Object[] {time(2010, 5, 6, 14, 18, 0), new Double(1125.00), new Double(1125.75), new Double(1118.50), new Double(1118.75),
                new Double(21474)});
        data.add(new Object[] {time(2010, 5, 6, 14, 19, 0), new Double(1119.00), new Double(1119.00), new Double(1115.25), new Double(1115.75),
                new Double(20824)});
        data.add(new Object[] {time(2010, 5, 6, 14, 20, 0), new Double(1115.75), new Double(1116.00), new Double(1111.75), new Double(1112.25),
                new Double(20310)});
        data.add(new Object[] {time(2010, 5, 6, 14, 21, 0), new Double(1112.00), new Double(1113.50), new Double(1108.00), new Double(1113.25),
                new Double(23136)});
        data.add(new Object[] {time(2010, 5, 6, 14, 22, 0), new Double(1113.25), new Double(1116.75), new Double(1112.50), new Double(1114.50),
                new Double(13819)});
        data.add(new Object[] {time(2010, 5, 6, 14, 23, 0), new Double(1114.75), new Double(1115.50), new Double(1111.50), new Double(1112.50),
                new Double(14484)});
        data.add(new Object[] {time(2010, 5, 6, 14, 24, 0), new Double(1112.50), new Double(1113.50), new Double(1111.75), new Double(1112.25),
                new Double(7490)});
        data.add(new Object[] {time(2010, 5, 6, 14, 25, 0), new Double(1112.25), new Double(1112.25), new Double(1108.25), new Double(1108.50),
                new Double(14881)});
        data.add(new Object[] {time(2010, 5, 6, 14, 26, 0), new Double(1108.25), new Double(1112.00), new Double(1106.50), new Double(1107.25),
                new Double(19247)});
        data.add(new Object[] {time(2010, 5, 6, 14, 27, 0), new Double(1107.25), new Double(1111.25), new Double(1106.50), new Double(1110.00),
                new Double(11622)});
        data.add(new Object[] {time(2010, 5, 6, 14, 28, 0), new Double(1110.00), new Double(1111.00), new Double(1109.00), new Double(1110.50),
                new Double(8758)});
        data.add(new Object[] {time(2010, 5, 6, 14, 29, 0), new Double(1110.50), new Double(1118.00), new Double(1110.00), new Double(1117.50),
                new Double(16857)});
        data.add(new Object[] {time(2010, 5, 6, 14, 30, 0), new Double(1117.75), new Double(1122.00), new Double(1116.50), new Double(1121.50),
                new Double(17153)});
    }

    public static List<Object[]> get() {
        return data;
    }

    private static Date time(int year, int month, int day, int hour, int minute, int second) {
        final java.util.Calendar calendar = java.util.Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.set(year, month - 1, day, hour, minute, second);
        final Date result = calendar.getTime();
        return result;
    }
}
