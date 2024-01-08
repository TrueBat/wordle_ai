import java.io.FileNotFoundException;

public class consoleAI {
    public static void main(String[] args) throws FileNotFoundException {
        Turtur turtur = new Turtur();
        turtur.readWords();

        int count = 1;
        String bestWord = "salet";
        while(count <= 6){
            if(count == 1){
                System.out.println("The Best Word is: " + bestWord);
            }else{
                System.out.println("1st Best Word is: " + turtur.bestWord + " (" + turtur.bestWordValue + ")");
                System.out.println("2nd Best Word is: " + turtur.secondBestWord + " (" + turtur.secondBestWordValue + ")");
                System.out.println("3rd Best Word is: " + turtur.thirdBestWord + " (" + turtur.thirdBestWordValue + ")");
            }
            System.out.println("possible answers remaining: " + turtur.possibleWords.size());
            System.out.println("Enter the word chosen and its result: ");
            String word = turtur.scan.next();
            String result = turtur.scan.next();
            turtur.evaluateWordResult(word.toCharArray(), result.toCharArray());
            turtur.getPossibleWords();
            count++;
            if(turtur.possibleWords.size() == 1){
                System.out.println("The answer is: " + turtur.possibleWords.get(0));
                break;
            }else{
                turtur.getBestWords();
            }
        }
    }
}