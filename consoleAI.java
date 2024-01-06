import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Iterator;

public class consoleAI {

    static int charValue[] = new int[26];
    static String allWords[] = new String[2309];
    static String bestWord = "";
    static int bestWordValue = 0;
    static String secondBestWord = "";
    static int secondBestWordValue = 0;
    static String thirdBestWord = "";
    static int thirdBestWordValue = 0;
    static Scanner scan = new Scanner(System.in);
    static short[][] wordChars = new short[5][26];
    static int[] charFreq = new int[26];

    static HashSet<Character> mustHaveChars = new HashSet<Character>();
    static LinkedList<String> possibleWords = new LinkedList<String>();

    public static void main(String[] args) throws FileNotFoundException {
        readWords();
        while(possibleWords.size() > 1){
            int a = 0;
            getBestWords();
            a = 0;
            printBestWords();
            System.out.println("Enter the word and the result");
            evaluateWordResult();
            getPossibleWords();
            int x = 0;
        }
        System.out.println("The Word is: "+possibleWords.get(0));
    }

    public static void printBestWords(){
        System.out.println("The Best Words Are:");
        System.out.println("1. "+bestWord +" : "+bestWordValue);
        System.out.println("2. "+secondBestWord +" : "+secondBestWordValue);
        System.out.println("3. "+thirdBestWord +" : "+thirdBestWordValue);
    }

    public static void readWords() throws FileNotFoundException{
        Scanner read = new Scanner(new File("words.txt"));
        int i = 0;
        while(read.hasNext()){
            allWords[i] = read.next();
            possibleWords.add(allWords[i]);
            i++;
        }
    }

    public static void evaluateWordResult() {
        char[] word = scan.next().toCharArray();
        char[] result = scan.next().toCharArray();
        
        for (int i = 0; i < 5; i++) {
            if(result[i] == 'y'){
                wordChars[i][word[i]-'a'] = -1;
                mustHaveChars.add(word[i]);
            }else if(result[i] == 'g'){
                mustHaveChars.add(word[i]);
                wordChars[i][word[i]-'a'] = 1;
            }else{
                for(int j = 0; j < 5; j++){
                    wordChars[j][word[i]-'a'] = -1;
                }
            }
        }
    }

    static void calculateCharValue(){
        int numOfWords = possibleWords.size();
        for(int i = 0 ; i < 26 ; i++){
            int count = 0;
            for (String string : possibleWords) {
                if(string.contains(Character.toString((char)(i+'a')))){
                    count++;
                    charFreq[i]++;
                }
            }
            double frequency = (double) count / numOfWords;
            charValue[i] = (int) (100 * (1 - 2 * Math.abs(frequency - 0.5)));
        }
    }

    static void getPossibleWords(){
        Iterator<String> iterator = possibleWords.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            boolean possible = true;
            for(int i = 0; i < 5 ; i++){
                for(int j = 0; j < 26; j++){
                    if((wordChars[i][j] == -1 && word.charAt(i) == j+'a')
                     || 
                     (wordChars[i][j] == 1 && word.charAt(i) != j+'a')){
                        possible = false;
                        break;
                    }
                }
            }
            for(Character c : mustHaveChars){
                if(!word.contains(c.toString())){
                    possible = false;
                    break;
                }
            }

            if(!possible)iterator.remove();
        }
    }

    static void getBestWords(){
        bestWordValue = 0;
        secondBestWordValue = 0;
        thirdBestWordValue = 0;
        calculateCharValue();
        for (String string : allWords) {
            int value = 0;
            boolean[] charUsed = new boolean[26];
            for (int i = 0; i < string.length(); i++) {
                value += charUsed[string.charAt(i)-'a']? 0 : charValue[string.charAt(i)-'a'];
                charUsed[string.charAt(i)-'a'] = true;
            }
            if(possibleWords.contains(string)){
                value+=100;
            }
            if(value > bestWordValue){
                thirdBestWord = secondBestWord;
                thirdBestWordValue = secondBestWordValue;
                secondBestWord = bestWord;
                secondBestWordValue = bestWordValue;
                bestWord = string;
                bestWordValue = value;
            }else if(value > secondBestWordValue){
                thirdBestWord = secondBestWord;
                thirdBestWordValue = secondBestWordValue;
                secondBestWord = string;
                secondBestWordValue = value;
            }else if(value > thirdBestWordValue){
                thirdBestWord = string;
                thirdBestWordValue = value;
            }
        }
    }
}
