public void testFunctionFor() {
    int sum = 0;
    int prod = 1;
    // int i = 0;
    for (int i = 0; i < 10; ) {
        i = i + 1;
        if (prod > 100 / i) {
            i = i - 1;
            break;
        }
        sum = sum + i;
        prod = prod * i;
    }
    // System.out.println(i);
    System.out.println(sum);
    System.out.println(prod);
}