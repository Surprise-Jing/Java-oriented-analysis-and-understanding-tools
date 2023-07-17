public class WhileIf {

    public void func01(int a,int b){
        a = 3;
        while(b<10){
            a++;
            if(a%2 == 0)
                continue;
            System.out.println(a+1);
            if(a%4 == 0)
                break;
            System.out.println(a+2);
            b++;
        }
        System.out.println(b);
    }
}
