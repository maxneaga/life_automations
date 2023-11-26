import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to generate Sectet Santa pairings among extended family.
 * Rules:
 *  - Everyone must give and receive exactly 1 gift
 *  - Gift giving must cross the boundaries of the immediate family
 */
public class SectetSanta {
    public static void main(String[] args) {
        // HashMap containing families and their members
        HashMap<String, String[]> families = new HashMap<>();
        families.put("Family 1", new String[]{"Dawn", "Tony", "Heather", "Alex", "Emily", "Lily", "Max"});
        families.put("Family 2", new String[]{"Vito", "Trudy"});
        families.put("Family 3", new String[]{"Juliana", "Ben"});
        families.put("Family 5", new String[]{"Elaine", "Robert", "Wayne", "Jenny"});

        HashMap<String, String> pairs = generatePairs(families);
        System.out.println(pairs);
    }

    private static HashMap<String, String> generatePairs(HashMap<String, String[]> families) {
        ArrayList<String> givers = new ArrayList<>();
        ArrayList<String> remainingReceivers = new ArrayList<>(); // Will store list of people left to get a gift
        HashMap<String, String> pairs = new HashMap<>();
        HashMap<String, String> personsFamily = new HashMap<>();

        // Generate list of all people to receive a gift
        // Generate mapping of person => family to make it easier to check one's belonging to a family
        for (Map.Entry<String, String[]> family : families.entrySet()) {
            remainingReceivers.addAll(Arrays.asList(family.getValue()));
            for (String member : family.getValue()) {
                personsFamily.put(member, family.getKey());
            }
        }

        // Randomly shuffle the list of participants (gift receivers)
        Collections.shuffle(remainingReceivers);

        for (String family : families.keySet()) {
            for (String member : families.get(family)) {
                givers.add(member);

                // Find receiver for the current giver
                for (String receiverCandidate : remainingReceivers) {
                    if (receiverCandidate.equals(member) // Receiver cannot be the giver
                    || personsFamily.get(member).equals(personsFamily.get(receiverCandidate))) { // Cannot be from the same family
                        continue; // Check the next candidate
                    }
                    else {
                        pairs.put(member, receiverCandidate);
                        remainingReceivers.remove(receiverCandidate);
                        break;
                    }
                }
            }
        }

        // If someone is left without a gift due to constraints,
        // keep retrying assignments until everyone gets a gift
        if (remainingReceivers.size() > 0) {
            pairs = generatePairs(families);
        }
        return pairs;
    }
}