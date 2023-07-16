public class CallGraphTest02 {
    A a = new A();
    public void g(){
        a.f();
        B.f();
    }
}
class A{
    public void f(){}
}
class B{
    public static void f(){}
}
