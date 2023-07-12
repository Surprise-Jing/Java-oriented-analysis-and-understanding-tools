public class IfDataFlow {
    public void dataFlow(){
        int a = 0;
        int b = 1;
        if(a==0){
            a = 1;
        }
        else if (a == 1){
            a = 2;
        }
        else if (b == 1){
            a = 3;
        }
        else {

        }
        System.out.println(a);
    }
}
