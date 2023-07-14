import java.util.Scanner;
public class Test5 {
    public static void main(String[]args) {
        int sum = 0;
        int prod = 1;

        for( ; ; prod++){
            sum = sum;

            prod = prod;
            sum++;
            if(sum > 2)
                break;
            sum++;
        }
        System.out.println(sum);
        System.out.println(prod);
    }
}
