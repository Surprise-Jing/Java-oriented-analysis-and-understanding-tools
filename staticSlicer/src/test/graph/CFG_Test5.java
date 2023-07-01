class CFG_Test5{
    public static void main(String[] args) {
        for (int i = 0; i < 10; i += 1) {
            if (i == 9) return i;
            for (int j = 0; j < 10; j += 1) {
                if (j % 2 == 0){
                    System.out.println("%d", j);
                    continue;
                }
            }
        }
    }
}
