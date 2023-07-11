import java.io.IOException;
import java.util.Scanner;
public class Test2{
    public static void main(String[]args) throws IOException{
        Scanner scanner = new Scanner(System.in); // 1
        int n = scanner.nextInt(); // 1
        int a = 0; // S2
        int i = 1; // S3
        while (i <= n) { // S4
            int x = scanner.nextInt(); // S5
            int y;
            if (x < 0) // S6
                y = x + 1; // S7
            else
                y = x + 2; // S8
            int z = y + 1; // S9
            if (z > 0) // S10
                a = z; // S11
            i = i + 1; // S12
        }
        System.out.println(a); // S13
    }

}
