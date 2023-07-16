public class CallGraphTest03 {
    public void g(){
        A a = new A();
        a.f();
    }
    public class A{
        public void f(){}
    }
}
class A{
    public void f(){}
}
