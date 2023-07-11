public class WhileTest {
    public void whileBreak(){
        boolean a = true;
        boolean d = false;
        int b = 0;
        int c = 0;
        while(a){
            b++;
            if(d){
                break;
            }

            c++;
        }
        System.out.println(b);
    }
    public void whileContinue(){
        boolean a =true;
        while(a){
            a = false;
            continue;
        }
    }
}
