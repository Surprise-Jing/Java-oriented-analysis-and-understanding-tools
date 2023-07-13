public class WhileBreak {
    public void func01(int a,int b){
        while(b<10){
            a++;
            if(a%4 == 0)break;
            System.out.println(a);
            if(a%2 == 0)break;
            System.out.println(a);
        }
    }
}
