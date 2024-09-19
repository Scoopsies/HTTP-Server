package com.cleanCoders;

public class ArrayUtils {
    public static int indexOfXth(int x, byte[] src, byte[] segment) {
        int occurrence = -1;

        for (int i = 0; i < src.length; i++) {
            if (src[i] == segment[0]) {
                int counter = 0;
                counter = updateCounter(src, segment, i, counter);
                occurrence = updateOccurance(segment, counter, occurrence);

                if (x == occurrence)
                    return i;
            }
        }
        return -1;
    }

    public static int indexOfFirst(byte[] src, byte[] segment) {
        return indexOfXth(0, src, segment);
    }

    public static int countOccurrencesOf(byte[] segment, byte[] src) {
        int occurrence = 0;

        for (int i = 0; i < src.length; i++) {
            if (src[i] == segment[0]) {
                int counter = 0;
                counter = updateCounter(src, segment, i, counter);
                occurrence = updateOccurance(segment, counter, occurrence);
            }
        }
        return occurrence;
    }

    public static byte[] slice(byte[] src, int startIdx, int endIdx) {
        byte[] result = new byte[endIdx - startIdx];
        System.arraycopy(src, startIdx, result, 0, result.length);

        return result;
    }

    private static int updateOccurance(byte[] segment, int counter, int occurrence) {
        if (counter == segment.length)
            occurrence++;
        return occurrence;
    }

    private static int updateCounter(byte[] src, byte[] segment, int i, int counter) {
        for (int j = 0; j < segment.length; j++) {
            if (segment[j] != src[i + j])
                break;

            counter++;
        }
        return counter;
    }
}
