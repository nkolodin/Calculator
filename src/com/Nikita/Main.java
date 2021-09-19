package com.Nikita;

import java.util.*;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            if(scanner.hasNext()){
                String stringFromConsole = scanner.nextLine();
                if(findLetters(stringFromConsole)){
                    System.out.println("Try again");
                }
                else{
                    String[] massiveWithoutSpaces = replaceSpaces(stringFromConsole);
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

    /**
     * поиск букв и сомволов всех кроме * / - + .
     *
     * @param stringFromConsole введенная строка
     * @return boolean нашел ли букву
     */
    public static boolean findLetters(String stringFromConsole){
        return Pattern.compile("[^0-9+/*-.]").matcher(stringFromConsole).find();
    }

    /**
     * удаление всех пробелов
     *
     * @param  stringFromConsole введенная строка
     * @return массив из введенной строки без пробелов
     */
    public static String[] replaceSpaces(String stringFromConsole){
        String stringAfterDeletedSpaces = stringFromConsole.replaceAll("\\s","");
        return stringAfterDeletedSpaces.split("");
    }

    /**
     * поиск букв в отформатированной строке
     *
     * @param  stringFromConsole строка из консоли
     * @return list знаков
     */
    public static List<String> searchLetters(String stringFromConsole){
        char[] massChar = stringFromConsole.toCharArray();
        List<String> listOfLetters = new ArrayList<>();
        for (char character : massChar) {
            if (!Character.isLetter(character) && !Character.isDigit(character) && !Character.isWhitespace(character) && character != '.') {
                listOfLetters.add(Character.toString(character));
            }
        }
        return listOfLetters;
    }

    /**
     * поиск чисел в отформатированной строке
     *
     * @param  stringFromConsole строка из консоли
     * @return list цифр
     */
    public static List<String> searchNumbers(String stringFromConsole){
        char[] massCharOfNumbers = stringFromConsole.toCharArray();
        StringBuilder output = new StringBuilder();
        String endOutput = "";
        int lastCharacter = 0;
        List<String> listOfNumbers = new ArrayList<>();
        for (int i = 0; i < massCharOfNumbers.length; i++) {
            if(massCharOfNumbers.length == i + 1 && Character.isDigit(massCharOfNumbers[i])) {
                for (int m = lastCharacter + 1; m < massCharOfNumbers.length; m++) {
                    endOutput = endOutput + massCharOfNumbers[m];
                    if(massCharOfNumbers.length == m + 1){
                        listOfNumbers.add(endOutput);
                    }
                }
            }else if (Character.isDigit(massCharOfNumbers[i]) | massCharOfNumbers[i] == '.') {
                output.append(massCharOfNumbers[i]);
            } else {
                lastCharacter = i;
                listOfNumbers.add(output.toString());
                output = new StringBuilder();
            }
        }
        return listOfNumbers;
    }



    /**
     * подсчет одного действия, сначала ищет умножение или деление, потом прибавить или вычесть
     *
     * @param  formattedString строка без пробелов
     * @return строка с подчитанным одним действием
     */
    public static String calculate(String formattedString){
        List<String> numbers = searchNumbers(formattedString);
        List<String> letters = searchLetters(formattedString);
        List<String> lettersAndNumbers = new ArrayList<>();
        StringBuilder stringBuilder;
        List<String> bufferArray = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            lettersAndNumbers.add(numbers.get(i));
            if (i < letters.size()) {
                lettersAndNumbers.add(letters.get(i));
            }
        }
        if (lettersAndNumbers.contains("/") | lettersAndNumbers.contains("*") ){
            stringBuilder = calculateDependingOfTheSign(lettersAndNumbers, bufferArray, "/", "*");
        }
        else {
            stringBuilder = calculateDependingOfTheSign(lettersAndNumbers, bufferArray, "+", "-");
        }
        return stringBuilder.toString();
    }

    /**
     * подсчет действия в зависимости от группы знаков, сначала ищет умножение или деление, потом прибавить или вычесть
     *
     * @params  lettersAndNumbers отформатированный list, bufferArray буфферный массив для хранения символов во время выполнения, firstCharacter первый символ группы, второй символ группы
     * @return строка после одного подсчитанного действия
     */
    public static StringBuilder calculateDependingOfTheSign(List<String> lettersAndNumbers, List<String> bufferArray,String firstCharacter, String secondCharacter){
        StringBuilder stringBuilder = new StringBuilder();
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
        return stringBuilder;
    }

    /**
     * подсчет действия
     *
     * @params  firstNumber первое число , secondNumber второе число, sign знак для выбора действия
     * @return резуьтат одного действия
     */
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
