import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HitAndBlow {
    private static final Random RANDOM = new Random();
    private static final Scanner STDIN = new Scanner(System.in);
    private static final int RANDOM_NUM_RANGE = 10;
    private static final int NUMBER_DIGIT = 5;
    private static final int CHALLENGE_COUNT_OF_START = 0;
    private static final int HITANDBLOW_STARTCOUNT = 0;
    private static final int HIT_COUNT_INDEX = 0;
    private static final int BLOW_COUNT_INDEX = 1;
    private static final int MAX_CHALLENGE_COUNT = 20;
    private static final int ROUND_OF_HINT = 3;
    private static List<Integer> HINT_ROUNDS = new ArrayList<>() {
        {
            for (int i = 0; i < NUMBER_DIGIT; i++) {
                add(ROUND_OF_HINT * i);
            }
        }
    };

    private static List<Integer> correctNumbers = new ArrayList<>();
    private static List<Integer> inputNumbers = new ArrayList<>();

    private static final String QUESTION_NUM_MESSAGE = "%d桁の数字を入力して下さい。";
    private static final String POINT_MESSAGE = "ヒット：%d個、ブロー：%d個";
    private static final String RESULT_MESSAGE = "おめでとう！%d回目で成功♪";
    private static final String FINISH_MESSAGE = "残念。答えは%dでした";
    private static final String HINT_MESSAGE = "%dつ目の数字は%dだよ";

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
            if (isHintRound(challengeCount)) {
                showHintMessage(challengeCount);
            }
        }
        showFinishMessage(correctNumbers);
    }

    private static void playRound() {
        int inputNumber = receiveInputNumber();
        inputNumbers = numberParseToList(inputNumber);
        int[] hitAndBlowCount = countHitAndBlow(correctNumbers, inputNumbers);
        showPointMessage(hitAndBlowCount);
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
        return inputStr.length() == NUMBER_DIGIT;
    }

    private static boolean isCorrect(List<Integer> correctNumbers, List<Integer> inputNumbers) {
        int[] hitAndBlowCount = countHitAndBlow(correctNumbers, inputNumbers);
        return hitAndBlowCount[HIT_COUNT_INDEX] == NUMBER_DIGIT;
    }

    private static boolean isContain(List<Integer> numbers, int number) {
        return numbers.contains(number);
    }

    private static void showQuestionNumMessage() {
        System.out.println(String.format(QUESTION_NUM_MESSAGE, NUMBER_DIGIT));
    }

    private static void showPointMessage(int[] hitAndBlowCount) {
        System.out.println(
                String.format(POINT_MESSAGE, hitAndBlowCount[HIT_COUNT_INDEX],
                        hitAndBlowCount[BLOW_COUNT_INDEX]));
    }

    private static void showResultMessage(int count) {
        System.out.println(String.format(RESULT_MESSAGE, count));
    }

    private static void showFinishMessage(List<Integer> correctNumbers) {
        System.out.println(String.format(FINISH_MESSAGE, listParseToInt(correctNumbers)));
    }

    private static boolean isCountOver(int count) {
        return !(count < MAX_CHALLENGE_COUNT);
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

    private static int receiveInputNumber() {
        String inputStr;
        int inputNumber;
        showQuestionNumMessage();
        inputStr = STDIN.nextLine();
        if (!isNumber(inputStr) || !isCorrectDigit(inputStr)) {
            inputNumber = receiveInputNumber();
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
            } else if (isBlow(i, correctNumbers, inputNumbers)) {
                blow++;
            }
        }
        int[] hitAndBlowCount = { hit, blow };
        return hitAndBlowCount;
    }

    private static boolean isHit(int index, List<Integer> correctNumbers, List<Integer> inputNumbers) {
        return correctNumbers.get(index) == (inputNumbers.get(index));
    }

    private static boolean isBlow(int index, List<Integer> correctNumbers, List<Integer> inputNumbers) {
        if (isHit(index, correctNumbers, inputNumbers)) {
            return false;
        }
        return isContain(correctNumbers, inputNumbers.get(index));
    }

    private static boolean isHintRound(int challengeCount) {
        return HINT_ROUNDS.contains(challengeCount);
    }

    private static void showHintMessage(int challengeCount) {
        int hintDigit = HINT_ROUNDS.indexOf(challengeCount);
        System.out.println(String.format(HINT_MESSAGE, hintDigit, correctNumbers.get(hintDigit - 1)));
    }

}