package com.manojkhannakm.nlptoolkit.commandline;

import com.manojkhannakm.nlptoolkit.Constants;
import com.manojkhannakm.nlptoolkit.document.Sentence;
import com.manojkhannakm.nlptoolkit.NlpToolkit;
import com.manojkhannakm.nlptoolkit.document.Paragraph;
import com.manojkhannakm.nlptoolkit.document.Word;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Manoj Khanna
 */

public class CommandLine {

    private static CommandLine commandLine;

    private CommandLine() {
    }

    public static CommandLine getInstance() {
        if (commandLine == null) {
            commandLine = new CommandLine();
        }

        return commandLine;
    }

    public void parse(String[] args) {
        HashMap<String, Argument> argumentMap = new HashMap<>();
        for (Argument argument : new Argument[]{
                new Argument(new String[]{"-p", "--options"}, new String[1]),
                new Argument(new String[]{"-l", "--language"}, new String[1]),
                new Argument(new String[]{"-i", "--input"}, new String[1]),
                new Argument(new String[]{"-o", "--output"}, new String[1]),
                new Argument(new String[]{"-d", "--debug"}, new String[0]),
                new Argument(new String[]{"-t", "--tool"}, new String[1]),
                new Argument(new String[]{"-h", "--help", "?"}, new String[0]),
                new Argument(new String[]{"-a", "--about"}, new String[0])
        }) {
            for (String arg : argument.getArgs()) {
                argumentMap.put(arg, argument);
            }
        }

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (!argumentMap.containsKey(arg)) {
                throw new UnknownArgumentException(arg);
            }

            Argument argument = argumentMap.get(arg);
            argument.setParsed(true);

            String[] values = argument.getValues();
            for (int j = 0; j < values.length; j++) {
                if (argumentMap.containsKey(args[i + 1]) || i + 1 == args.length) {
                    if (values[j] == null) {
                        throw new MissingValueException(arg);
                    }

                    break;
                }

                values[j] = args[++i];
            }
        }

        if (argumentMap.get("-h").isParsed()) {
            System.out.println("Usage: java -jar NlpToolkit.jar <arguments>");
            System.out.println("-p, --options <values|file_name.*>              sets the option values or file. e.g. -p options.ini");
            System.out.println("-l, --language <language_name>                  sets the language. e.g. -l english");
            System.out.println("-i, --input <plain_text|file_name.*>            sets the input text or file. e.g. -i in.txt");
            System.out.println("-o, --output <xml|json|file_name.<*|xml|json>>  sets the output type or file. e.g. -o out.txt");
            System.out.println("-d, --debug                                     sets debug as false");
            System.out.println("-t, --tool <tool_name>                          sets the tool. eg. -t tokenizer");
            System.out.println("-h, --help, ?                                   prints help");
            System.out.println("-a, --about                                     prints about");
        } else if (argumentMap.get("-a").isParsed()) {
            System.out.println(Constants.TITLE);

            System.out.println("");

            System.out.println("Version: " + Constants.VERSION);
            System.out.println("Build version: " + Constants.BUILD_VERSION);
            System.out.println("Build date: " + Constants.BUILD_DATE);
            System.out.println("Url: " + Constants.URL);

            System.out.println("");

            System.out.println("Languages:");
            for (Constants.Language language : Constants.LANGUAGES) {
                System.out.println(language.getName() + " - " + language.getCode());
            }

            System.out.println("");

            System.out.println("Contributors:");
            for (Constants.Contributor contributor : Constants.CONTRIBUTORS) {
                System.out.print(contributor.getName());

                String email = contributor.getEmail();
                if (email != null) {
                    System.out.print(" - " + email);
                }

                String url = contributor.getUrl();
                if (url != null) {
                    System.out.print(" - " + url);
                }

                System.out.println("");
            }
        } else {
            for (Argument argument : new Argument[]{
                    argumentMap.get("-i"),
                    argumentMap.get("-t")
            }) {
                if (!argument.isParsed()) {
                    throw new MissingArgumentException(argument.getArgs()[0]);
                }
            }

            NlpToolkit.Options options;

            Argument argument = argumentMap.get("-p");
            if (argument.isParsed()) {
                try {
                    options = new NlpToolkit.Options(argument.getValues()[0]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                options = new NlpToolkit.Options();
            }

            argument = argumentMap.get("-l");
            if (argument.isParsed()) {
                NlpToolkit.Options.Language language = null;
                try {
                    language = NlpToolkit.Options.Language.valueOf(argument.getValues()[0].toUpperCase());
                } catch (IllegalArgumentException ignored) {
                }

                options.setLanguage(language);
            }

            options.setInput(argumentMap.get("-i").getValues()[0]);

            argument = argumentMap.get("-o");
            if (argument.isParsed()) {
                options.setOutput(argument.getValues()[0]);
            }

            if (argumentMap.get("-d").isParsed()) {
                options.setDebug(false);
            }

            NlpToolkit nlpToolkit = new NlpToolkit(options);
            switch (argumentMap.get("-t").getValues()[0]) {
                case "cleaner":
                    nlpToolkit.clean();

                    System.out.println(nlpToolkit.getDocument().getCleanedCharsAsString());
                    break;

                case "tokenizer":
                    nlpToolkit.tokenize();

                    for (Paragraph paragraph : nlpToolkit.getDocument().getParagraphList()) {
                        for (Sentence sentence : paragraph.getSentenceList()) {
                            for (Word word : sentence.getWordList()) {
                                System.out.println(word.getString());
                            }
                        }
                    }
                    break;

                case "tagger":
                    nlpToolkit.tag();

                    for (Paragraph paragraph : nlpToolkit.getDocument().getParagraphList()) {
                        for (Sentence sentence : paragraph.getSentenceList()) {
                            for (Word word : sentence.getWordList()) {
                                System.out.print(word.getString() + "_" + word.getTag() + " ");
                            }

                            System.out.println("");
                        }
                    }
                    break;
            }
        }
    }

    private class Argument {

        private final String[] args, values;

        private boolean parsed;

        public Argument(String[] args, String[] values) {
            this.args = args;
            this.values = values;
        }

        public String[] getArgs() {
            return args;
        }

        public String[] getValues() {
            return values;
        }

        public boolean isParsed() {
            return parsed;
        }

        public void setParsed(boolean parsed) {
            this.parsed = parsed;
        }

    }

}
