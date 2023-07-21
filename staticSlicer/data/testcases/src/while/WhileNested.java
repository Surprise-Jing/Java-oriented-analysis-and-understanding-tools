public class WhileNested {
    public void func01(int a,int b){
        while(a<10){
            while(b<10){

            }
        }
    }
    public void funcContinue(int a,int b){
        while(a<10){
            while(b<10){
                b++;
                if(b==5)continue;
            }
            a++;
        }
    }
    public void funcBreak(int a,int b){
        while(a<10){
            while(b<10){
                b++;
                if(b==5)break;
            }
            a++;
        }
    }
    public void funcContinue02(int a,int b){
        while(a<10){
            while(b<10){
                b++;
                if(b==5)continue;
                b++;
            }
            a++;
            if(a==5)continue;
            a++;
        }
    }

}
