import java.io.FileNotFoundException;
import java.util.*;

public class day7 {
    public static void main(String[] args) {
        List<String> discSpecs = new ArrayList<>();
        try {
            discSpecs = utils.readInputAsList("day7/day7.in", "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Map<String, List<String>> discs = parseDiscSpecs(discSpecs);
        Map<String, Integer> discHierarchies = getDiscHierarchies(discs);
        System.out.println(findRootDisc(discHierarchies));
    }

    private static Map<String, List<String>> parseDiscSpecs(List<String> discSpecs) {
        Map<String, List<String>> discs = new HashMap<>();
        for (String discSpec : discSpecs) {
            String mainDisc = parseMainDisc(discSpec);
            List<String> subDiscs = parseSubDiscs(discSpec);
            discs.put(mainDisc, subDiscs);
        }
        return discs;
    }

    private static String parseMainDisc(String discSpec) {
        return discSpec.split("\\s+")[0];
    }

    private static List<String> parseSubDiscs(String discSpec) {
        String subDiscString;
        try {
            subDiscString = discSpec.split("->")[1];
        } catch (IndexOutOfBoundsException e) {
            return new ArrayList<>();
        }
        String[] subDiscs = subDiscString.replaceAll("\\s+", "").split(",");
        return Arrays.asList(subDiscs);
    }


    private static Map<String, Integer> getDiscHierarchies(Map<String, List<String>> discs) {
        Map<String, Integer> discHierarchies = new HashMap<>();
        for (String disc : discs.keySet()) {
            int hierarchyValue = findHierarchyValue(discs, disc);
            discHierarchies.put(disc, hierarchyValue);
        }
        return discHierarchies;
    }

    private static int findHierarchyValue(Map<String, List<String>> discs, String disc) {
        int hierarchyValue = 0;
        String currentDisc = disc;
        while (true) {
            try {
                currentDisc = getParentDisc(discs, currentDisc);
            } catch (NoSuchElementException e) {
                return hierarchyValue;
            }
            hierarchyValue++;
        }
    }

    private static String getParentDisc(Map<String, List<String>> discs, String subDisc) throws NoSuchElementException {
        for (Map.Entry<String, List<String>> disc : discs.entrySet()) {
            if (disc.getValue().contains(subDisc)) {
                return disc.getKey();
            }
        }
        throw new NoSuchElementException();
    }

    private static String findRootDisc(Map<String, Integer> discHierarchies) throws NoSuchElementException {
        for (Map.Entry<String, Integer> discHierarchy : discHierarchies.entrySet()) {
            if (discHierarchy.getValue() == 0) {
                return discHierarchy.getKey();
            }
        }
        throw new NoSuchElementException();
    }
}