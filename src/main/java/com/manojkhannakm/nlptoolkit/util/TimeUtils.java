package com.manojkhannakm.nlptoolkit.util;

import java.util.concurrent.TimeUnit;

/**
 * @author Manoj Khanna
 */

public class TimeUtils {

    public static String format(long time) {
        long days = TimeUnit.MILLISECONDS.toDays(time),
                hours = TimeUnit.MILLISECONDS.toHours(time - TimeUnit.DAYS.toMillis(days)),
                minutes = TimeUnit.MILLISECONDS.toMinutes(time - TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hours)),
                seconds = TimeUnit.MILLISECONDS.toSeconds(time - TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes)),
                millis = TimeUnit.MILLISECONDS.toMillis(time - TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes) - TimeUnit.SECONDS.toMillis(seconds));
        if (days > 0) {
            return String.format("%dd %dh %dm %ds %dms", days, hours, minutes, seconds, millis);
        } else if (hours > 0) {
            return String.format("%dh %dm %ds %dms", hours, minutes, seconds, millis);
        } else if (minutes > 0) {
            return String.format("%dm %ds %dms", minutes, seconds, millis);
        } else if (seconds > 0) {
            return String.format("%ds %dms", seconds, millis);
        } else {
            return String.format("%dms", millis);
        }
    }

    public static class Timer {

        private final long timeout;

        private long startTime, endTime;

        public Timer() {
            this(Long.MAX_VALUE - System.currentTimeMillis());
        }

        public Timer(long timeout) {
            this.timeout = timeout;

            startTime = System.currentTimeMillis();
            endTime = startTime + timeout;
        }

        public void reset() {
            endTime = System.currentTimeMillis() + timeout;
        }

        public boolean isRunning() {
            return System.currentTimeMillis() < endTime;
        }

        public long getElapsedTime() {
            return System.currentTimeMillis() - startTime;
        }

        public long getRemainingTime() {
            return isRunning() ? endTime - System.currentTimeMillis() : 0;
        }

    }

}
