public class SwitchTest {

    public void fun01(int a){
        switch(a){
            case 0:
            case 1:
            default:
        }
    }
    public void fun02(int a){
        switch(a){
            case 0:break;
            case 1:break;
            default:break;
        }
    }
    public void fun03(int a){
        a = 3;
        switch(a){
            case 0:break;
            case 1:break;
            default:break;
        }
    }
    public void fun04(int a){
        a = 3;
        switch(a){
            case 0:break;
            case 1:break;
            default:break;
        }
        System.out.println(a);
    }
    public void func05(int a){
        a = 3;
        switch(a){
            case 0:a++;break;
            case 1:break;
            default:break;
        }
        System.out.println(a);
    }

    public void func06(int a){
        a = 3;
        int b = 2;
        switch(a){
            case 0:b+=1;
            case 1:b+=2;
            default:a++;
        }
        System.out.println(a);
    }
    public void func07(int a){
        a = 3;
        int b = 2;
        switch(a){
            case 0:a +=1;break;
            case 1:a +=2;break;
            default:b +=2;break;
        }
        System.out.println(a);
    }
}
