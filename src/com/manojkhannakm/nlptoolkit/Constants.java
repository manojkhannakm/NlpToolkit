package com.manojkhannakm.nlptoolkit;

/**
 * @author Manoj Khanna
 */

public class Constants {

    public static final String TITLE = "Nlp Toolkit";
    public static final String VERSION = "1.0"; //TODO: Update
    public static final String BUILD_VERSION = "1000";  //TODO: Update
    public static final String BUILD_DATE = "1 Jan 2016";   //TODO: Update
    public static final String URL = "https://github.com/manojkhannakm/NlpToolkit"; //TODO: Update
    public static final Language[] LANGUAGES = new Language[]{
            new Language("English", "en"),
            new Language("Tamil", "ta")
    };
    public static final Contributor[] CONTRIBUTORS = new Contributor[]{
            new Contributor("Manoj Khanna", "manojkhannakm@gmail.com", "https://github.com/manojkhannakm"),
            new Contributor("Vignesh Vijaykumar", "rooney_vignesh3@yahoo.com"),
            new Contributor("Rishe Viswanath", "srisheviswanath@gmail.com")
    };

    private Constants() {
    }

    public static class Language {

        private final String name, code;

        public Language(String name, String code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

    }

    public static class Contributor {

        private final String name, email, url;

        private Contributor(String name) {
            this(name, null, null);
        }

        private Contributor(String name, String email) {
            this(name, email, null);
        }

        private Contributor(String name, String email, String url) {
            this.name = name;
            this.email = email;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getUrl() {
            return url;
        }

    }

}
