
public class Test1{

    public static void main(String[]args){
        int sum = 0;
        int prod = 1;
        int i =0;
       while(i<10){
            if(prod > 100/i){
                i = i - 1;
                break;
            }
            sum = sum+i;
            sum = prod;
            prod = prod*i;
            i++;
        }
       System.out.println(i);
        System.out.println(sum);
        System.out.println(prod);

    }

}
