public class slicer {
    public static void main(String[]args){
        int sum = 0;
        int prod = 1;

        int i = 0;
        while(i<10){
            i=i+1;
            if(prod > 100/i){
                i = i - 1;
                break;
            }
            sum = sum+i;
            sum = prod;
            prod = prod*i;

        }
        System.out.println(i);
        System.out.println(sum);
        System.out.println(prod);
    }
}
