import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int size = Integer.parseInt(scanner.nextLine());
        int[] array = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        long invs = mergeSort(array, size);
        System.out.println(invs);
    }

    private static long mergeSort(int[] array, int size) {
        long inversions = 0;
        if (size < 2) {
            return inversions;
        }


        int middle = array.length / 2;
        int[] left = new int[middle];
        int[] right = new int[array.length - middle];

        for (int i = 0; i < middle; i++) {
            left[i] = array[i];
        }

        for (int i = middle; i < array.length; i++) {
            right[i - middle] = array[i];
        }

        mergeSort(left, middle);
        mergeSort(right, array.length - middle);
        inversions += merge(array, left, right);
        return inversions;
    }

    private static long merge(int[] array, int[] left, int[] right) {
        int resPos = 0;
        int lPos = 0;
        int rPos = 0;

        long inversions = 0;

        int lLen = left.length;
        int rLen = right.length;

        while (lPos < lLen && rPos < rLen) {
            if (left[lPos] < right[rPos]) {
                array[resPos++] = left[lPos++];
            } else {
                array[resPos++] = right[rPos++];

                // вот тут я считаю инверсии
                // я знаю что у меня массивы правый и левый отсортированы
                // значит, если в левом массиве элемент больше чем в правом,
                // кличество инверсий = длина левого массива - индекс последнего элемента в левом массиве,
                // который был записан в результирующий массив
                inversions += (lLen - lPos);
            }
        }

        while (lPos < lLen) {
            array[resPos++] = left[lPos++];
        }

        while (rPos < rLen) {
            array[resPos++] = right[rPos++];
        }
        return inversions;
    }
}