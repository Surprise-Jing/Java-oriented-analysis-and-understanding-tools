public class Sequence1{
    //顺序执行的样例
    public func(){
        int a = 0,
                b=1,
                c=2;
        a = a+b;
        b = b+c;
        c = a+b;
        return a+b+c;
    }
}
