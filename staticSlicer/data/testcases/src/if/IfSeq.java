public class IfSeq {
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
