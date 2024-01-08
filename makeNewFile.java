import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class makeNewFile {
    public static void main(String[] args) throws IOException {
        FileWriter myWriter = new FileWriter("answers.txt");
        Scanner scan = new Scanner(new File("oldFile.txt"));
        while(scan.hasNext()){
            String word = scan.next();
            myWriter.write(word+"\n");
        }
    }
}
