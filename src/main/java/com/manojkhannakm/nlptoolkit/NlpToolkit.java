package com.manojkhannakm.nlptoolkit;

import com.manojkhannakm.nlptoolkit.document.Document;
import com.manojkhannakm.nlptoolkit.language.english.EnglishToolkit;
import com.manojkhannakm.nlptoolkit.toolkit.Toolkit;
import com.manojkhannakm.nlptoolkit.util.IoUtils;
import com.manojkhannakm.nlptoolkit.util.TimeUtils;
import com.manojkhannakm.nlptoolkit.language.tamil.TamilToolkit;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.*;

/**
 * @author Manoj Khanna
 */

public class NlpToolkit {

    public static final Logger LOGGER = Logger.getLogger(NlpToolkit.class.getName());

    static {
        LOGGER.setUseParentHandlers(false);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(new Formatter() {

            private SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy hh:mm:ss a");

            @Override
            public String format(LogRecord record) {
                return dateFormat.format(record.getMillis()) + " [" + record.getLevel() + "] " + record.getMessage() + "\n";
            }

        });
        LOGGER.addHandler(consoleHandler);
    }

    private final HashMap<Options.Language, Toolkit> toolkitMap = new HashMap<>();

    private Options options;
    private Toolkit toolkit;
    private Document document;

    public NlpToolkit() {
        this(null);
    }

    public NlpToolkit(Options options) {
        LOGGER.setLevel(options == null || options.isDebug() ? Level.ALL : Level.OFF);

        LOGGER.fine(Constants.TITLE);
        LOGGER.fine("");

        toolkitMap.put(Options.Language.ENGLISH, new EnglishToolkit(this));
        toolkitMap.put(Options.Language.TAMIL, new TamilToolkit(this));

        setOptions(options);
    }

    public void clean() {
        if (!document.isCleaned()) {
            toolkit.getCleaner().clean();
        }
    }

    public void tokenize() {
        clean();

        if (!document.isTokenized()) {
            toolkit.getTokenizer().tokenize();
        }
    }

    public void tag() {
        tokenize();

        if (!document.isTagged()) {
            toolkit.getTagger().tag();
        }
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        if (options == null) {
            options = new Options();
        }

        this.options = options;

        LOGGER.setLevel(options.isDebug() ? Level.ALL : Level.OFF);

        LOGGER.config("Options: ");
        for (String option : options.toString().split(", ")) {
            LOGGER.config(option);
        }
        LOGGER.fine("");

        toolkit = toolkitMap.get(options.getLanguage());

        String input = options.getInput();
        if (input.endsWith(".txt")) {
            String fileName = new File(input).getName();

            LOGGER.info("Reading " + fileName + " file...");

            TimeUtils.Timer timer = new TimeUtils.Timer();

            try {
                input = IoUtils.readFile(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            LOGGER.fine("Read " + fileName + " file in " + TimeUtils.format(timer.getElapsedTime()));
            LOGGER.fine("");
        }

        document = new Document(input);
    }

    public void setToolkit(Toolkit toolkit) {
        if (toolkit == null) {
            toolkit = toolkitMap.get(Options.DEFAULT_LANGUAGE);
        }

        this.toolkit = toolkit;
    }

    public Document getDocument() {
        return document;
    }

    public final static class Options extends com.manojkhannakm.nlptoolkit.Options {

        public static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
        public static final String DEFAULT_INPUT = "";
        public static final String DEFAULT_OUTPUT = null;
        public static final boolean DEFAULT_DEBUG = true;

        private static final String LANGUAGE_OPTION = "language";
        private static final String INPUT_OPTION = "input";
        private static final String OUTPUT_OPTION = "output";
        private static final String DEBUG_OPTION = "debug";

        private Language language;
        private String input, output;
        private boolean debug;
        private Toolkit.Options toolkitOptions;

        public Options() {
            language = DEFAULT_LANGUAGE;
            input = DEFAULT_INPUT;
            output = DEFAULT_OUTPUT;
            debug = DEFAULT_DEBUG;
            toolkitOptions = new Toolkit.Options();
        }

        public Options(Options options) {
            super(options);

            language = options.language;
            input = options.input;
            output = options.output;
            debug = options.debug;
            toolkitOptions = new Toolkit.Options(options.toolkitOptions);
        }

        public Options(String fileName) throws IOException {
            super(fileName);
        }

        @Override
        protected void readFromMap(Map map) {
            language = map.getEnum(LANGUAGE_OPTION, DEFAULT_LANGUAGE);
            input = map.getString(INPUT_OPTION, DEFAULT_INPUT);
            output = map.getString(OUTPUT_OPTION, DEFAULT_OUTPUT);
            debug = map.getBoolean(DEBUG_OPTION, DEFAULT_DEBUG);

            toolkitOptions = new Toolkit.Options();
            map.getOptions(toolkitOptions);
        }

        @Override
        protected void writeToMap(Map map) {
            map.setEnum(LANGUAGE_OPTION, language);
            map.setString(INPUT_OPTION, input);
            map.setString(OUTPUT_OPTION, output);
            map.setBoolean(DEBUG_OPTION, debug);
            map.setOptions(toolkitOptions);
        }

        public Language getLanguage() {
            return language;
        }

        public void setLanguage(Language language) {
            if (language == null) {
                language = DEFAULT_LANGUAGE;
            }

            this.language = language;
        }

        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            if (input == null) {
                input = DEFAULT_INPUT;
            }

            this.input = input;
        }

        public String getOutput() {
            return output;
        }

        public void setOutput(String output) {
            if (output == null) {
                output = DEFAULT_OUTPUT;
            }

            this.output = output;
        }

        public boolean isDebug() {
            return debug;
        }

        public void setDebug(boolean debug) {
            this.debug = debug;
        }

        public Toolkit.Options getToolkitOptions() {
            return toolkitOptions;
        }

        public void setToolkitOptions(Toolkit.Options toolkitOptions) {
            if (toolkitOptions == null) {
                toolkitOptions = new Toolkit.Options();
            }

            this.toolkitOptions = toolkitOptions;
        }

        public enum Language {

            ENGLISH, TAMIL

        }

    }

}
