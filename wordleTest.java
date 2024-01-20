import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Iterator;


public class wordleTest {

        static class FastReader { 
        BufferedReader br; 
        StringTokenizer st; 
  
        public FastReader() { 
            br = new BufferedReader( 
                new InputStreamReader(System.in)); 
        } 

        public FastReader(File file) throws FileNotFoundException{ 
            br = new BufferedReader( 
                new InputStreamReader(new FileInputStream(file))); 
        }
  
        String next() { 
            while (st == null || !st.hasMoreElements()) { 
                try { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        }  
    } 

    static String[] allWords = new String[2309];
    public static void main(String[] args) throws FileNotFoundException {
        init();
        int countOfNotSolved = 0;
        int maxCount = 0;
        int totalWords = 2309;
        int currentWord = 0;
        int progressBarWidth = 50;
        int[] numberOfGuesses = new int[6];

        System.out.println("Loading....");

        StringBuilder progressStringBuilder = new StringBuilder();
        for (int i = 0; i < progressBarWidth; i++) {
            progressStringBuilder.append(" ");
        }
        LinkedList<String> wordsNotSolved = new LinkedList<String>();
        long start = System.currentTimeMillis();
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
                    Iterator<String> it = turtur.possibleWords.iterator();
                    bestWord = it.next();
                }
            }

            if(count > 6){
                countOfNotSolved++;
                wordsNotSolved.add(ans);
            }else{
                numberOfGuesses[count-1]++;
            }

            maxCount = Math.max(maxCount, count);

            currentWord++;
        }
        long finish = System.currentTimeMillis();
        
        long totalTime = finish - start;
        int countOfSolved = totalWords - countOfNotSolved;
        int totalNumberOfGuesses = 0;
        for(int i = 0 ; i < 6 ; i++){
            totalNumberOfGuesses += numberOfGuesses[i] * (i+1);
        }

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
        System.out.println("Words that took 1 guess: "+numberOfGuesses[0]);
        System.out.println("Words that took 2 guesses: "+numberOfGuesses[1]);
        System.out.println("Words that took 3 guesses: "+numberOfGuesses[2]);
        System.out.println("Words that took 4 guesses: "+numberOfGuesses[3]);
        System.out.println("Words that took 5 guesses: "+numberOfGuesses[4]);
        System.out.println("Words that took 6 guesses: "+numberOfGuesses[5]);
        double average = (double)totalNumberOfGuesses/countOfSolved;
        System.out.println("Average number of guesses: "+average);
        if(!wordsNotSolved.isEmpty()){
            System.out.println("Words not solved: ");
            for(String word : wordsNotSolved){
                System.out.print(word+" ");
            }
            System.out.println();
        }
        System.out.println("Max number of guesses: "+maxCount);
    }

    public static void init() throws FileNotFoundException{
        FastReader read = new FastReader(new File("answers.txt"));
        int i = 0;
        while(i < 2309){
            allWords[i] = read.next();
            i++;
        }
    }

    public static String getResult(String ans , String guess){
        StringBuilder result = new StringBuilder();

        int[] numOfChar = new int[26];
        for(int i = 0 ; i < ans.length() ; i++){
            numOfChar[ans.charAt(i)-'a']++;
        }
        for(int i = 0 ; i < ans.length() ; i++){
            if(ans.charAt(i) == guess.charAt(i)){
                result.append("g");
                numOfChar[ans.charAt(i)-'a']--;
            }else if(ans.contains(guess.charAt(i)+"")){
                if(numOfChar[guess.charAt(i)-'a'] > 0){
                    result.append("y");
                    numOfChar[guess.charAt(i)-'a']--;
                }else{
                    result.append("b");
                }

            }else{
                result.append("b");
            }
        }
        return result.toString();
    }
}