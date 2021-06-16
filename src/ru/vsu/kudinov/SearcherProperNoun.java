package ru.vsu.kudinov;

import java.util.Map;

public class SearcherProperNoun implements Searcher
{

    String pattern = "(\\s|[,;():-])+";

    @Override
    public void search(String text, Map<String, Integer> map)
    {
        String[] textArray = readTextToStrArray(text, pattern);
        for (int i = 1; i < textArray.length; i++)
        {
            char[] charArray = textArray[i].toCharArray();

            if (Character.isUpperCase(charArray[0]) && !textArray[i - 1].contains(".") && !Character.isUpperCase(charArray[1]))
            {
                boolean isKeyInMap = false;
                for (Map.Entry<String, Integer> entry : map.entrySet())
                {
                    if (entry.getKey().equals(textArray[i]))
                    {
                        int value = entry.getValue();
                        entry.setValue(value + 1);
                        isKeyInMap = true;
                    }
                }
                if (!isKeyInMap)
                {
                    map.put(textArray[i].replace('.', ' '), 1);
                }
            }
        }
    }
}
