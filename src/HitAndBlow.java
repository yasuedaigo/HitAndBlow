import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HitAndBlow {
    private static final Random RANDOM = new Random();
    private static final Scanner STDIN = new Scanner(System.in);
    private static final int RANDOM_NUM_RANGE = 10;
    private static final int NUMBER_DIGIT = 4;
    private static final int CHALLENGE_COUNT_OF_START = 0;
    private static final int HITANDBLOW_STARTCOUNT = 0;
    private static final int HIT_COUNT_INDEX = 0;
    private static final int BLOW_COUNT_INDEX = 1;
    private static final int MAX_CHALLENGE_COUNT = 5;

    private static List<Integer> correctNumbers = new ArrayList<Integer>();
    private static List<Integer> inputNumbers = new ArrayList<Integer>();

    private static final String QUESTION_NUM_MESSAGE = "%d桁の数字を入力して下さい。";
    private static final String HINT_MESSAGE = "ヒット：%d個、ブロー：%d個";
    private static final String RESULT_MESSAGE = "おめでとう！%d回目で成功♪";
    private static final String FINISH_MESSAGE = "残念。答えは%dでした";

    public static void main(String[] args) {
        int challengeCount = CHALLENGE_COUNT_OF_START;
        generateCorrectNumber();
        while (!isCountOver(challengeCount)) {
            challengeCount++;
            playRound();
            if (isCorrect(correctNumbers, inputNumbers)) {
                showResultMessage(challengeCount);
                break;
            }
        }
        showFinishMessage(correctNumbers);
    }

    private static void playRound() {
        System.out.println(correctNumbers);
        int inputNumber = receiveinputNumber();
        inputNumbers = numberParseToList(inputNumber);
        int[] hitAndBlowCount = countHitAndBlow(correctNumbers, inputNumbers);
        showHintMessage(hitAndBlowCount);
    }

    private static boolean isNumber(String inputStr) {
        try {
            Integer.parseInt(inputStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isCorrectDigit(String inputStr) {
        if (inputStr.length() == NUMBER_DIGIT) {
            return true;
        }
        return false;
    }

    private static boolean isCorrect(List<Integer> correctNumbers, List<Integer> inputNumbers) {
        int[] hitAndBlowCount = countHitAndBlow(correctNumbers, inputNumbers);
        if (hitAndBlowCount[HIT_COUNT_INDEX] == NUMBER_DIGIT) {
            return true;
        }
        return false;
    }

    private static boolean isContain(List<Integer> numbers, int number) {
        return numbers.contains(number);
    }

    private static void showQuestionNumMessage() {
        System.out.println(String.format(QUESTION_NUM_MESSAGE, NUMBER_DIGIT));
    }

    private static void showHintMessage(int[] hitAndBlowCount) {
        System.out.println(
                String.format(HINT_MESSAGE, hitAndBlowCount[HIT_COUNT_INDEX], hitAndBlowCount[BLOW_COUNT_INDEX]));
    }

    private static void showResultMessage(int count) {
        System.out.println(String.format(RESULT_MESSAGE, count));
    }

    private static void showFinishMessage(List<Integer> correctNumbers) {
        System.out.println(String.format(FINISH_MESSAGE, listParseToInt(correctNumbers)));
    }

    private static boolean isCountOver(int count) {
        if (count < MAX_CHALLENGE_COUNT) {
            return false;
        }
        return true;
    }

    private static List<Integer> numberParseToList(int numbers) {
        String format = "%0" + String.valueOf(NUMBER_DIGIT) + "d";
        String numberStr = String.format(format, numbers);
        List<Integer> inputNumbers = new ArrayList<Integer>();
        for (int i = 0; i < NUMBER_DIGIT; i++) {
            char number = numberStr.charAt(i);
            inputNumbers.add(Character.getNumericValue(number));
        }
        return inputNumbers;
    }

    private static int listParseToInt(List<Integer> intList) {
        StringBuilder sb = new StringBuilder();
        for (int number : intList) {
            sb.append(number);
        }
        return Integer.valueOf(sb.toString());
    }

    private static int generateRandomNumber(int NUMRANGE) {
        int number = RANDOM.nextInt(RANDOM_NUM_RANGE);
        if (isContain(correctNumbers, number)) {
            number = generateRandomNumber(NUMRANGE);
        }
        return number;
    }

    private static void generateCorrectNumber() {
        for (int i = 0; i < NUMBER_DIGIT; i++) {
            correctNumbers.add(i, generateRandomNumber(RANDOM_NUM_RANGE));
        }
    }

    private static int receiveinputNumber() {
        String inputStr;
        int inputNumber;
        showQuestionNumMessage();
        inputStr = STDIN.nextLine();
        if (!isNumber(inputStr) || !isCorrectDigit(inputStr)) {
            inputNumber = receiveinputNumber();
            return inputNumber;
        }
        inputNumber = Integer.parseInt(inputStr);
        return inputNumber;
    }

    private static int[] countHitAndBlow(List<Integer> correctNumbers, List<Integer> inputNumbers) {
        int hit = HITANDBLOW_STARTCOUNT;
        int blow = HITANDBLOW_STARTCOUNT;
        for (int i = 0; i < NUMBER_DIGIT; i++) {
            if (isHit(i, correctNumbers, inputNumbers)) {
                hit++;
            }
        }
        blow = countBlow(hit, correctNumbers, inputNumbers);
        int[] hitAndBlowCount = { hit, blow };
        return hitAndBlowCount;
    }

    private static boolean isHit(int index, List<Integer> correctNumbers, List<Integer> inputNumbers) {
        if (correctNumbers.get(index) == (inputNumbers.get(index))) {
            return true;
        }
        return false;
    }

    private static int countBlow(int hitCount, List<Integer> correctNumbers, List<Integer> inputNumbers) {
        int containCount = 0;
        for (int inputNumber : inputNumbers) {
            if (isContain(correctNumbers, inputNumber)) {
                containCount++;
            }
        }
        return containCount - hitCount;
    }

}