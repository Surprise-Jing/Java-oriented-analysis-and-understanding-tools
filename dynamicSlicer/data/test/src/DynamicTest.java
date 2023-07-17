import java.util.Scanner;
public class DynamicTest{
    public static void main(String[]args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int a = 0;
        int i = 0;
        while (i <= n) {
            if( i % 3 == 0 ) {
                a++;
            } else if ( i % 3 == 1) {
                a = a * 2;
            } else {
                a = 1;
            }
            i = i + 1;
        }
        System.out.println(a);
    }
}
