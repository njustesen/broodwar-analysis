import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Collections;

/**
 * The Apriori class is used to run the apriori algorithm on the given data set.
 */
public class Apriori
{
  /**
   * Here for testing.
   */
  public static void main(String[] args)
  {
    final int minSupport = 2;
    final Integer[][] data = new Integer[][]
      {
        { 1, 2, 3, 4, 5 },
        { 1, 3, 5 },
        { 2, 3, 5 },
        { 1, 5 },
        { 1, 3, 4 },
        { 2, 3, 5 },
        { 2, 3, 5 },
        { 3, 4, 5 },
        { 4, 5 },
        { 2 },
        { 2, 3 },
        { 2, 3, 4 },
        { 3, 4, 5 }
      };

    List<List<List<Integer>>> output = Apriori.<Integer>run(data, minSupport);
    for (int i = 0; i < output.size(); i++)
      for (int j = 0; j < output.get(i).size(); j++)
        for (int k = 0; k < output.get(i).get(j).size(); k++)
          System.out.printf("%s" + (k < output.get(i).get(j).size() - 1 ? ", " : "\n"),
                            output.get(i).get(j).get(k).toString());
  }

  /**
   * The ItemSet class is used to represent a set of item (candidate or
   * frequent). It is also used as the key of the HashMap.
   */
  private static class ItemSet <T extends Comparable<T>>
  {
    private static final int[] PRIMES = { 2, 3, 5, 7, 11, 13, 17, 23, 27, 31, 37 };
    public List<T> set;
    private int support = -1;

    /**
     * Creates a new instance of the ItemSet class.
     * @param set An int array representing an awnser set.
     */
    public ItemSet(List<T> set)
    {
      this.set = new ArrayList<T>(set);
      Collections.sort(this.set);
    }

    /**
     * Return a string representation of the ItemSet.
     * @return The string representation.
     */
    @Override
      public String toString()
    {
      String string = "";

      for (int i = 0; i < set.size(); i++)
        string += (set.get(i).toString() + (i < set.size() - 1 ? ", " : ""));

      return string;
    }

    /**
     * Return the support of the ItemSet in the given data set.
     * @param data The set of data in which the support while be compute.
     * @return The support of the ItemSet in the given data set.
     */
    public int support(T[][] data)
    {
      if (support != -1)
        return support;

      for (T[] itemSet : data)
        for (int i = 0, j = 0; j < set.size() && i < itemSet.length; i++)
          if (itemSet[i].compareTo(set.get(j)) == 0)
            if (++j == set.size())
            {
              support++;
              break;
            }

      return support;
    }

    /**
     * Join the two given ItemSet
     * @param first The first ItemSet to join.
     * @param second The second ItemSet to join.
     * @return A new ItemSet that is the join of the two given or null.
     */
    public static <T extends Comparable<T>> ItemSet<T> join(ItemSet<T> first, ItemSet<T> second)
    {
      List<T> set = new ArrayList<T>(first.set.size() + 1);

      for (int i = 0; i < first.set.size() - 1; i++)
        // if the n first are not the same, then no join can be done
        if (first.set.get(i).compareTo(second.set.get(i)) != 0)
          return null;
        else
          set.add(first.set.get(i));

      // put the two last items in the good order
      if (first.set.get(first.set.size() - 1).compareTo(second.set.get(second.set.size() - 1)) > 0)
      {
        set.add(first.set.get(first.set.size() - 1));
        set.add(second.set.get(first.set.size() - 1));
      }
      else
      {
        set.add(second.set.get(first.set.size() - 1));
        set.add(first.set.get(first.set.size() - 1));
      }

      return new ItemSet<T>(set);
    }

    @Override
      /**
       * hashCode functioned is used internally in Hashtable
       */
    public int hashCode()
    {
      int code = 0;

      for (int i = 0; i < set.size(); i++)
        code += (set.get(i).hashCode() % 1000) * (PRIMES[i % PRIMES.length]);

      return code;
    }

    /**
     * Used to determine whether two ItemSet objects are equal.
     * @param other The ItemSet which is compared.
     * @return true if the two ItemSet objects are equal, false otherwise.
     */
    public boolean equals(ItemSet<T> other)
    {
      if (other.set.size() != this.set.size())
        return false;
      for (int i = 0; i < set.size(); i++)
        if (set.get(i).compareTo(other.set.get(i)) != 0)
          return false;
      return true;
    }
  }


  /**
   * Run the Apriori algorithm on the given data set.
   * @param data The data set on which the algorithm will run.
   * @param minSupport The mininal support required to be considered as frequent.
   * @return A two dimensional list of all the frequent patterns.
   */
  public static <T extends Comparable<T>> List<List<List<T>>> run(T[][] data, int minSupport)
  {
    List<List<List<T>>> itemSets = new ArrayList<List<List<T>>>();
    Hashtable<ItemSet<T>, Integer> frequentItemSets = null;
    for (int k = 0; frequentItemSets == null || frequentItemSets.size() > 0; k++)
    {
      // generate the frequent item sets
      frequentItemSets = Apriori.<T>generateFrequentItemSets(data, minSupport, frequentItemSets);
      // add them to the global list
      itemSets.add(new ArrayList<List<T>>());
      for (ItemSet<T> itemSet : frequentItemSets.keySet())
        itemSets.get(k).add(itemSet.set);
    }

    // TODO: create association rules from the frequent itemsets

    return itemSets;
  }

  /**
   * Generate the frequent item sets of length N out of the given items sets of
   * length N-1.
   * @param data The data set on which the support will be calculated.
   * @param minSupport The mininal support required to be considered as frequent.
   * @param lowerLevelItemSets The N-1-length frequent items sets.
   * @return The frequent N-length frequent items sets.
   */
  private static <T extends Comparable<T>> Hashtable<ItemSet<T>, Integer> generateFrequentItemSets(T[][] data,
                                                                                                   int minSupport,
                                                                                                   Hashtable<ItemSet<T>, Integer> lowerLevelItemSets)
  {
    // generate candidates
    List<ItemSet<T>> candidates = new ArrayList<ItemSet<T>>();
    if (lowerLevelItemSets != null)
    {
      List<ItemSet<T>> itemSets = new ArrayList<ItemSet<T>>(lowerLevelItemSets.keySet());
      for (int i = 0; i < lowerLevelItemSets.size(); i++)
        for (int j = i + 1; j < lowerLevelItemSets.size(); j++)
          candidates.add(ItemSet.<T>join(itemSets.get(i), itemSets.get(j)));
    }
    else
      for (T[] itemSets : data)
        for (T item : itemSets)
          candidates.add(new ItemSet<T>(new ArrayList<T>(Arrays.asList(item))));

    // find the frequent ones
    Hashtable<ItemSet<T>, Integer> frequents = new Hashtable<ItemSet<T>, Integer>();
    List<ItemSet<T>> seens = new LinkedList<ItemSet<T>>();
    next:
    for (ItemSet<T> candidate : candidates)
      if (candidate != null)
      {
        // check if not already seen (as the candidate generation can create doubles)
        for (ItemSet<T> seen : seens)
          if (seen.equals(candidate))
            continue next;
        // add the candidate to the list of the seens
        seens.add(candidate);

        // add it if the support if higher than the mininal support
        if (candidate.support(data) >= minSupport)
          frequents.put(candidate, candidate.support(data));
      }

    return frequents;
  }
}
