public class Prog3 {
    public void func06(int a){
        a = 3;
        int b = 2;
        switch(a){
            case 0:
                a = b+1;
            case 1:
                b += 2;
            default:
                a = b+2;
        }
        System.out.println(a);
    }
}
