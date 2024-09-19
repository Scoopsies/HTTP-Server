package com.cleanCoders.multipart;

import com.cleanCoders.IndexOf;

import java.util.HashMap;

public class Body {
    HashMap<String, Part> partMap = new HashMap<>();


    public Body(byte[] body, byte[] boundary) {
        byte[] partBytes = slice(body, 0, IndexOf.xth(1, body, boundary) + boundary.length);
        Part part = new Part(partBytes, boundary);
        partMap.put(part.getName(), part);
    }

    public Part getPart(String name) {
        return partMap.get(name);
    }

    private byte[] slice(byte[] src, int startIdx, int endIdx) {
        byte[] result = new byte[endIdx - startIdx];
        System.arraycopy(src, startIdx, result, 0, result.length);

        return result;
    }
}
