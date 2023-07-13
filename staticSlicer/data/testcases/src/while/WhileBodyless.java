public class WhileBodyless {
    public void func01(int a,int b){
        while(a<10){
            while(b<10){

            }
        }
    }
    public void func02(int a,int b){
        while(a<10){
            while(b<10){
                b++;
                if(b==5)continue;
            }
            a++;
        }
    }
    public void func03(int a,int b){
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
