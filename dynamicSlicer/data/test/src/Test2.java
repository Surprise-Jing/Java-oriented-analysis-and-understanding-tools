import java.io.IOException;
import java.util.Scanner;
public class Test2{
    public static void main(String[]args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int a = 0;
        int i = 1;
        while (i <= n) {
            int x = scanner.nextInt();
            int y;
            if (x < 0)
                y = x + 1;
            else
                y = x + 2;
            int z = y + 1;
            if (z > 0)
                a = z;
            i = i + 1;
        }
        System.out.println(a);
    }

}
