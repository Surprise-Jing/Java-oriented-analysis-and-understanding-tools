public class IfTest {

    public void func02(int a,int b){
        if(a == 0){
            a = 1;
            b = 1;
        }
        else if (a == 1){
            a = b;
        }
        else {
            b = a;
        }
        System.out.println(b);
    }
}
