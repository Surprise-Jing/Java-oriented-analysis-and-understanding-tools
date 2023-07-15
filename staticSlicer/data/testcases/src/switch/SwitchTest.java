public class SwitchTest {
    public void func01(int a){
        switch (a){
            case 1: System.out.println("1");
            case 2: System.out.println("2");
            default: System.out.println("default");
        }
        System.out.println("endSwitch");
    }
    public void func02(int a){
        int i = 0;
        switch (a){
            case 1:;break;
            case 2:;break;
            default: System.out.println("default");
        }
        System.out.println(i);
    }


}
