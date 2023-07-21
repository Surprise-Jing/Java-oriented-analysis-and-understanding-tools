import java.util.Scanner;
public class LongLoop {
    public static void main(String[]args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int sum = 0;
        int prod = 1;

        while (n < 10000){
            prod = n + 1;
            prod = sum;
            sum += prod;
            n++;
        }
        System.out.println(sum);
        System.out.println(prod);
    }
}
