import org.omg.PortableInterceptor.INACTIVE;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day10 {
    static int skipSize = 0;
    static int currentPosition = 0;

    public static void main(String[] args) {
        testKnot();
        testBitwiseXOR();
        testKnotHash();

        List<String> lengths = new ArrayList<>();
        try {
            lengths = utils.readInputAsList("day10/Day10.in", ",");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        part1(lengths);
        part2(lengths);
    }

    private static void part1(List<String> stringLengths) {
        List<Integer> lengths = stringLengths.stream().map(Integer::parseInt).collect(Collectors.toList());

        List<Integer> hashList = new ArrayList<>();
        for (int i = 0; i <= 255; i++) {
            hashList.add(i);
        }
        List<Integer> hash = knot(hashList, lengths);
        int result = hash.get(0) * hash.get(1);
        System.out.println("Part1:");
        System.out.println(result);
    }

    private static void part2(List<String> lengths) {
        String lengthsString = String.join(",", lengths);


        System.out.println("Part2:");
        System.out.println(knotHash(lengthsString));


    }

    static String knotHash(String input) {
        skipSize = 0;
        currentPosition = 0;
        List<Integer> lengths = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            lengths.add((int) input.charAt(i));
        }
        lengths.add(17);
        lengths.add(31);
        lengths.add(73);
        lengths.add(47);
        lengths.add(23);

        List<Integer> denseHash = generateDenseHash(lengths);

        String hash = "";
        for (Integer number : denseHash) {
            String hex = Integer.toHexString(number);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            hash += hex;
        }
        return hash;
    }

    private static List<Integer> generateDenseHash(List<Integer> lengths) {
        List<Integer> sparseHashList = generateSparseHash(lengths);
        List<Integer> denseHashList = new ArrayList<>();
        for (int i = 0; i < 255; i += 16) {
            List<Integer> subList = sparseHashList.subList(i, i + 16);
            int XORValue = bitwiseXOR(subList);
            denseHashList.add(XORValue);
        }
        return denseHashList;
    }

    private static List<Integer> generateSparseHash(List<Integer> lengths) {
        List<Integer> sparseHashList = new ArrayList<>();
        for (int i = 0; i <= 255; i++) {
            sparseHashList.add(i);
        }
        for (int i = 0; i < 64; i++) {
            sparseHashList = knot(sparseHashList, lengths);
        }
        return sparseHashList;
    }

    private static int bitwiseXOR(List<Integer> numbers) {
        int XORValue;
        XORValue = numbers.get(0);
        for (int j = 1; j < numbers.size(); j++) {
            XORValue = XORValue ^ numbers.get(j);
        }
        return XORValue;
    }

    private static List<Integer> knot(List<Integer> hashList, List<Integer> lengths) {
        for (int i = 0; i < lengths.size(); i++) {
            int length = lengths.get(i);

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

    private static void testKnot() {
        List<Integer> input = Arrays.asList(3, 4, 1, 5);
        List<Integer> hashList = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            hashList.add(i);
        }
        List<Integer> hash = knot(hashList, input);
        int result = hash.get(0) * hash.get(1);
        if (result != 12) {
            System.out.println("testKnot failed, expected 12, got " + result);
            System.exit(1);
        }
    }

    private static void testKnotHash() {
        Map<String, String> inputAndExpectedResults = new HashMap<>();
        inputAndExpectedResults.put("", "a2582a3a0e66e6e86e3812dcb672a272");
        inputAndExpectedResults.put("AoC 2017", "33efeb34ea91902bb2f59c9920caa6cd");
        inputAndExpectedResults.put("1,2,3", "3efbe78a8d82f29979031a4aa0b16a9d");
        inputAndExpectedResults.put("1,2,4", "63960835bcdc130f0b66d7ff4f6a5a8e");

        for (Map.Entry<String, String> inputAndExpectedResult : inputAndExpectedResults.entrySet()) {
            String result = knotHash(inputAndExpectedResult.getKey());
            String expectedResult = inputAndExpectedResult.getValue();
            if (!result.equals(expectedResult)) {
                System.out.println("testKnotHash failed, got: " + result + " expected: " + expectedResult);
                System.exit(1);
            }
        }
    }

    private static void testBitwiseXOR() {
        String input = "65 27 9 1 4 3 40 50 91 7 6 0 2 5 68 22";
        List<Integer> inputList = Arrays.stream(input.split("\\s")).map(Integer::parseInt).collect(Collectors.toList());
        int expectedResult = 64;
        int result = bitwiseXOR(inputList);
        if (result != expectedResult) {
            System.out.println("testBitwiseXOR failed, got: " + result + ", expected: " + expectedResult);
            System.exit(1);
        }
    }
}