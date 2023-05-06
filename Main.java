import java.security.Key;
import java.security.KeyStore;
import java.util.*;


class IncorrectInputException extends Exception {
    // Для входящего потока, в случае если массив получается больше 3 элементов
    public IncorrectInputException(String error_name) {
        super(error_name);
    }
}


class Converter {
    private HashMap<Character, Integer> map = new HashMap<>();

    public Converter() {
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
    }

    public boolean isRoman(String digit) {
        return map.containsKey(digit.charAt(0));
    }

    public int romanToArab(String romanDigit) {
        char[] charsArray = romanDigit.toCharArray();
        int  arabDigitResult = map.get(charsArray[romanDigit.length() - 1]);
        for (int i = romanDigit.length() - 2; i >= 0; i--) {
            if (map.get(charsArray[i]) < map.get(charsArray[i + 1])) {
                arabDigitResult -= map.get(charsArray[i]);
            } else {
                arabDigitResult += map.get(charsArray[i]);
            }
        }
        return arabDigitResult;
    }

    public String arabToRoman(int digit) {
        String[] romanDigits = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder romanString = new StringBuilder();

        for (int i = 0; i < arabicValues.length; i++) {
            while (digit >= arabicValues[i]) {
                romanString.append(romanDigits[i]);
                digit -= arabicValues[i];
            }
        }
        return romanString.toString();
    }
}


class Main {
    public static void main(String[] args) throws IncorrectInputException {
        System.out.print("Введите мат. выражение!" + "\n");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        System.out.println(calc(input));
    }


    public static String calc(String input) throws IncorrectInputException {
        String[] signs = input.split(" ");
        int digitsCounter = 0;
        int operatorsCounter = 0;
        String currentOperator = " ";
        Converter converter = new Converter();

        for (String string : signs) {
            if (string.equals("+") || string.equals("-") || string.equals("*") || string.equals("/")) {
                currentOperator = string;
                operatorsCounter += 1;
            }
        }

        for (String string : signs) {
            if (!string.equals("+") && !string.equals("-") && !string.equals("*") && !string.equals("/")) {
                digitsCounter += 1;
            }
        }

        if (operatorsCounter == 1 && digitsCounter == 2) {
            // continue
        }
        else if (operatorsCounter == 0 || signs[0].equals("+") ||  signs[0].equals("-") || signs[0].equals("/") || signs[0].equals("*") ){
            throw new IncorrectInputException("строка не является математической операцией");
        } else {
            throw new IncorrectInputException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        if (converter.isRoman(signs[0]) == converter.isRoman(signs[2])) {
            int firstDigit, secondDigit;
            boolean isRoman = converter.isRoman(signs[0]); // Возвращаемые значения True -- Римские, а все что False -- Арабские
            if (isRoman) {
                firstDigit = converter.romanToArab(signs[0]);
                secondDigit = converter.romanToArab(signs[2]);
            } else {
                firstDigit = Integer.parseInt(signs[0]);
                secondDigit = Integer.parseInt(signs[2]);
            }

            if (firstDigit > 10 || firstDigit < 1 || secondDigit > 10 || secondDigit < 1) {
                throw new IncorrectInputException("Числа должны быть от 1 до 10 включительно!");
            }

            int expression_result = switch (currentOperator) {
                case "+" -> firstDigit + secondDigit;
                case "-" -> firstDigit - secondDigit;
                case "/" -> firstDigit / secondDigit;
                default -> firstDigit * secondDigit;
            };

            if (isRoman) {
                if (Integer.compare(expression_result, 0) == 1) {
                    return converter.arabToRoman(expression_result);
                }
                throw new IncorrectInputException("в римской системе нет отрицательных чисел");
            } else {
                return String.valueOf(expression_result);
            }
        } else {
            throw new IncorrectInputException("используются одновременно разные системы счисления");
        }

    }
}


