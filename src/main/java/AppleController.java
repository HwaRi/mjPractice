import java.util.*;
import java.lang.*;

public class AppleController {

    //2.1.1 filter green appless
    public static List<Apple> filterGreenApples(List<Apple> inventory){
        List<Apple> result = new ArrayList<>();

        for(Apple apple: inventory){
            if (Apple.Color.GREEN == apple.getColor()){
                result.add(apple);
            }
        }

        return result;
    }
    //2.1.2 filter apple by color
    public static List<Apple> filterApplesByColor(List<Apple> inventory, Apple.Color color){
        List<Apple> result = new ArrayList<>();

        for(Apple apple: inventory){
            if (apple.getColor().equals(color)){
                result.add(apple);
            }
        }

        return result;
    }
    //2.1.2 filter apple by weight
    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight){
        List<Apple> result = new ArrayList<>();

        for(Apple apple: inventory){
            if (apple.getWeight() > weight){
                result.add(apple);
            }
        }

        return result;
    }

    //2.2 define ApplePredicates
    public static class AppleHeavyWeightPredicate implements ApplePredicate{
        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }
    public static class AppleGreenColorPredicate implements ApplePredicate{
        @Override
        public boolean test(Apple apple) {
            return Apple.Color.GREEN.equals(apple.getColor());
        }
    }
    public static class AppleRedAndHeavyColorPredicate implements ApplePredicate{
        @Override
        public boolean test(Apple apple) {
            return Apple.Color.RED.equals(apple.getColor()) && apple.getWeight() > 150;
        }
    }
    //2.2.1
    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple: inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }
    //Quiz 2-1
    public static class ColorFormatter implements AppleFormatter{
        @Override
        public String print(Apple apple) {
            return apple.getColor() + " Apple";
        }
    }
    public static class ColorWeightFormatter implements AppleFormatter{
        @Override
        public String print(Apple apple) {
            return (apple.getWeight() > 150? "Heavy ":"Light ") + apple.getColor() + " apple";
        }
    }
    public static void prettyPrintApple(List<Apple> inventory, AppleFormatter formatter){
        for(Apple apple: inventory){
            String output = formatter.print(apple);
            System.out.println(output);
        }
    }

    //2.3.4 list 형식으로 추상화
    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for(T e: list){
            if(p.test(e)){
                result.add(e);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        //set test data
        List<Apple> inventory = Arrays.asList(
                new Apple(100, Apple.Color.GREEN),
                new Apple(170, Apple.Color.GREEN),
                new Apple(90, Apple.Color.RED),
                new Apple(160, Apple.Color.RED),
                new Apple(120, Apple.Color.YELLOW),
                new Apple(160, Apple.Color.YELLOW)
                );
        //2.1
        System.out.println(">>>>>>>2.1 filtering by parameter");
        List<Apple> greenApples = filterGreenApples(inventory);
        System.out.println(greenApples);

        //2.2
        System.out.println("\n>>>>>>>2.1 behavior parameterization");
        List<Apple> redAndHeavyApples = filterApples(inventory, new AppleRedAndHeavyColorPredicate());
        System.out.println(redAndHeavyApples);

        //QUIZ 2-1
        System.out.println("\n>>>>>>>QUIZ 2.1");
        prettyPrintApple(inventory, new ColorFormatter());
        prettyPrintApple(inventory, new ColorWeightFormatter());

        //2.3 익명 클래스 활용
        System.out.println("\n>>>>>>>2.3 익명클래스 활용");
        List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return Apple.Color.RED.equals(apple.getColor());
            }
        });
        System.out.println(redApples);

        //2.3.3 람다 표현식 활용
        System.out.println("\n>>>>>>>2.3.3 람다 표현식 활용");
        List<Apple> result = filterApples(inventory, (Apple apple) -> Apple.Color.RED.equals(apple.getColor()));
        System.out.println(result);

        //2.3.4 리스트 형식으로 추상화
        //Apple 객체에 한정하지 않고 필터 사용 가능
        System.out.println("\n>>>>>>>2.3.4 리스트 형식으로 추상화");
        List<Apple> yellowApples = filter(inventory, (Apple apple) -> Apple.Color.YELLOW.equals(apple.getColor()));
        System.out.println(yellowApples);
        List<Integer> numbers = Arrays.asList(1,2,3,4,6,8);
        List<Integer> evenNumbers = filter(numbers, (Integer i) -> i%2 == 0);
        System.out.println(evenNumbers);
        
        //2.4
        System.out.println("\n>>>>>>>2.4 실전 예제");
        //inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
        Thread t = new Thread(()->System.out.println("hello world"));
    }
}
