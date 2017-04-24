package com.manojkhannakm.nlptoolkit;

import com.manojkhannakm.nlptoolkit.util.IoUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manoj Khanna
 */

public abstract class Options {

    private final Map map = new Map();

    protected Options() {
    }

    protected Options(Options options) {
    }

    protected Options(String fileName) throws IOException {
        readFromFile(fileName);
    }

    @Override
    public String toString() {
        writeToMap(map);

        StringBuilder stringBuilder = new StringBuilder();
        for (java.util.Map.Entry<String, String> entry : map.getMap().entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }

            stringBuilder.append(entry.getKey())
                    .append(" = ")
                    .append(entry.getValue());
        }

        return stringBuilder.toString();
    }

    protected abstract void readFromMap(Map map);

    protected abstract void writeToMap(Map map);

    public final void readFromFile(String fileName) throws IOException {
        for (String line : IoUtils.readFile(fileName).split("(\n|\r)+")) {
            Matcher matcher = Pattern.compile("(.+) = (.+)").matcher(line);
            if (matcher.find()) {
                map.getMap().put(matcher.group(1), matcher.group(2));
            }
        }

        readFromMap(map);
    }

    public final void writeToFile(String fileName) throws IOException {
        writeToMap(map);

        StringBuilder stringBuilder = new StringBuilder();
        for (java.util.Map.Entry<String, String> entry : map.getMap().entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append(" = ")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }

        IoUtils.writeFile(fileName, stringBuilder.toString());
    }

    protected final class Map {

        private final LinkedHashMap<String, String> map = new LinkedHashMap<>();

        public boolean getBoolean(String key, boolean defaultValue) {
            if (map.containsKey(key)) {
                return Boolean.parseBoolean(map.get(key));
            }

            return defaultValue;
        }

        public void setBoolean(String key, boolean value) {
            map.put(key, Boolean.toString(value));
        }

        public byte getByte(String key, byte defaultValue) {
            if (map.containsKey(key)) {
                return Byte.parseByte(map.get(key));
            }

            return defaultValue;
        }

        public void setByte(String key, byte value) {
            map.put(key, Byte.toString(value));
        }

        public char getCharacter(String key, char defaultValue) {
            if (map.containsKey(key)) {
                return map.get(key).charAt(0);
            }

            return defaultValue;
        }

        public void setCharacter(String key, char value) {
            map.put(key, Character.toString(value));
        }

        public short getShort(String key, short defaultValue) {
            if (map.containsKey(key)) {
                return Short.parseShort(map.get(key));
            }

            return defaultValue;
        }

        public void setShort(String key, short value) {
            map.put(key, Short.toString(value));
        }

        public int getInteger(String key, int defaultValue) {
            if (map.containsKey(key)) {
                return Integer.parseInt(map.get(key));
            }

            return defaultValue;
        }

        public void setInteger(String key, int value) {
            map.put(key, Integer.toString(value));
        }

        public long getLong(String key, long defaultValue) {
            if (map.containsKey(key)) {
                return Long.parseLong(map.get(key));
            }

            return defaultValue;
        }

        public void setLong(String key, long value) {
            map.put(key, Long.toString(value));
        }

        public float getFloat(String key, float defaultValue) {
            if (map.containsKey(key)) {
                return Float.parseFloat(map.get(key));
            }

            return defaultValue;
        }

        public void setFloat(String key, float value) {
            map.put(key, Float.toString(value));
        }

        public double getDouble(String key, double defaultValue) {
            if (map.containsKey(key)) {
                return Double.parseDouble(map.get(key));
            }

            return defaultValue;
        }

        public void setDouble(String key, double value) {
            map.put(key, Double.toString(value));
        }

        public String getString(String key, String defaultValue) {
            if (map.containsKey(key)) {
                return map.get(key);
            }

            return defaultValue;
        }

        public void setString(String key, String value) {
            map.put(key, value != null ? value : "");
        }

        public <E extends Enum<E>> E getEnum(String key, E defaultValue) {
            if (map.containsKey(key)) {
                try {
                    //noinspection unchecked
                    return Enum.valueOf((Class<E>) defaultValue.getClass(), map.get(key).toUpperCase());
                } catch (IllegalArgumentException ignored) {
                }
            }

            return defaultValue;
        }

        public void setEnum(String key, Enum<?> value) {
            map.put(key, value != null ? value.toString().toLowerCase() : "");
        }

        public void getOptions(Options options) {
            if (options != null) {
                options.readFromMap(this);
            }
        }

        public void setOptions(Options options) {
            if (options != null) {
                options.writeToMap(this);
            }
        }

        private LinkedHashMap<String, String> getMap() {
            return map;
        }

    }

}
