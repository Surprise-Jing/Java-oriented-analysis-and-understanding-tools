import java.util.Scanner;
public class DynamicLabelAndBreakTest{
    public static void main(String[]args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int a = 0;

        int i = 0;

        for(;;) {
            if( n >= 100 ) {
                n--;
                break;
            }
            a = a + 1;
            n = n * 2;
        }
        System.out.println(a);
    }
}
