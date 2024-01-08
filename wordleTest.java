import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class wordleTest {
    static String allWords[] = new String[2309];
    public static void main(String[] args) throws FileNotFoundException {
        init();
        int countOfSolved = 0;
        int countOfNotSolved = 0;
        int numberOfWordsGuessed = 0;
        int maxCount = 0;

        int totalWords = 2309;
        int currentWord = 0;
        int progressBarWidth = 50;

        System.out.println("Loading....");

        StringBuilder progressStringBuilder = new StringBuilder();
        for (int i = 0; i < progressBarWidth; i++) {
            progressStringBuilder.append(" ");
        }
        long start = System.currentTimeMillis();
        LinkedList<String> wordsNotSolved = new LinkedList<String>();
        for(String ans : allWords){

            int progress = (int) (currentWord / (float) totalWords * progressBarWidth);
            StringBuffer progressBarBuffer = new StringBuffer(progressBarWidth + 10);
            progressBarBuffer.append('[');
            for (int i = 0; i < progressBarWidth; i++) {
                if (i < progress) {
                    progressBarBuffer.append('#');
                } else {
                    progressBarBuffer.append('-');
                }
            }
            progressBarBuffer.append("] ").append(" solving: ").append(ans);
            String progressBarWithBuffer = progressBarBuffer.toString();

            System.out.print("\r" + progressBarWithBuffer);

            Turtur turtur = new Turtur();
            turtur.readWords();

            int count = 1;
            String bestWord = "salet";
            while(count <= 6){
                String result = getResult(ans, bestWord);
                if(result.equals("ggggg")){
                    break;
                }
                turtur.evaluateWordResult(bestWord.toCharArray(), result.toCharArray());
                turtur.getPossibleWords();
                count++;
                if(turtur.possibleWords.size() > 1){
                    turtur.getBestWords();
                    bestWord = turtur.bestWord;
                }else{
                    bestWord = turtur.possibleWords.get(0);
                }
            }

            if(count > 6){
                countOfNotSolved++;
                wordsNotSolved.add(ans);
            }else{
                countOfSolved++;
                numberOfWordsGuessed += count;
            }
            maxCount = Math.max(maxCount, count);

            currentWord++;
        }
        long finish = System.currentTimeMillis();
        
        long totalTime = finish - start;
        StringBuffer progressBarBuffer = new StringBuffer(progressBarWidth + 10);
        progressBarBuffer.append('[');
        for (int i = 0; i < progressBarWidth; i++) {
            progressBarBuffer.append('#');
        }
        String progressBarWithBuffer = progressBarBuffer.toString();
        
        System.out.print("\r" + progressBarWithBuffer);
        
        System.out.println("\nTotal time: "+totalTime/1000+" seconds");
        System.out.println("Average time per testcase: "+ totalTime/totalWords+" ms");
        System.out.println("Number of words solved: "+countOfSolved);
        System.out.println("Number of words not solved: "+countOfNotSolved);
        double average = (double)numberOfWordsGuessed/countOfSolved;
        System.out.println("Average number of guesses: "+average);
        System.out.println("Words not solved: ");
        if(wordsNotSolved.size() > 0){
            for(String word : wordsNotSolved){
                System.out.print(word+" ");
            }
            System.out.println();
        }
        System.out.println("Max number of guesses: "+maxCount);
    }

    public static void init() throws FileNotFoundException{
        Scanner read = new Scanner(new File("answers.txt"));
        int i = 0;
        while(read.hasNext()){
            allWords[i] = read.next();
            i++;
        }
    }

    public static String getResult(String ans , String guess){
        String result = "";

        int[] numOfChar = new int[26];
        for(int i = 0 ; i < ans.length() ; i++){
            numOfChar[ans.charAt(i)-'a']++;
        }
        for(int i = 0 ; i < ans.length() ; i++){
            if(ans.charAt(i) == guess.charAt(i)){
                result += "g";
                numOfChar[ans.charAt(i)-'a']--;
            }else if(ans.contains(guess.charAt(i)+"")){
                if(numOfChar[guess.charAt(i)-'a'] > 0){
                    result += "y";
                    numOfChar[guess.charAt(i)-'a']--;
                }else{
                    result += "b";
                }

            }else{
                result += "b";
            }
        }

        return result;
    }

    
}