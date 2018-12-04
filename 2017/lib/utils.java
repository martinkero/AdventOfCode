import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class utils {
    static List<String> readInputAsList(String file, String delimiter) throws FileNotFoundException {
        Scanner s = new Scanner(new File(file)).useDelimiter(delimiter);
        List<String> inputList = new ArrayList<>();
        while (s.hasNext()) {
            inputList.add(s.next());
        }
        s.close();
        return inputList;
    }
}