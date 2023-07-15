public class WhileIf {
    public int va = 0;
    public int vb = 1;
    public static void main(String[]args){
        int sum = 0;
        int prod = 1;

        if(prod > 0) {
            sum = prod;
        }
        else if (prod == 0) {
            sum = 0;
        }
        else {
            sum = -prod;
        }

        System.out.println(sum);
        System.out.println(prod);

    }

}
