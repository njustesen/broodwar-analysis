package apriori;

import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.io.*;


/**
 * The Apriori class is used to run the apriori algorithm on the given data set.
 */
public class Apriori
{
  /**
   * Here for testing.
   */

  static double minConfidence = 0.53;

  /**
   * The ItemSet class is used to represent a set of item (candidate or
   * frequent). It is also used as the key of the HashMap.
   */
  private static class ItemSet <T extends PatternFinder.Item> implements Comparable<ItemSet<T>>
  {
    public List<T> set;
    private int support = -1;

    public T outcome()
    {
      for (T item : set)
        if (item.isOutcome())
          return item;
      return null;
    }

    public ItemSet<T> woOutcome()
    {
      ItemSet<T> itemSet = new ItemSet<T>(set);
      itemSet.set.remove(outcome());
      return itemSet;
    }

    public T opponentRace()
    {
      for (T item : set)
        if (item.isOpponentRace())
          return item;
      return null;
    }

    public ItemSet<T> woOpponentRace()
    {
      ItemSet<T> itemSet = new ItemSet<T>(set);
      itemSet.set.remove(opponentRace());
      return itemSet;
    }

    public T map()
    {
      for (T item : set)
        if (item.isMap())
          return item;
      return null;
    }

    public ItemSet<T> woMap()
    {
      ItemSet<T> itemSet = new ItemSet<T>(set);
      itemSet.set.remove(map());
      return itemSet;
    }

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
    public static <T extends PatternFinder.Item> ItemSet<T> join(ItemSet<T> first, ItemSet<T> second)
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

    @Override
    public int compareTo(ItemSet<T> other)
    {
      if (this.equals(other))
                return 0;
      return 1;
    }
  }

  private static <T extends PatternFinder.Item> void generateAssociationRules(T[][] data,
                                                                              int minSupport,
                                                                              Set<ItemSet<T>> frequentItemSets,
                                                                              String outputFile)
  {
    PrintWriter writer;
    try
    {
      writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
    }
    catch (IOException e)
    {
      return;
    }
    for (ItemSet frequentItemSet : frequentItemSets)
    {
      T outcome;
      if ((outcome = (T) frequentItemSet.outcome()) != null && frequentItemSet.opponentRace() == null)
      {
        ItemSet set = frequentItemSet.woOutcome();
        if (set.set.size() != 0)
        {
          double outcomeConf = (double) frequentItemSet.support(data) / set.support(data);
          if (outcomeConf > minConfidence)
            writer.println(set + " -> " + outcome + " (" + outcomeConf + ") [" + ((double) set.support(data) / data.length) +"]");
          else
            outcome = null;
        }
      }

      T opponentRace = null;
      if ((opponentRace = (T) frequentItemSet.opponentRace()) != null && frequentItemSet.outcome() == null)
      {
        ItemSet set = frequentItemSet.woOpponentRace();
        if (set.set.size() != 0)
        {
          double opponentRaceConf = (double) frequentItemSet.support(data) / set.support(data);
          if (opponentRaceConf > minConfidence)
            writer.println(set + " -> " + opponentRace + " [" + opponentRaceConf + " / " + ((double) set.support(data) / data.length) +"]");
          else
            opponentRace = null;
        }
      }

      if (opponentRace != null && outcome != null)
      {
        ItemSet set = frequentItemSet.woOutcome().woOpponentRace();
        if (set.set.size() != 0)
        {
          // supp(build + win) / supp(build)
          double conf = (double) frequentItemSet.woOpponentRace().support(data) / set.support(data);
          if (conf > minConfidence)
          {
            // supp(build + race + win) / supp(build + race)
            double outcomeConf = (double) frequentItemSet.support(data) / frequentItemSet.woOutcome().support(data);
            // supp(build + race) / supp(build)
            double opponentRaceConf = (double) frequentItemSet.woOutcome().support(data) / set.support(data);
            // build_order -> outcome (chance of this outcome) vs race (chance of this outcome vs this race) [ use vs race / use ]
            writer.println(set + " -> " + outcome + " (" + conf + ") vs " + opponentRace + " (" + outcomeConf + ") [" + opponentRaceConf + " / " + ((double) set.support(data) / data.length) + "]");
          }
        }
      }

      else if (frequentItemSet.opponentRace() == null && frequentItemSet.outcome() == null)
        writer.println(frequentItemSet + " [" + (double) frequentItemSet.support(data) / data.length + "]");
    }

    writer.close();
  }

  /**
   * Run the Apriori algorithm on the given data set.
   * @param data The data set on which the algorithm will run.
   * @param minSupport The mininal support required to be considered as frequent.
   * @return A two dimensional list of all the frequent patterns.
   */
  public static <T extends PatternFinder.Item> List<List<List<T>>> run(T[][] data, int minSupport, String outputFile)
  {
    try
    {
      PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, false)));
      writer.write("min. supp.: " + minSupport + " (" + ((double) minSupport / data.length) + ")\n");
      writer.write("data set size: " + data.length + "\n");
      writer.write("min. conf.: " + minConfidence + "\n");
      writer.close();
    }
    catch (IOException e)
    {
    }

    List<List<List<T>>> itemSets = new ArrayList<List<List<T>>>();
    Map<ItemSet<T>, Integer> frequentItemSets = null;
    for (int k = 0; frequentItemSets == null || frequentItemSets.size() > 0; k++)
    {
      // generate the frequent item sets
      frequentItemSets = Apriori.<T>generateFrequentItemSets(data, minSupport, frequentItemSets);
      // add them to the global list
      itemSets.add(new ArrayList<List<T>>());
      for (ItemSet<T> itemSet : frequentItemSets.keySet())
      {
        itemSets.get(k).add(itemSet.set);
      }
      Apriori.<T>generateAssociationRules(data, minSupport, frequentItemSets.keySet(), outputFile);
    }

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
  private static <T extends PatternFinder.Item> Map<ItemSet<T>, Integer> generateFrequentItemSets(T[][] data,
                                                                                                   int minSupport,
                                                                                                   Map<ItemSet<T>, Integer> lowerLevelItemSets)
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
    Map<ItemSet<T>, Integer> frequents = new TreeMap<ItemSet<T>, Integer>();
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
