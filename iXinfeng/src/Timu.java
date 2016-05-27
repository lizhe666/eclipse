public class Timu {
    public static void main(String[] args) {
        int[] data = { 1, 3, 5, 7, 9, 11, 13, 15 };
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    int sum = data[i] + data[j] + data[k];
                    if (sum == 30) {
                        System.out.println("_______" +data[i] + "  " +data[j] + "  " +data[k]);
                    }
                }
            }
        }
    }
}
