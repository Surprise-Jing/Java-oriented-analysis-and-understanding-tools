public class CFG_Test3 {
    public static void main(String[] args) {
        int n = 0;
        int i = 1;
        int sum = 0;
        int product = 1;
        while (i < n){
            sum = sum + i;
            product = product * i;
            i = i + 1;
        }
        System.out.println(sum);
        System.out.println(product);
    }
}
