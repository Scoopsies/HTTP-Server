package com.cleanCoders.multipart;

import com.cleanCoders.ArrayUtils;

import java.util.HashMap;

public class Body {
    HashMap<String, Part> partMap = new HashMap<>();

    public Body(byte[] body, byte[] boundary) {
        int partsCount = ArrayUtils.countOccurrencesOf(boundary, body) - 1;

        for (int i = 0; i < partsCount; i++) {
            byte[] partBytes = ArrayUtils.slice(body, ArrayUtils.indexOfXth(i, body, boundary), ArrayUtils.indexOfXth(i + 1, body, boundary) + boundary.length);
            Part part = new Part(partBytes, boundary);
            partMap.put(part.getName(), part);
        }
    }

    public Part getPart(String name) {
        return partMap.get(name);
    }
}