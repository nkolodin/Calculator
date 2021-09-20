package com.nikita;

import java.util.*;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            if(scanner.hasNext()){
                String stringFromConsole = scanner.nextLine();
                if(containLetters(stringFromConsole)){
                    System.out.println("Try again");
                }
                else if (divisionByZero(stringFromConsole)){
                    System.out.println("Error");
                }
                else{
                    String[] massiveWithoutSpaces = removeSpaces(stringFromConsole);
                    StringBuilder stringBuilderWithAppendLetters = new StringBuilder();
                    for (String s : massiveWithoutSpaces) {
                        stringBuilderWithAppendLetters.append(s);
                    }
                    String stringAfterMultipleAndDivide = calculate(stringBuilderWithAppendLetters.toString());
                    while(stringAfterMultipleAndDivide.contains("/") | stringAfterMultipleAndDivide.contains("*") | stringAfterMultipleAndDivide.contains("+") | stringAfterMultipleAndDivide.contains("-")  ){
                        stringAfterMultipleAndDivide = calculate(stringAfterMultipleAndDivide);
                    }
                    System.out.println(stringAfterMultipleAndDivide);
                }
            }
        }
    }

    public static boolean containLetters(String s){
        return Pattern.compile("[^0-9+/*-]").matcher(s).find();
    }

    public static String[] removeSpaces(String s){
        return s.replaceAll(" ","").split("");
    }

    public static boolean divisionByZero(String s){
        boolean isTrue = false;
        if(s.contains("/0")){
            isTrue = true;
        }
        return isTrue;
    }

    public static List<String> getLetters(String stringFromConsole){
        char[] chars = stringFromConsole.toCharArray();
        List<String> listOfLetters = new ArrayList<>();
        for (char character : chars) {
            if (!Character.isLetter(character) && !Character.isDigit(character) && !Character.isWhitespace(character) && character != '.') {
                listOfLetters.add(Character.toString(character));
            }
        }
        return listOfLetters;
    }

    public static List<String> getDigits(String s){
        char[] chars = s.toCharArray();
        StringBuilder output = new StringBuilder();
        String endOutput = "";
        int lastCharacter = 0;
        List<String> listOfNumbers = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            if(chars.length == i + 1 && Character.isDigit(chars[i])) {
                for (int m = lastCharacter + 1; m < chars.length; m++) {
                    endOutput = endOutput + chars[m];
                    if(chars.length == m + 1){
                        listOfNumbers.add(endOutput);
                    }
                }
            }else if (Character.isDigit(chars[i]) | chars[i] == '.') {
                output.append(chars[i]);
            } else {
                lastCharacter = i;
                listOfNumbers.add(output.toString());
                output = new StringBuilder();
            }
        }
        return listOfNumbers;
    }

    public static String calculate(String formattedString){
        List<String> numbers = getDigits(formattedString);
        List<String> letters = getLetters(formattedString);
        List<String> lettersAndNumbers = new ArrayList<>();
        String wordAfterCalculating;
        for (int i = 0; i < numbers.size(); i++) {
            lettersAndNumbers.add(numbers.get(i));
            if (i < letters.size()) {
                lettersAndNumbers.add(letters.get(i));
            }
        }
        if (lettersAndNumbers.contains("/") | lettersAndNumbers.contains("*") ){
            wordAfterCalculating = calculateDependingOfTheSign(lettersAndNumbers, "/", "*");
        }
        else {
            wordAfterCalculating = calculateDependingOfTheSign(lettersAndNumbers, "+", "-");
        }
        return wordAfterCalculating;
    }

    public static String calculateDependingOfTheSign(List<String> lettersAndNumbers, String firstCharacter, String secondCharacter){
        StringBuilder stringBuilder = new StringBuilder();
        List<String> bufferArray = new ArrayList<>();
        int i = 0;
        while (i != lettersAndNumbers.size() - 2){
            if(lettersAndNumbers.size() > i+1 && lettersAndNumbers.get(i+1).equals(firstCharacter) | lettersAndNumbers.get(i+1).equals(secondCharacter)){
                double result =  selectSign(lettersAndNumbers.get(i),lettersAndNumbers.get(i+2),lettersAndNumbers.get(i+1));
                bufferArray.add(Double.toString(result));
                if(i + 2 != lettersAndNumbers.size()){
                    for(int n = i + 3; n < lettersAndNumbers.size(); n++){
                        bufferArray.add(lettersAndNumbers.get(n));
                    }
                }
                break;
            }
            else{
                bufferArray.add(lettersAndNumbers.get(i));
            }
            i++;
        }
        for(String character : bufferArray){
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

    public static double selectSign(String firstNumber, String secondNumber, String sign) {
        double result = 0;
        switch (sign.charAt(0)) {
            case '/':
                result = Double.parseDouble(firstNumber) / Double.parseDouble(secondNumber);
                break;
            case '*':
                result = Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber);
                break;
            case '+':
                result = Double.parseDouble(firstNumber) + Double.parseDouble(secondNumber);
                break;
            case '-':
                result = Double.parseDouble(firstNumber) - Double.parseDouble(secondNumber);
                break;
        }
        return result;
    }
}
