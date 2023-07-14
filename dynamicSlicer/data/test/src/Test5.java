import java.util.Scanner;
public class Test5 {
    public static void main(String[]args) {
        int sum = 0;
        int prod = 1;

        for(int i = 1; i < 10; i++){
            if(prod > i){
                i = i - 1;
                break;
            }
            sum = sum+i;
            sum = prod;
            prod = prod*i;
        }
        System.out.println(sum);
        System.out.println(prod);
    }
}
