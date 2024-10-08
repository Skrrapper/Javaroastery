public class 重载 {
    public static void main(String[] args) {
        int sum1=add(1,2);
        int sum2=add(2,3,4);
        double sum3=add(2.5,3.1);

        System.out.println(sum1);
        System.out.println(sum2);
        System.out.println(sum3);
    }

    //1
    public static int add(int x,int y){
        return x+y;
    }
    //2
    public static int add(int x,int y,int z){
        return x+y+z;
    }
    //3
    public static double add(double x,double y){
        return x+y;
    }
}

