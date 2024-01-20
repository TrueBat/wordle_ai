import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.HashSet;
import java.util.Iterator;

public class Turtur {

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

    int[][] charValuePos = new int[5][26];
    int[] charValue = new int[26];
    String[] allWords = new String[14855];
    String bestWord = "";
    int bestWordValue = 0;
    String secondBestWord = "";
    int secondBestWordValue = 0;
    String thirdBestWord = "";
    int thirdBestWordValue = 0;
    FastReader scan = new FastReader();
    short[][] wordChars = new short[5][26];
     

    HashSet<Character> mustHaveChars = new HashSet<Character>();
    HashSet<Character> knownChars = new HashSet<Character>();
    HashSet<String> possibleWords = new HashSet<String>();

    public void readWords() throws FileNotFoundException{
        FastReader read = new FastReader(new File("validWords.txt"));
        int i = 0;
        while(i < 14855){
            allWords[i] = read.next();
            i++;
        }
        read = new FastReader(new File("answers.txt"));
        i = 0;
        while(i < 2309){
            possibleWords.add(read.next());
            i++;
        }
    }

    public void evaluateWordResult(char[] word, char[] result) {
        
        for (int i = 0; i < 5; i++) {
            if(result[i] == 'y'){
                wordChars[i][word[i]-'a'] = -1;
                mustHaveChars.add(word[i]);
            }else if(result[i] == 'g'){
                mustHaveChars.add(word[i]);
                knownChars.add(word[i]);
                wordChars[i][word[i]-'a'] = 1;
            }else{
                if(!mustHaveChars.contains(word[i])){
                    for(int j = 0; j < 5; j++){
                        wordChars[j][word[i]-'a'] = (short) (wordChars[j][word[i]-'a'] == 1 ? 1 : -1);
                    }
                }else{
                    wordChars[i][word[i]-'a'] = -1;
                }
            }
        }
    }

    void calculateCharValue(){
        int numOfWords = possibleWords.size();
        for(int c = 0; c < 5; c++){
            for(int i = 0 ; i < 26 ; i++){
                int count = 0;
                for (String string : possibleWords) {
                    if(string.charAt(c) == i+'a'){
                        count++;
                    }
                }
                double frequency = (double) count / numOfWords;
                charValuePos[c][i] = (int) (50 * (frequency <= 0.6 ? func1(frequency) : (func2(frequency))));
            }
        }
        for(int i = 0 ; i < 26 ; i++){
            int count = 0;
            for (String string : possibleWords) {
                if(string.contains(Character.toString((char)(i+'a')))){
                    count++;
                }
            }
            double frequency = (double) count / numOfWords;
            charValue[i] = (int) (50 * (frequency <= 0.5 ? func1(frequency) : (func2(frequency))));
        }
    }

    double func1(double x){
        return (-1*Math.pow(-1*x+1,6) + 1);
    }
    double func2(double x){
        return (-1*Math.pow(x,11)+1);
    }

    void getPossibleWords(){
        Iterator<String> iterator = possibleWords.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            boolean possible = true;
            for(int i = 0; i < 5 ; i++){
                if(wordChars[i][word.charAt(i)-'a'] == -1){
                    possible = false;
                    break;
                }
                for(int j = 0; j < 26; j++){
                    if(wordChars[i][j] == 1 && word.charAt(i) != j+'a'){
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

    void getBestWords(){
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
                value += charValuePos[i][string.charAt(i)-'a'];
            }
            if(possibleWords.contains(string)){
                value+=1;
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
        if(possibleWords.size() == 2){
            String temp = bestWord;
            Iterator<String> iterator = possibleWords.iterator();
            bestWord = iterator.next();
            secondBestWord = iterator.next();
            bestWordValue = 50;
            secondBestWordValue = 50;
            thirdBestWord = temp;
            thirdBestWordValue = 50;
        }
    }
}

