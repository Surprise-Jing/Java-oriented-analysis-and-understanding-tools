import java.util.Scanner;
public class Test4 {
    public static void main(String[]args) {
        Scanner scanner = new Scanner(System.in); // 1
        int n = scanner.nextInt(); // 1
        int a = 0;
        int i = 0;
        int x = 0;
        while (i <= n) {
            switch (i) {
                case 1:
                    a = a
                            + 1;
                    break;
                case 2:
                    a = a + 2;
                    break;
                case 3: a = a + 3; break;
                default:
                    a = a + 4;
                    break;
            }

            x++;

            i = i + 1;
        }

        System.out.println(a); // S13
    }
}
