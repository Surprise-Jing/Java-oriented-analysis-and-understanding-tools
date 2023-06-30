class CFG_Test5{
    public void testFunction(){
        int i = 0 ;
        int j = 0;
        while(i<10){
            if(i==9)break;
            while(j<10){
                if(i*j == 90)continue;
                j++;
            }
            i++;
        }
    }
}
