import java.util.Scanner;
public class Test3{
    public static void main(String[]args) {
        Scanner scanner = new Scanner(System.in); // 1
        int n = scanner.nextInt(); // 1
        int a = 0; // S2
        int i = 0; // S3
        while (i <= n) { // S4
            i = i + 1; // S12
            a = a + 1;
            if (i == 2)
                continue;
            a = a - 1;
        }
        System.out.println(a); // S13
    }
}
