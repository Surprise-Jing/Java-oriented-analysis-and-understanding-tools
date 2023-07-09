package caseOne;

public class B{
    A a1 = new A();
    C a2 = new C();

    public void func(){

        a1.func();
        a2.func();
    }

}
class  C{
    public void func(){
        System.out.println("C.func()");
    }
}
