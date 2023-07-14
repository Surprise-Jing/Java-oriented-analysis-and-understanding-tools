import java.util.Scanner;
public class Test7 {
    public static void main(String[]args) {
        int sum = 0;
        int prod = 1;

        int[] numbers = {1,2,3,4,5};

        for(int i:numbers){
            prod = sum;
            sum += i;

        }
        System.out.println(sum);
        System.out.println(prod);
    }
}
