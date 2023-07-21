public class LessThanThousand {
    public int f(int a,int para){
        int b = 0;
        while(a<100){
            if(para == 1){
                a = a + 10;
                b = b - 10;
            }
            else if (para == 2){
                a += 4;
                b += 5;
                b *= a;
                break;
            }
            else if( para == 3){
                b = a+para;
                a = a+b;
                continue;
            }
            else
                return a;
        }
        System.out.println(a);
        System.out.println(b);
        return b;
    }
}
