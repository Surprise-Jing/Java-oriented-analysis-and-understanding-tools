public class WhileIf {
    public void func(int a){
        a = 3;
        int b = 2;
        switch(a){
            case 0:a = b+1;break;
            case 1:b+=2;break;
            default:a = b+1;break;
        }
        System.out.println(a);
    }

}
