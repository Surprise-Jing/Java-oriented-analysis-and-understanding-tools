public class CallGraphTest01 {
    public void A(){
        System.out.println("test1");
    }
    public void B(){
        A();
    }
    public void C(){
        B();
        A();
    }
    public void D(){
        C();
    }
}
