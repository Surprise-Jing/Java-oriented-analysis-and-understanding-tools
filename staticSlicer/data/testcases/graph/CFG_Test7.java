import java.util.Scanner;

public class SwitchDemo {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("输入一个数据:");
        int a = 0;
        int week=sc.nextInt();
        switch (week){
            case 1:
                System.out.println("星期一");
                System.out.println("in case 1" + String.valueOf(a++));
            case 2:
                System.out.println("in case 2" + String.valueOf(a++));
            case 3:
                System.out.println("in case 3" + String.valueOf(a++));
            case 7:
                System.out.println("in case 7" + String.valueOf(a++));
            default:
                System.out.println("非法数据");
                System.out.println("in case default" + String.valueOf(a++));
        }
        System.out.println(a);
    }
}
