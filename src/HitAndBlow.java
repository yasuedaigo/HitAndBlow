import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HitAndBlow {
    private static int inputNumber;
    private static int targetNumber;
    private static final Random RANDOM = new Random();
    private static final Scanner STDIN = new Scanner(System.in);
    private static final int MAX_CHALLENGE_TIMES = 5;
    private static final int RANDOM_NUM_RANGE = 10;
    private static final int NUMBER_DIGIT = 4;
    private static final int CHALLENGE_COUNT_OF_START = 0;
    private static final int HITANDBLOW_COUNT_OF_START = 0;
    private static final int NUMBER_OF_COUNT = 2;
    
    private static List<Integer> correctNumbers = new ArrayList<Integer>();
    private static List<Integer> inputNumbers = new ArrayList<Integer>();

    private static final String QUESTION_NUM_MESSAGE = "%d桁の数字を入力して下さい。";
    private static final String RULE_MESSAGE = String.format("答えられるのは%d回までだよ。", MAX_CHALLENGE_TIMES);

    
    private static boolean testmode = true;
    public static void main(String[] args) {
        if(testmode == true){
            test();
            return;
        }
        targetNumber = RANDOM.nextInt(RANDOM_NUM_RANGE);
        System.out.println(targetNumber);
        showFirstMessage();
        int challengeTimesCounter = CHALLENGE_COUNT_OF_START;
        while (!isCountOver(challengeTimesCounter)) {
            showCountMessage(challengeTimesCounter);
            receiveinputNumber();
            if (isCorrect()) {
                break;
            }
            showHintMessage();
            challengeTimesCounter++;
        }
        showResultMessage(challengeTimesCounter);
        STDIN.close();
    }

    private static void showResultMessage(int challengeTimesCounter) {
        if (isCorrect()) {
            System.out.println(String.format("すごい！！%d回で当てられちゃった！", challengeTimesCounter));
            return;
        }
        System.out.println("残念！！ 正解は " + targetNumber + " でした！");
    }

    private static void showCountMessage(int challengeTimesCounter) {
        System.out.println(String.format("%d回目", challengeTimesCounter));
    }

    private static void showFirstMessage() {
        System.out.println(RULE_MESSAGE);
    }

    private static boolean isCountOver(int challengeTimesCounter) {
        if (challengeTimesCounter <= MAX_CHALLENGE_TIMES) {
            return false;
        }
        return true;
    }

    private static boolean isNumber(String inputStr) {
        try {
            Integer.parseInt(inputStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isCorrectDigit(String inputStr){
        if(inputStr.length() == NUMBER_DIGIT){
            return true;
        }
        return false;
    }

    private static boolean isCorrect() {
        if (targetNumber == inputNumber) {
            return true;
        }
        return false;
    }

    private static boolean isTooBig() {
        if (targetNumber >= inputNumber) {
            return false;
        }
        return true;
    }

    private static void showHintMessage() {
        if (isTooBig()) {
            System.out.println("もっと小さい数字だよ");
            return;
        }
        System.out.println("もっと大きい数字だよ");
    }

    private static boolean isContain(List<Integer> numbers,int number){
        return numbers.contains(number);
    }

    private static void showQuestionNumMessage() {
        System.out.println(String.format(QUESTION_NUM_MESSAGE,NUMBER_DIGIT));
    }

    private static void test(){
        System.out.println("テストモードです");
        generateCorrectNumebr();
        /*for(int s : correctNumbers){
            System.out.print(s);
        }*/
        System.out.println("correctNumbers"+correctNumbers);
        int inputNumber = receiveinputNumber();
        System.out.println("correctNumbers"+correctNumbers+"　inputNumbers"+inputNumbers);
        inputNumbers = numberParseToList(inputNumber);
        int[] hitAndBlowCount = countHitAndBlow(correctNumbers,inputNumbers);
        System.out.println("ヒット"+hitAndBlowCount[0]+"　ブロー"+hitAndBlowCount[1]);
    }

    private static List<Integer> numberParseToList(int numbers){
        String format = "%0" + String.valueOf(NUMBER_DIGIT) + "d";
        String numberStr = String.format(format,numbers);
        List<Integer> inputNumbers = new ArrayList<Integer>();
        for(int i=0;i<NUMBER_DIGIT;i++){
            char number = numberStr.charAt(i);
            inputNumbers.add(Character.getNumericValue(number));
        }
        return inputNumbers;
    }

    private static int generateRandomNumber(int NUMRANGE){
        int number = RANDOM.nextInt(RANDOM_NUM_RANGE);
        if(isContain(correctNumbers,number)){
            number = generateRandomNumber(NUMRANGE);
        }
        return number;
    }

    private static void generateCorrectNumebr(){
        for(int i = 0; i < NUMBER_DIGIT; i++){
            correctNumbers.add(i,generateRandomNumber(RANDOM_NUM_RANGE));
        }
    }

    private static int receiveinputNumber() {
        String inputStr;
        int inputNumber;
        showQuestionNumMessage();
        inputStr = STDIN.nextLine();
        if(!isNumber(inputStr) || !isCorrectDigit(inputStr)){
            inputNumber = receiveinputNumber();
            return inputNumber;
        }
        inputNumber = Integer.parseInt(inputStr);
        return inputNumber;
    }

    private static int[] countHitAndBlow(List<Integer> correctNumbers,List<Integer> inputNumbers){
        int hit = HITANDBLOW_COUNT_OF_START;
        int blow = HITANDBLOW_COUNT_OF_START;
        for(int i=0;i<NUMBER_DIGIT;i++){
            if(isHit(i,correctNumbers,inputNumbers)){
                hit++;
            }
        }
        blow = countBlow(hit,correctNumbers,inputNumbers);
        int[] hitAndBlowCount = {hit,blow};
        return hitAndBlowCount;
    }

    private static boolean isHit(int index,List<Integer> correctNumbers,List<Integer> inputNumbers){
        if(correctNumbers.get(index) == (inputNumbers.get(index))){
            return true;
        }
        return false;
    }

    private static int countBlow(int hitCount,List<Integer> correctNumbers,List<Integer> inputNumbers){
        int containCount = 0;
        for(int inputNumber : inputNumbers){
            if(isContain(correctNumbers,inputNumber)){
                containCount++;
            }
        }
        return containCount - hitCount;
    }

}