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

        System.out.println(mergeSort(array, size));
    }

    public static long mergeSort(int[] array, int size) {
        if (size < 2) {
            return 0L;
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

        // тут прибавляем прибавляем то, что вернулась с предыдущего уровня рекурсии
        long invLeft = mergeSort(left, middle);
        long invRight = mergeSort(right, array.length - middle);
        // и добавляем значение, которое получилось на текущем уровне рекурсии
        long invsMerge = merge(array, left, right);
        return invLeft + invRight + invsMerge;
    }

    private static long merge(int[] array, int[] left, int[] right) {
        int resPos = 0;
        int lPos = 0;
        int rPos = 0;

        long inversions = 0;

        int lLen = left.length;
        int rLen = right.length;

        while (lPos < lLen && rPos < rLen) {
            if (left[lPos] <= right[rPos]) {
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