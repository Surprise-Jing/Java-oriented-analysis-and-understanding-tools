public class IfWhile{
    public int func01(int a,int b){
        if(a == 0){
            while (b<10){
                b++;
                return b;
            }
        }
        else if(a == 1){
            while (b<20){
                b++;
            }
        }
        return b;
    }
}
