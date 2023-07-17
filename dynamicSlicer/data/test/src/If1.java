public class If1 {
    public void func01(int a,int b){
        int c = 0;
        c++;
        if(a == 0){
            b = 1;
        }
        else if (a == 1){
            b = 2;
        }
        else {
            b = 3;
        }
        System.out.println(c);
    }
    public void func02(int a,int b){
        if(a == 0){
            b = 1;
        }
        else if (a == 1){
            b = 2;
        }
        else {
            b = 3;
        }
        System.out.println(b);
    }
}
