package apriori;

import java.util.*;
import json.MatchDecoder;
import analyser.Match;
import analyser.Player;
import analyser.Action;
import apriori.Apriori;
import analyser.Map;

public class PatternFinder
{
        public static class Item implements Comparable<Item>
        {
                private String value;
                private int player;
                private boolean outcome;
                private boolean map;
                private boolean opponentRace;
                private int order;

                public Item(int player, int order, String value, boolean outcome, boolean map, boolean opponentRace)
                {
                        this.player = player;
                        if (! (map || outcome || opponentRace))
                                this.value = order + "_" + value;
                        else
                                this.value = value;
                        this.order = order;
                        this.outcome = outcome;
                        this.opponentRace = opponentRace;
                        this.map = map;
                }

                public boolean isMap()
                {
                        return map;
                }

                public boolean isOpponentRace()
                {
                        return opponentRace;
                }

                public boolean isOutcome()
                {
                        return outcome;
                }

                public boolean isOrder(int i)
                {
                        if (this.outcome || this.map || this.opponentRace)
                                return true;
                        return (order == i);
                }

                @Override
                public int compareTo(Item other)
                {
                        if (other.value.equals(this.value))
                                return 0;
                        return 1;
                }

                @Override
                public String toString()
                {
                        return (value);
                }
        }


        public static void main(String[] argv)
        {
                String root = "matches/";
                int minSupport = 1000;
                int length = 10;
                Player.Race race = Player.Race.Zerg;
                Map.Type type = null; //Map.Type.Lost_Temple;

                List<Item[]> data = new ArrayList<Item[]>();
                MatchDecoder decoder = new MatchDecoder(root, type, race, 60000);

                Match match = null;

                while ((match = decoder.getMatch()) != null)
                {
                        for (int player = 0; player < match.players.length; player++)
                        {
                                if (match.players[player].race != race)
                                        continue;

                                List<Item> items = new ArrayList<Item>();

                                // items.add(new Item(0, 0, match.map.type.toString(), false, true, false));
                                items.add(new Item(0, 0, match.players[(player + 1) % 2].race.toString(), false, false, true));

                                List<Action> actions = match.players[player].selectActions(false, true, false, false);
                                for (int action = 0; action < Math.min(actions.size(), length); action++)
                                        items.add(new Item(player + 1, action, actions.get(action).actionType.toString(), false, false, false));
                                if (match.players[player].win)
                                        items.add(new Item(player + 1, 0, "win", true, false, false));
                                else
                                        items.add(new Item(player + 1, 0, "lose", true, false, false));

                                data.add(items.toArray(new Item[items.size()]));
                        }


                }

                List<List<List<Item>>> output = Apriori.<Item>run(data.toArray(new Item[data.size()][]), data.size() / 100 * 2);
                // for (int i = 0; i < output.size(); i++)
                // {
                //         System.out.println("==============( " + i + " )================");
                //         for (int j = 0; j < output.get(i).size(); j++)
                //                 for (int k = 0; k < output.get(i).get(j).size(); k++)
                //                         System.out.printf("%s" + (k < output.get(i).get(j).size() - 1 ? ", " : "\n"),
                //                                           output.get(i).get(j).get(k).toString());
                // }
        }
}
