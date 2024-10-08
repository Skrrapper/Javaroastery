public class 数组 {
    public static void main(String[] args) {
        int[] arr1;
        arr1 = new int[6];
        System.out.println(arr1.length);
        System.out.println(arr1[0]);
        System.out.println(arr1[1]);
        System.out.println(arr1[2]);
        System.out.println(arr1[arr1.length - 1]);
        System.out.println(" ");
        String[] arr2;
        arr2 = new String[6];
        System.out.println(arr2.length);
        System.out.println(arr2[0]);
        System.out.println(" ");

        double[] arr3;
        arr3 = new double[6];
        System.out.println(arr3.length);
        System.out.println(arr3[0]);
        System.out.println(" ");

        arr1[0] = 1;
        arr2[0] = "a";
        arr3[0] = 1.0;
        System.out.println(arr1[0]);
        System.out.println(arr2[0]);
        System.out.println(arr3[0]);

        int[] arr4 = {1, 2, 3};
        System.out.println(arr4[0]);
        int[] arr5 = new int[]{1, 2, 3};
        System.out.println(arr5[0]);

        arr1 = null;
        System.out.println(arr1[0]);
    }

    //数组基本操作
//1.遍历
    public static void printArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    //2.插入（数组末尾）
    public static void insertArray(int[] arr, int score) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int[] newArr = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        newArr[arr.length] = score;
        arr = newArr;
        return;
    }

    //3.排序
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // If no two elements were swapped by inner loop, then break
            if (!swapped) break;
        }
    }
}

