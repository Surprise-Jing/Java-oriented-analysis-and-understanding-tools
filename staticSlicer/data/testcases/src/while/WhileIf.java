public class WhileIf {
    public void whileIfBreak(){
        boolean a = true;
        int i = 0;
        while(a){
            if(i==3)break;
            i++;
        }
    }
    public void whileIfFunction(){
        boolean a = true;
        int i = 0;
        while(a){
            if(i==3)a=false;
            i++;
        }
    }
    public void whileIfContinue(){
        boolean a = true;
        int i = 0;
        while(a){
            i++;
            if(i!=3)continue;
        }
    }

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
