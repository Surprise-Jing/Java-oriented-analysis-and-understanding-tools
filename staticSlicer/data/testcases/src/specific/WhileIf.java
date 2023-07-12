public class WhileIf {

    public void whileIfContinue2(){
        boolean a = true;
        int i = 0;
        while(a){
            i++;
            if(i!=3){
                continue;
            }
            else{
                System.out.println("else");
            }
            System.out.println("didn't continue");
        }

    }
}
