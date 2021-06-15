package ru.vsu.kudinov;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public interface Searcher
{
    void search(String text, Map<String, Integer> map);

    default String[] readTextToStrArray(String text, String pattern)
    {
        Scanner scanner = new Scanner(text);
        scanner.useLocale(Locale.ROOT);
        scanner.useDelimiter(pattern);
        java.util.List<String> list = new ArrayList<>();
        while (scanner.hasNext())
        {
            list.add(scanner.next());
        }

        return list.toArray(new String[0]);
    }

}
