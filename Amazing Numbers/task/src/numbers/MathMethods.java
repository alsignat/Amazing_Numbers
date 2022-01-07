package numbers;

import java.util.HashSet;

public class MathMethods {

    public static boolean isBuzz(long num) {
        return num % 7 == 0 || num % 10 == 7;
    }

    public static boolean isDuck(long num) {
        while (num > 0) {
            long last = num % 10;
            if (last == 0) {
                return true;
            }
            num /= 10;
        }
        return false;
    }

    public static boolean isPalindromic(long num) {
        String numStr = Long.toString(num);
        for (int i = 0; i < numStr.length() / 2; i ++) {
            if (numStr.charAt(i) != numStr.charAt(numStr.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isGapful(long num) {
        if (num < 100) return false;
        int lastDigit = (int) (num % 10);
        int firstDigit = (int) (num / (long) Math.pow(10, (int) Math.log10(num)));
        return num % (firstDigit * 10 + lastDigit) == 0;
    }

    public static boolean isSpy(long number) {
        long sum = 0L;
        long product = 1L;
        long num = number;
        while (num > 0) {
            sum += num % 10;
            product *= num % 10;
            num /= 10;
        }
        return sum == product;
    }

    public static boolean isSquare(long number) {
        Double num = Math.sqrt(number);
        return Math.floor(num) == num;
    }

    public static boolean isSunny(long number) {
        return isSquare(number + 1);
    }

    public static boolean isJumping(long num) {
        if (num < 10) return true;
        while (num >= 10) {
            long last = num % 10;
            long adjacent = num / 10 % 10;
            if (Math.abs(last - adjacent) != 1) {
                return false;
            }
            num /= 10;
        }
        return true;
    }

    public static boolean isHappy(long num) {
        if (num == 1) return true;
        HashSet<Long> seen = new HashSet<>();
        long number;
        while (true) {
            number = 0;
            while (num > 0) {
                number += Math.pow(num % 10, 2);
                num /= 10;
            }
            if (number == 1) return true;
            if (seen.contains(number)) {
                return false;
            }
            seen.add(number);
            num = number;
        }
    }

    public static boolean isSad(long num) {
        return !isHappy(num);
    }

    public static boolean isEven(long num) {
        return  num % 2 == 0;
    }

    public static boolean isOdd(long num) {
        return !isEven(num);
    }

    public static void isNatural(long num) throws NumberFormatException {
        if (num <= 0) throw new NumberFormatException();
    }

    public static void isNegative(long num) throws NumberFormatException {
        if (num < 0) throw new NumberFormatException();
    }

}
