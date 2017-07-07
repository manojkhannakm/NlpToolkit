package com.manojkhannakm.nlptoolkit.swing;

import com.manojkhannakm.nlptoolkit.Constants;
import com.manojkhannakm.nlptoolkit.NlpToolkit;
import com.manojkhannakm.nlptoolkit.document.Paragraph;
import com.manojkhannakm.nlptoolkit.document.Sentence;
import com.manojkhannakm.nlptoolkit.document.Word;
import com.manojkhannakm.nlptoolkit.toolkit.Toolkit;
import com.manojkhannakm.nlptoolkit.toolkit.cleaner.SimpleCleaner;
import com.manojkhannakm.nlptoolkit.toolkit.tokenizer.SimpleTokenizer;

import javax.swing.*;
import java.awt.*;

/**
 * @author Manoj Khanna
 */

public class Swing {

    private static Swing swing;

    private Swing() {
        JFrame frame = new JFrame(Constants.TITLE);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(panel);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Language"), constraints);

        ButtonGroup buttonGroup1 = new ButtonGroup();

        JRadioButton englishRadioButton = new JRadioButton("English");
        englishRadioButton.setSelected(true);
        buttonGroup1.add(englishRadioButton);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(englishRadioButton, constraints);

        JRadioButton tamilRadioButton = new JRadioButton("Tamil");
        buttonGroup1.add(tamilRadioButton);
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(tamilRadioButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(new JLabel("Cleaner"), constraints);

        JCheckBox blankSpacesCheckBox = new JCheckBox("Clean blank spaces");
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(blankSpacesCheckBox, constraints);

        JCheckBox lineBreaksCheckBox = new JCheckBox("Clean line breaks");
        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(lineBreaksCheckBox, constraints);

        JCheckBox numbersCheckBox = new JCheckBox("Clean numbers");
        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(numbersCheckBox, constraints);

        JCheckBox commonSymbolsCheckBox = new JCheckBox("Clean common symbols");
        constraints.gridx = 0;
        constraints.gridy = 7;
        panel.add(commonSymbolsCheckBox, constraints);

        JCheckBox uncommonSymbolsCheckBox = new JCheckBox("Clean uncommon symbols");
        constraints.gridx = 0;
        constraints.gridy = 8;
        panel.add(uncommonSymbolsCheckBox, constraints);

        JCheckBox urlsCheckBox1 = new JCheckBox("Clean urls");
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(urlsCheckBox1, constraints);

        JCheckBox emailsCheckBox1 = new JCheckBox("Clean emails");
        constraints.gridx = 1;
        constraints.gridy = 5;
        panel.add(emailsCheckBox1, constraints);

        JCheckBox otherLanguagesCheckBox = new JCheckBox("Clean other languages");
        constraints.gridx = 1;
        constraints.gridy = 6;
        panel.add(otherLanguagesCheckBox, constraints);

        JCheckBox replaceUncommonSymbolsCheckBox = new JCheckBox("Replace uncommon symbols");
        constraints.gridx = 1;
        constraints.gridy = 7;
        panel.add(replaceUncommonSymbolsCheckBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 9;
        panel.add(new JLabel("Tokenizer"), constraints);

        JCheckBox urlsCheckBox2 = new JCheckBox("Tokenize urls");
        constraints.gridx = 0;
        constraints.gridy = 10;
        panel.add(urlsCheckBox2, constraints);

        JCheckBox emailsCheckBox2 = new JCheckBox("Tokenize emails");
        constraints.gridx = 1;
        constraints.gridy = 10;
        panel.add(emailsCheckBox2, constraints);

        constraints.gridx = 0;
        constraints.gridy = 11;
        panel.add(new JLabel("Input"), constraints);

        Font font = new Font("Arial Unicode MS", Font.PLAIN, 11);

        JTextArea inputTextArea = new JTextArea(8, 40);
        inputTextArea.setFont(font);

        constraints.gridx = 0;
        constraints.gridy = 12;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(new JScrollPane(inputTextArea), constraints);

        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;

        constraints.gridx = 0;
        constraints.gridy = 13;
        panel.add(new JLabel("Output"), constraints);

        JTextArea outputTextArea = new JTextArea(8, 40);
        outputTextArea.setFont(font);

        constraints.gridx = 0;
        constraints.gridy = 14;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(new JScrollPane(outputTextArea), constraints);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 15;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(buttonPanel, constraints);

        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;

        JButton cleanButton = new JButton("Clean");
        cleanButton.addActionListener(e -> {
            NlpToolkit.Options options = new NlpToolkit.Options();
            options.setLanguage(englishRadioButton.isSelected() ? NlpToolkit.Options.Language.ENGLISH : NlpToolkit.Options.Language.TAMIL);
            options.setInput(inputTextArea.getText());

            Toolkit.Options toolkitOptions = new Toolkit.Options();
            options.setToolkitOptions(toolkitOptions);

            SimpleCleaner.Options cleanerOptions = new SimpleCleaner.Options();
            cleanerOptions.setCleanBlankSpaces(blankSpacesCheckBox.isSelected());
            cleanerOptions.setCleanLineBreaks(lineBreaksCheckBox.isSelected());
            cleanerOptions.setCleanNumbers(numbersCheckBox.isSelected());
            cleanerOptions.setCleanCommonSymbols(commonSymbolsCheckBox.isSelected());
            cleanerOptions.setCleanUncommonSymbols(uncommonSymbolsCheckBox.isSelected());
            cleanerOptions.setCleanUrls(urlsCheckBox1.isSelected());
            cleanerOptions.setCleanEmails(emailsCheckBox1.isSelected());
            cleanerOptions.setCleanOtherLanguages(otherLanguagesCheckBox.isSelected());
            cleanerOptions.setReplaceUncommonSymbols(replaceUncommonSymbolsCheckBox.isSelected());
            toolkitOptions.setCleanerOptions(cleanerOptions);

            SimpleTokenizer.Options tokenizerOptions = new SimpleTokenizer.Options();
            tokenizerOptions.setTokenizeUrls(urlsCheckBox2.isSelected());
            tokenizerOptions.setTokenizeEmails(emailsCheckBox2.isSelected());
            toolkitOptions.setTokenizerOptions(tokenizerOptions);

            NlpToolkit nlpToolkit = new NlpToolkit(options);
            nlpToolkit.clean();

            outputTextArea.setText(nlpToolkit.getDocument().getCleanedCharsAsString());
        });
        constraints.gridx = 0;
        constraints.gridy = 0;
        buttonPanel.add(cleanButton, constraints);

        JButton tokenizeButton = new JButton("Tokenize");
        tokenizeButton.addActionListener(e -> {
            NlpToolkit.Options options = new NlpToolkit.Options();
            options.setLanguage(englishRadioButton.isSelected() ? NlpToolkit.Options.Language.ENGLISH : NlpToolkit.Options.Language.TAMIL);
            options.setInput(inputTextArea.getText());

            Toolkit.Options toolkitOptions = new Toolkit.Options();
            options.setToolkitOptions(toolkitOptions);

            SimpleCleaner.Options cleanerOptions = new SimpleCleaner.Options();
            cleanerOptions.setCleanBlankSpaces(blankSpacesCheckBox.isSelected());
            cleanerOptions.setCleanLineBreaks(lineBreaksCheckBox.isSelected());
            cleanerOptions.setCleanNumbers(numbersCheckBox.isSelected());
            cleanerOptions.setCleanCommonSymbols(commonSymbolsCheckBox.isSelected());
            cleanerOptions.setCleanUncommonSymbols(uncommonSymbolsCheckBox.isSelected());
            cleanerOptions.setCleanUrls(urlsCheckBox1.isSelected());
            cleanerOptions.setCleanEmails(emailsCheckBox1.isSelected());
            cleanerOptions.setCleanOtherLanguages(otherLanguagesCheckBox.isSelected());
            cleanerOptions.setReplaceUncommonSymbols(replaceUncommonSymbolsCheckBox.isSelected());
            toolkitOptions.setCleanerOptions(cleanerOptions);

            SimpleTokenizer.Options tokenizerOptions = new SimpleTokenizer.Options();
            tokenizerOptions.setTokenizeUrls(urlsCheckBox2.isSelected());
            tokenizerOptions.setTokenizeEmails(emailsCheckBox2.isSelected());
            toolkitOptions.setTokenizerOptions(tokenizerOptions);

            NlpToolkit nlpToolkit = new NlpToolkit(options);
            nlpToolkit.tokenize();

            StringBuilder stringBuilder = new StringBuilder();
            for (Paragraph paragraph : nlpToolkit.getDocument().getParagraphList()) {
                for (Sentence sentence : paragraph.getSentenceList()) {
                    for (Word word : sentence.getWordList()) {
                        stringBuilder.append(word.getString())
                                .append(System.lineSeparator());
                    }
                }
            }

            outputTextArea.setText(stringBuilder.toString());
        });
        constraints.gridx = 1;
        constraints.gridy = 0;
        buttonPanel.add(tokenizeButton, constraints);

        JButton tagButton = new JButton("Tag");
        tagButton.addActionListener(e -> {
            NlpToolkit.Options options = new NlpToolkit.Options();
            options.setLanguage(englishRadioButton.isSelected() ? NlpToolkit.Options.Language.ENGLISH : NlpToolkit.Options.Language.TAMIL);
            options.setInput(inputTextArea.getText());

            Toolkit.Options toolkitOptions = new Toolkit.Options();
            options.setToolkitOptions(toolkitOptions);

            SimpleCleaner.Options cleanerOptions = new SimpleCleaner.Options();
            cleanerOptions.setCleanBlankSpaces(blankSpacesCheckBox.isSelected());
            cleanerOptions.setCleanLineBreaks(lineBreaksCheckBox.isSelected());
            cleanerOptions.setCleanNumbers(numbersCheckBox.isSelected());
            cleanerOptions.setCleanCommonSymbols(commonSymbolsCheckBox.isSelected());
            cleanerOptions.setCleanUncommonSymbols(uncommonSymbolsCheckBox.isSelected());
            cleanerOptions.setCleanUrls(urlsCheckBox1.isSelected());
            cleanerOptions.setCleanEmails(emailsCheckBox1.isSelected());
            cleanerOptions.setCleanOtherLanguages(otherLanguagesCheckBox.isSelected());
            cleanerOptions.setReplaceUncommonSymbols(replaceUncommonSymbolsCheckBox.isSelected());
            toolkitOptions.setCleanerOptions(cleanerOptions);

            SimpleTokenizer.Options tokenizerOptions = new SimpleTokenizer.Options();
            tokenizerOptions.setTokenizeUrls(urlsCheckBox2.isSelected());
            tokenizerOptions.setTokenizeEmails(emailsCheckBox2.isSelected());
            toolkitOptions.setTokenizerOptions(tokenizerOptions);

            NlpToolkit nlpToolkit = new NlpToolkit(options);
            nlpToolkit.tag();

            StringBuilder stringBuilder = new StringBuilder();
            for (Paragraph paragraph : nlpToolkit.getDocument().getParagraphList()) {
                for (Sentence sentence : paragraph.getSentenceList()) {
                    for (Word word : sentence.getWordList()) {
                        stringBuilder.append(word.getString())
                                .append("_")
                                .append(word.getTag())
                                .append(" ");
                    }

                    stringBuilder.append(System.lineSeparator());
                }
            }

            outputTextArea.setText(stringBuilder.toString());
        });
        constraints.gridx = 2;
        constraints.gridy = 0;
        buttonPanel.add(tagButton, constraints);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static Swing getInstance() {
        if (swing == null) {
            swing = new Swing();
        }

        return swing;
    }

}
