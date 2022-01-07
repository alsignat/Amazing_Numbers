package numbers;

import java.util.*;

import static numbers.MathMethods.*;


public class Main {
    public static String ln = System.lineSeparator();
    public static final String ARGLIST =
            "[BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD, EVEN, ODD]";

    public enum Property {
        BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD, EVEN, ODD,
        _BUZZ, _DUCK, _PALINDROMIC, _GAPFUL, _SPY, _SQUARE, _SUNNY, _JUMPING, _HAPPY, _SAD, _EVEN, _ODD
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        printMenu();
        while (running) {
            System.out.println(ln + "Enter a request:");
            String[] request = scanner.nextLine().split("\\s+");
            running = processRequestWithParams(request);
        }
    }

    public static void printMenu () {
        System.out.print(
                "Welcome to Amazing Numbers!" + ln
                        + ln
                        + "Supported requests:" + ln
                        + "- enter a natural number to know its properties;" + ln
                        + "- enter two natural numbers to obtain the properties of the list:" + ln
                        + "  * the first parameter represents a starting number;" + ln
                        + "  * the second parameter shows how many consecutive numbers are to be processed;" + ln
                        + "- two natural numbers and a property to search for;" + ln
                        + "- two natural numbers and two properties to search for;" + ln
                        + "- a property preceded by minus must not be present in numbers;" + ln
                        + "- separate the parameters with one space;" + ln
                        + "- enter 0 to exit." + ln
                        + ln
        );
    }

    public static boolean processRequestWithParams(String[] request) {
        if (request.length == 1) return processOne(request[0]);
        return processWithParams(request[0], request[1], Arrays.copyOfRange(request, 2, request.length));
    }

    private static boolean processOne(String n) {
        if (n.equals("0")) {
            System.out.println("Goodbye!");
            return false;
        }
        try {
            long num = Long.parseLong(n);
            descLong(num);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("The first parameter should be a natural number or zero.");
            return true;
        }
    }

    public static void descLong(long num) throws NumberFormatException {
        if (num < 0) throw new NumberFormatException();
        System.out.print(
                ln +
                        "Properties of " + num + ln
                        + "buzz: " + isBuzz(num) + ln
                        + "duck: " + isDuck(num) + ln
                        + "palindromic: " + isPalindromic(num) + ln
                        + "gapful: " + isGapful(num) + ln
                        + "spy: " + isSpy(num) + ln
                        + "square: " + isSquare(num) + ln
                        + "sunny: " + isSunny(num) + ln
                        + "jumping: " + isJumping(num) + ln
                        + "happy: " + isHappy(num) + ln
                        + "sad: " + isSad(num) + ln
                        + "even: " + isEven(num) + ln
                        + "odd: " + isOdd(num) + ln
                        + ln
        );
    }

    private static boolean processWithParams(String n1, String n2, String... params) {
        long num1, num2;
        try {
            num1 = Long.parseLong(n1);
            isNegative(num1);
        } catch (NumberFormatException e) {
            System.out.println("The first parameter should be a natural number or zero.");
            return true;
        }
        try {
            num2 = Long.parseLong(n2);
            isNatural(num2);
        } catch (NumberFormatException e) {
            System.out.println("The second parameter should be a natural number.");
            return true;
        }

        ArrayList<String> wrongParams = new ArrayList<>();
        ArrayList<Property> correctParams = new ArrayList<>();

        for (String s : params) {
            try {
                if (s.charAt(0) == '-') {
                    correctParams.add(Property.valueOf('_' + s.toUpperCase().substring(1)));
                } else {
                    correctParams.add(Property.valueOf(s.toUpperCase()));
                }
            } catch (IllegalArgumentException e) {
                wrongParams.add(s);
            }
        }

        if (wrongParams.size() == 1) {
            System.out.println("The property [" + wrongParams.get(0)
                    + "] is wrong."
                    + System.lineSeparator()
                    + "Available properties: "
                    + ARGLIST
            );
            return true;
        } else if (wrongParams.size() > 1) {
            System.out.println("The properties " + wrongParams
                    + " are wrong."
                    + System.lineSeparator()
                    + "Available properties: "
                    + ARGLIST
            );
            return true;
        }

        try {
            checkParamsMany(correctParams.toArray(new Property[0]));
        } catch (MutuallyExclusiveParam e) {
            return true;
        }

        checkRowNumberWithParams(num1, num2, correctParams.toArray(new Property[0]));
        return true;

    }

    private static void checkParamsMany(Property... params) throws MutuallyExclusiveParam {
        List<Property> paramsList = Arrays.asList(params);
        if (paramsList.contains(Property.EVEN) && paramsList.contains(Property.ODD)) {
            throw new MutuallyExclusiveParam(Property.EVEN, Property.ODD);
        } else if (paramsList.contains(Property.SQUARE) && paramsList.contains(Property.SUNNY)) {
            throw new MutuallyExclusiveParam(Property.SQUARE, Property.SUNNY);
        } else if (paramsList.contains(Property.DUCK) && paramsList.contains(Property.SPY)) {
            throw new MutuallyExclusiveParam(Property.DUCK, Property.SPY);
        } else if (paramsList.contains(Property.HAPPY) && paramsList.contains(Property.SAD)) {
            throw new MutuallyExclusiveParam(Property.HAPPY, Property.SAD);
        } else if (paramsList.contains(Property._EVEN) && paramsList.contains(Property._ODD)) {
            throw new MutuallyExclusiveParam(Property._EVEN, Property._ODD);
        } else if (paramsList.contains(Property._SQUARE) && paramsList.contains(Property._SUNNY)) {
            throw new MutuallyExclusiveParam(Property._SQUARE, Property._SUNNY);
        } else if (paramsList.contains(Property._DUCK) && paramsList.contains(Property._SPY)) {
            throw new MutuallyExclusiveParam(Property._DUCK, Property._SPY);
        } else if (paramsList.contains(Property._HAPPY) && paramsList.contains(Property._SAD)) {
            throw new MutuallyExclusiveParam(Property._HAPPY, Property._SAD);
        }
        for (Property p : paramsList) {
            if (p.name().charAt(0) != '_' && paramsList.contains(Property.valueOf("_" + p.name()))) {
                throw new MutuallyExclusiveParam(p, Property.valueOf("_" + p.name()));
            }
        }
    }

    public static void checkRowNumberWithParams(long num1, long num2, Property... params) {
        int counter = 0;
        for (int i = 0; counter < num2; i++) {
            if (filterWithParams(num1 + i, params)) {
                descShort(num1 + i);
                counter++;
            }
        }
    }

    public static boolean filterWithParams(long num, Property... params) {
        if (params.length == 0) return true;
        for (Property p : params) {
            if (!checkByProperty(num, p)) return false;
        }
        return true;
    }

    public static boolean checkByProperty(long num, Property p) {
        switch (p) {
            case BUZZ:
                return isBuzz(num);
            case DUCK:
                return isDuck(num);
            case PALINDROMIC:
                return isPalindromic(num);
            case GAPFUL:
                return isGapful(num);
            case SPY:
                return isSpy(num);
            case SQUARE:
                return isSquare(num);
            case SUNNY:
                return isSunny(num);
            case JUMPING:
                return isJumping(num);
            case HAPPY:
                return isHappy(num);
            case SAD:
                return isSad(num);
            case EVEN:
                return isEven(num);
            case ODD:
                return isOdd(num);
            case _BUZZ:
                return !isBuzz(num);
            case _DUCK:
                return !isDuck(num);
            case _PALINDROMIC:
                return !isPalindromic(num);
            case _GAPFUL:
                return !isGapful(num);
            case _SPY:
                return !isSpy(num);
            case _SQUARE:
                return !isSquare(num);
            case _SUNNY:
                return !isSunny(num);
            case _JUMPING:
                return !isJumping(num);
            case _HAPPY:
                return !isHappy(num);
            case _SAD:
                return !isSad(num);
            case _EVEN:
                return !isEven(num);
            case _ODD:
                return !isOdd(num);
            default:
                return true;
        }
    }

    public static void descShort(long num) {
        System.out.print(
                ln
                        + (num) + " is "
                        + (isBuzz(num) ? "buzz, " : "")
                        + (isDuck(num) ? "duck, " : "")
                        + (isPalindromic(num) ? "palindromic, " : "")
                        + (isGapful(num) ? "gapful, " : "")
                        + (isSpy(num) ? "spy, " : "")
                        + ((isSquare(num)) ? "square, " : "")
                        + (isSunny(num) ? "sunny, " : "")
                        + (isJumping(num) ? "jumping, " : "")
                        + (isHappy(num) ? "happy, " : "sad, ")
                        + (isEven(num) ? "even" : "odd")

        );

    }

}
