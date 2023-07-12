public class Sequence1{
    //顺序执行的样例
    public void func(){
        int a = 0,
                b=1,
                c=2;
        a = a+b;
        b = b+c;
        c = a+b;
        return a+b+c;
    }
    public void func2(){
        int a,b,c,d = 0;
        d =4;
        c =3;
        boolean x = true;
        if(x){
            d = 5;
        }
        else{
            c = 4;
        }
        a =b+ c+d;
    }
}
