package ru.vsu.kudinov;

import java.util.Map;

public class SearcherAbbreviation implements Searcher
{

    String pattern = "(\\s|[,;():.-])+";

    @Override
    public void search(String text, Map<String, Integer> map)
    {
        String[] textArray = readTextToStrArray(text, pattern);

        for (String word : textArray)
        {
            char[] charArray = word.toCharArray();

            boolean isAbbreviation = true;

            if (charArray.length < 6 && charArray.length > 1)
            {
                for (char n : charArray)
                {
                    if (!Character.isUpperCase(n))
                    {
                        isAbbreviation = false;
                        break;
                    }
                }
            }
            else
            {
                isAbbreviation = false;
            }
            if (isAbbreviation)
            {
                boolean isKeyInMap = false;
                for (Map.Entry<String, Integer> entry : map.entrySet())
                {
                    if (entry.getKey().equals(word))
                    {
                        int value = entry.getValue();
                        entry.setValue(value + 1);
                        isKeyInMap = true;
                    }
                }
                if (!isKeyInMap)
                {
                    map.put(word, 1);
                }
            }
        }
    }
}

