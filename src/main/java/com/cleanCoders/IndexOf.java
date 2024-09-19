package com.cleanCoders;

public class IndexOf {
    public static int xth(int x, byte[] src, byte[] segment) {
        int occurrence = -1;

        for (int i = 0; i < src.length; i++) {

            if (src[i] == segment[0]) {
                int counter = 0;
                for (int j = 0; j < segment.length; j++) {
                    if (segment[j] != src[i + j])
                        break;

                    counter++;
                }
                if (counter == segment.length)
                    occurrence++;

                if (x == occurrence)
                    return i;
            }
        }
        return -1;
    }

    public static int first(byte[] src, byte[] segment) {
        return xth(0, src, segment);
    }
}
