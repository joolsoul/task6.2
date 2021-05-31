package ru.vsu.kudinov;

import java.util.Map;

public interface Searcher
{
    void search(String text, Map<String, Integer> map);
    String[] readTextToStrArray(String text);
}
