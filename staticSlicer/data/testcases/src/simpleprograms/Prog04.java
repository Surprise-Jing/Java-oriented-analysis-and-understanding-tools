public class Prog04 {
    public void func(int a){
        a = 3;
        int b = 2;
        switch(a){
            case 0:a = b+1;break;//把a=b+1改成a=b+2，切8-a的结果不一样。。。。。Set<Node>的contains……好像会对不在其中的
            case 1:b+=2;break;//对象也返回true…………………………？？？？？
            default:a = b+1;break;
        }
        System.out.println(a);
    }
}
