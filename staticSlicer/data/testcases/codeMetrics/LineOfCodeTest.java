public class LineOfCodeTest{
    public int method1(){//line=11
        int a = 0;
        int b = 1;
        int c = 2;
        //comment
        a = b+c;
        for (int i =0;
        i<4;i++){//comment
            a++;
        }
        return a;/*comment
        *comment
        *comment*/
    }
}
