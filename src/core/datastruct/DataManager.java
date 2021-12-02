package core.datastruct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

// The object T will have to implement its own T.equals(Object obj) and T.hashCode() methods
public class DataManager<T> {

    private final ArrayList<T> list;   // A resizable array
    // A hash where keys are array elements and values are indexes in the list
    private final HashMap<T, Integer> hash;

    // Constructor (creates arrayList[] and hash)
    public DataManager()
    {
        list = new ArrayList<>();
        hash = new HashMap<>();
    }

    // A Theta(1) function to add an element to DataManager
    public void add(T x)
    {
        // If element is already present, then nothing to do
        if (hash.containsKey(x))
            return;

        // Else put element at the end of arrayList
        int s = list.size();
        list.add(x);

        // And put in hash also
        hash.put(x, s);
    }

    // A Theta(1) function to remove an element from DataManager
    public void remove(T x)
    {
        // Check if element is present
        Integer index = hash.get(x);
        if (index == null)
            return;


        // If present, then remove element from hash
        hash.remove(x);

        // Swap element with last element so that remove from
        // the arrayList can be done in O(1) time
        int size = list.size();
        T last = list.get(size-1);
        Collections.swap(list, index,  size-1);

        // Remove last element (This is O(1))
        list.remove(size-1);

        // Update hash table for new index of last element if its not the same element
        if(!last.equals(x))
            hash.put(last, index);
    }

    // Returns a random element
    public T getRandom()
    {
        // Find a random index from 0 to size - 1
        Random rand = new Random();  // Choose a different seed
        int index = rand.nextInt(list.size());

        // Return element at randomly picked index
        return list.get(index);
    }

    // Returns index of element if element is present, otherwise null
    public Integer search(T x)
    {
        return hash.get(x);
    }

    public ArrayList<T> getList() {
        return list;
    }
}
