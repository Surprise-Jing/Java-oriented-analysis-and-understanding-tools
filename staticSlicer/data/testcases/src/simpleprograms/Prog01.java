public class Prog01 {
    public void f(){
        int a;
        int sum_q= 0;
        int product_q = 1;
        for(a=0;a<N;a++){
            sum_q += a;
            product_q *= a;
        }
        System.out.println(sum_q);
        System.out.println(product_q);
    }
}
