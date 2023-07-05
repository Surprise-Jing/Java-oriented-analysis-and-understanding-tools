public class CFG_Test2 {
    public static void main(String[] args) {
        int a;
        int b = 2;
        int c = 3;
        int d = 4;
        if(a == b){
            a = b +1 ;
            d = d+1;
        }
        else{
            a = b;
            c = c+2;
        }
        System.out.println(a);
    }
}
