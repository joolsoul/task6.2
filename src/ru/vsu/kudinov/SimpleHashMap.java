package ru.vsu.kudinov;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;

import ru.vsu.kudinov.util.dummy.DefaultNotSupportedMap;
import ru.vsu.kudinov.util.dummy.DefaultNotSupportedSet;

public class SimpleHashMap<K, V> implements DefaultNotSupportedMap<K, V>
{
    private class EntryListItem implements Map.Entry<K, V>
    {
        public K key;
        public V value;
        public EntryListItem next;

        public EntryListItem(K key, V value, EntryListItem next)
        {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey()
        {
            return key;
        }

        @Override
        public V getValue()
        {
            return value;
        }

        @Override
        public V setValue(V value)
        {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    private EntryListItem[] table;
    private int size = 0;

    public SimpleHashMap(int capacity)
    {
        table = (EntryListItem[]) Array.newInstance(EntryListItem.class, capacity);
    }

    private int getIndex(Object key)
    {
        int index = key.hashCode() % table.length;
        if (index < 0)
        {
            index += table.length;
        }
        return index;
    }

    private EntryListItem getEntry(Object key, int index)
    {
        if (index < 0)
        {
            index = getIndex(key);
        }
        for (EntryListItem curr = table[index]; curr != null; curr = curr.next)
        {
            if (key.equals(curr.key))
            {
                return curr;
            }
        }
        return null;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public boolean isEmpty()
    {
        return size() <= 0;
    }

    @Override
    public boolean containsKey(Object key)
    {
        return getEntry(key, -1) != null;
    }

    @Override
    public boolean containsValue(Object value)
    {
        return entrySet().stream().anyMatch(kv -> value.equals(kv.getValue()));
    }

    @Override
    public V get(Object key)
    {
        EntryListItem item = getEntry(key, -1);
        return (item == null) ? null : item.value;
    }

    @Override
    public V put(K key, V value)
    {
        int index = getIndex(key);
        EntryListItem item = getEntry(key, index);
        if (item != null)
        {
            V oldValue = item.value;
            item.value = value;
            return oldValue;
        }
        table[index] = new EntryListItem(key, value, table[index]);
        size++;
        return null;
    }

    @Override
    public V remove(Object key)
    {
        int index = getIndex(key);
        EntryListItem parent = null;
        for (EntryListItem curr = table[index]; curr != null; curr = curr.next)
        {
            if (key.equals(curr.key))
            {
                if (parent == null)
                {
                    table[index] = curr.next;
                } else
                {
                    parent.next = curr.next;
                }
                size--;
                return curr.value;
            }
            parent = curr;
        }
        return null;
    }

    @Override
    public void clear()
    {
        Arrays.fill(table, null);
        size = 0;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        return new DefaultNotSupportedSet<Map.Entry<K, V>>()
        {
            @Override
            public int size()
            {
                return SimpleHashMap.this.size();
            }

            @Override
            public Iterator<Map.Entry<K, V>> iterator()
            {
                return new Iterator<Map.Entry<K, V>>()
                {
                    int tableIndex = -1;
                    EntryListItem curr = null;

                    {
                        findNext();
                    }

                    private void findNext()
                    {
                        if (tableIndex >= table.length)
                        {
                            return;
                        }
                        if (curr != null)
                        {
                            curr = curr.next;
                        }
                        if (curr == null)
                        {
                            for (tableIndex = tableIndex + 1; tableIndex < table.length; tableIndex++)
                            {
                                curr = table[tableIndex];
                                if (curr != null)
                                {
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public boolean hasNext()
                    {
                        return curr != null;
                    }

                    @Override
                    public Map.Entry<K, V> next()
                    {
                        Map.Entry<K, V> temp = curr;
                        findNext();
                        return temp;
                    }
                };
            }
        };
    }
}
