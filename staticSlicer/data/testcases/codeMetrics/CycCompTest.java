public class CycCompTest {
    public void f(){
        for (int i = 0;i<9;i++){
            for (int j = 0; j < 9; j++){
                System.out.println(i+j);
                if(j == 6)break;
                while(j<3){
                    j++;
                    System.out.println(j);
                }
            }
        }
    }
}
