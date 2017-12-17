import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {
    public static void main(String[] args) {
        testKnotHash();

        List<String> stringHashList = new ArrayList<>();
        try {
            stringHashList = utils.readInputAsList("day10/Day10.in", ",");
        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }
        List<Integer> hashList = stringHashList.stream().map(Integer::parseInt).collect(Collectors.toList());


        List<Integer> hash = knotHash(0, 255, hashList);
        int result = hash.get(0) * hash.get(1);
        System.out.println(result);
    }

    static List<Integer> knotHash(int seqStart, int seqEnd, List<Integer> input) {
        int skipSize = 0;
        int currentPosition = 0;
        List<Integer> hashList = new ArrayList<>();
        for (int i = seqStart; i <= seqEnd; i++) {
            hashList.add(i);
        }

        for (int i = 0; i < input.size(); i++) {
            int length = input.get(i);

            List<Integer> subList = getSublistWithWrap(hashList, currentPosition, currentPosition + length);
            Collections.reverse(subList);

            for (int j = 0; j < subList.size(); j++) {
                hashList.set((currentPosition + j) % hashList.size(), subList.get(j));
            }

            currentPosition = (currentPosition + length + skipSize) % hashList.size();
            skipSize++;
        }

        return hashList;
    }

    private static List<Integer> getSublistWithWrap(List<Integer> list, int startIndex, int endIndex) {
        List<Integer> subList = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) {
            subList.add(list.get(i % list.size()));
        }
        return subList;
    }

    private static void testKnotHash() {
        List<Integer> input = Arrays.asList(3, 4, 1, 5);
        List<Integer> hash = knotHash(0, 4, input);
        int result = hash.get(0) * hash.get(1);
        if (result != 12) {
            System.out.println("Test failed, expected 12, got " + result);
            System.exit(1);
        }
    }
}