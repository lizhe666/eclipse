public class T {
    public static void main(String[] args) {
        byte a = 0x01;
        byte b = 0x02;

        System.out.println(bytesToInt(a,b));

        
        
        
        
        
    }

    /**
     * byte数组中取int数值
     * 
     */
    public static int bytesToInt(byte h, byte l) {
        int value = (int) ((l & 0xFF) | ((h << 8) & 0xFF00));
        return value;
    }

}
