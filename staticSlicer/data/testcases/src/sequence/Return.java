public class Return {
    public void func01(){
        return;
    }
    public int func02(){
        return 0;
    }
    public int func03(int a){
        if(a == 0){
            return 1;
        }
        else if (a == 1){
            return 2;
        }
        else {
            return 3;
        }
    }
    public int func04(int a){
        if(a == 0){
            return 1;
        }
        else if (a == 1){

        }
        else if (a == 2){
            return 2;
        }
        else {

        }
        return 4;
    }
    public int func05(int a){
        while(a < 10){
            a++;
            if(a ==5) return a;
        }
        return a;
    }
}
