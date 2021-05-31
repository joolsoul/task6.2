package ru.vsu.kudinov.gui;

import ru.vsu.kudinov.Searcher;
import ru.vsu.kudinov.SimpleHashMap;
import ru.vsu.kudinov.util.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrameMain extends JFrame
{
    public FrameMain()
    {
        setTitle("Search");
        setSize(802, 598);
        add(new MainPanel());
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static class MainPanel extends JPanel
    {
        private static Searcher instance;

        private JTextArea initialTextArea = new JTextArea();
        private JScrollPane initialTextAreaScroll = new JScrollPane(initialTextArea);
        private JLabel initialLabel = new JLabel("Initial text:");
        private JTextArea extractedElementsTextArea = new JTextArea();
        private JScrollPane extractedElementsTextAreaScroll = new JScrollPane(extractedElementsTextArea);
        private JLabel extractedElementsLabel = new JLabel("Extracted elements:");
        private JLabel elementsChooseLabel = new JLabel("Choose what you want to find:");
        private JButton searchButton = new JButton("Search");
        private JButton clearButton = new JButton("Clear");
        private JButton readFromFileButton = new JButton("Read from file");
        private JButton writeToFileButton = new JButton("Write to file");
        private JCheckBox searchAbbreviationsCheckBox = new JCheckBox("Abbreviations");
        private JCheckBox searchProperNounsCheckBox = new JCheckBox("Proper nouns");
        private JLabel hashMapChooseLabel = new JLabel("Choose a HashMap realization:");
        private JCheckBox javaHashMapCheckBox = new JCheckBox("Java HashMap");
        private JCheckBox myHashMapCheckBox = new JCheckBox("My HashMap");

        private final int BUTTONS_Y_ALIGNMENT = 105;
        private final int BUTTONS_X_ALIGNMENT = 335;
        private final int BUTTONS_WIDTH = 125;
        private final int BUTTONS_HEIGHT = 30;
        private final int TEXT_AREAS_Y_ALIGNMENT = 35;
        private final int TEXT_AREAS_X_ALIGNMENT = 10;
        private final int TEXT_AREAS_WIDTH = 275;
        private final int TEXT_AREAS_HEIGHT = 520;

        private final Font font = new Font("SanSerif", Font.PLAIN, 14);
        private final Color backgroundColor = new Color(170, 210, 250);
        private final Color backgroundButtonColor = new Color(0xE1F1F6);

        private String text;
        private String outputMessage;
        private Map<String, Integer> map;



        public MainPanel()
        {
            setLayout(null);
            setFont(font);
            setBackground(backgroundColor);

            addTextAreas();
            addButtons();
            addActionListeners();
            myHashMapCheckBox.doClick();

        }

        private void addTextAreas()
        {
            initialLabel.setBounds(10, 12, 80, 10);
            add(initialLabel);
            initialTextArea.setBackground(backgroundButtonColor);
            initialTextArea.setLineWrap(true);
            initialTextArea.setWrapStyleWord(true);
            initialTextAreaScroll.setBounds(TEXT_AREAS_X_ALIGNMENT, TEXT_AREAS_Y_ALIGNMENT, TEXT_AREAS_WIDTH, TEXT_AREAS_HEIGHT);
            add(initialTextAreaScroll);

            extractedElementsLabel.setBounds(510, 12, 150, 10);
            add(extractedElementsLabel);
            extractedElementsTextArea.setBackground(backgroundButtonColor);
            extractedElementsTextArea.setEditable(false);
            extractedElementsTextArea.setLineWrap(true);
            extractedElementsTextArea.setWrapStyleWord(true);
            extractedElementsTextAreaScroll.setBounds(TEXT_AREAS_X_ALIGNMENT + 500, TEXT_AREAS_Y_ALIGNMENT, TEXT_AREAS_WIDTH, TEXT_AREAS_HEIGHT);
            add(extractedElementsTextAreaScroll);
        }


        private void addButtons()
        {
            elementsChooseLabel.setBounds(BUTTONS_X_ALIGNMENT - 20, BUTTONS_Y_ALIGNMENT - 30, BUTTONS_WIDTH + 45, BUTTONS_HEIGHT / 2);
            add(elementsChooseLabel);

            searchAbbreviationsCheckBox.setBounds(BUTTONS_X_ALIGNMENT + 10, BUTTONS_Y_ALIGNMENT + 10, BUTTONS_WIDTH - 20, BUTTONS_HEIGHT / 2);
            searchAbbreviationsCheckBox.setBackground(backgroundColor);
            add(searchAbbreviationsCheckBox);

            searchProperNounsCheckBox.setBounds(BUTTONS_X_ALIGNMENT + 10, BUTTONS_Y_ALIGNMENT + 35, BUTTONS_WIDTH - 20, BUTTONS_HEIGHT / 2);
            searchProperNounsCheckBox.setBackground(backgroundColor);
            add(searchProperNounsCheckBox);

            hashMapChooseLabel.setBounds(BUTTONS_X_ALIGNMENT - 20, BUTTONS_Y_ALIGNMENT + 80, BUTTONS_WIDTH + 70, BUTTONS_HEIGHT / 2);
            add(hashMapChooseLabel);

            myHashMapCheckBox.setBounds(BUTTONS_X_ALIGNMENT + 10, BUTTONS_Y_ALIGNMENT + 120, BUTTONS_WIDTH - 20, BUTTONS_HEIGHT / 2);
            myHashMapCheckBox.setBackground(backgroundColor);
            add(myHashMapCheckBox);

            javaHashMapCheckBox.setBounds(BUTTONS_X_ALIGNMENT + 10, BUTTONS_Y_ALIGNMENT + 145, BUTTONS_WIDTH - 10, BUTTONS_HEIGHT / 2);
            javaHashMapCheckBox.setBackground(backgroundColor);
            add(javaHashMapCheckBox);

            searchButton.setBounds(BUTTONS_X_ALIGNMENT, BUTTONS_Y_ALIGNMENT + 230, BUTTONS_WIDTH, BUTTONS_HEIGHT);
            searchButton.setBackground(backgroundButtonColor);
            add(searchButton);

            clearButton.setBounds(BUTTONS_X_ALIGNMENT, BUTTONS_Y_ALIGNMENT + 280, BUTTONS_WIDTH, BUTTONS_HEIGHT);
            clearButton.setBackground(backgroundButtonColor);
            add(clearButton);

            readFromFileButton.setBounds(BUTTONS_X_ALIGNMENT, BUTTONS_Y_ALIGNMENT + 330, BUTTONS_WIDTH, BUTTONS_HEIGHT);
            readFromFileButton.setBackground(backgroundButtonColor);
            add(readFromFileButton);

            writeToFileButton.setBounds(BUTTONS_X_ALIGNMENT, BUTTONS_Y_ALIGNMENT + 380, BUTTONS_WIDTH, BUTTONS_HEIGHT);
            writeToFileButton.setBackground(backgroundButtonColor);
            add(writeToFileButton);
        }

        private void addActionListeners()
        {
            readFromFileButton.addActionListener(e ->
            {
                try
                {
                    String[] inputText = FileUtils.readFile();
                    for(String word : inputText)
                    {
                        initialTextArea.append(word);
                    }
                }
                catch (FileNotFoundException fileNotFoundException)
                {
                    fileNotFoundException.printStackTrace();
                }

            });

            writeToFileButton.addActionListener(e ->
            {
                try
                {
                    FileUtils.writeFile(extractedElementsTextArea.getText());
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            });

            clearButton.addActionListener(e ->
            {
              initialTextArea.setText(null);
              extractedElementsTextArea.setText(null);
            });

            searchProperNounsCheckBox.addActionListener(e ->
                    {
                            instance = new SimpleHashMap.SearcherProperNoun();
                            outputMessage = "Found proper nouns in this text:\n\n";
                    });

            searchAbbreviationsCheckBox.addActionListener(e ->
                    {
                            instance = new SimpleHashMap.SearcherAbbreviation();
                            outputMessage = "Found abbreviations in this text:\n\n";
                    });

            myHashMapCheckBox.addActionListener(e ->
            {
                map = new SimpleHashMap<>(20);
                javaHashMapCheckBox.setSelected(false);
            });

            javaHashMapCheckBox.addActionListener(e ->
            {
                map = new HashMap<>();
                myHashMapCheckBox.setSelected(false);
            });

            searchButton.addActionListener(e ->
            {
                map.clear();
                text = initialTextArea.getText();

                if(searchProperNounsCheckBox.isSelected() && searchAbbreviationsCheckBox.isSelected())
                {
                    searchAbbreviationsCheckBox.doClick();
                    instance.search(text, map);

                    extractedElementsTextArea.setText(null);
                    extractedElementsTextArea.append(outputMessage);
                    for (Map.Entry<String, Integer> entry : map.entrySet())
                    {
                        extractedElementsTextArea.append(entry.getKey() + "->" + entry.getValue() + "\n");
                    }

                    map.clear();

                    searchProperNounsCheckBox.doClick();
                    instance.search(text, map);

                    extractedElementsTextArea.append("\n\n" + outputMessage);
                    for (Map.Entry<String, Integer> entry : map.entrySet())
                    {
                        extractedElementsTextArea.append(entry.getKey() + "->" + entry.getValue() + "\n");
                    }
                }
                else
                {
                    instance.search(text, map);

                    extractedElementsTextArea.setText(null);
                    extractedElementsTextArea.append(outputMessage);

                    for (Map.Entry<String, Integer> entry : map.entrySet())
                    {
                        extractedElementsTextArea.append(entry.getKey() + "->" + entry.getValue() + "\n");
                    }
                }
                searchAbbreviationsCheckBox.setSelected(false);
                searchProperNounsCheckBox.setSelected(false);
            });
        }
    }
}
