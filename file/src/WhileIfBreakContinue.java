public class WhileIfBreakContinue {

    public static void main(String[]args){
        int a = 3;
        int b = 0;
        while(b<10){
            if(a%3 == 0)
                break;
            System.out.println(a+1);
            if(a%2 == 0)
                continue;
            System.out.println(a+2);
            a++;
            b++;
        }
        System.out.println(b);
    }
}
