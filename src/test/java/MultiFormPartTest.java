import com.cleanCoders.MultiFormPart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiFormPartTest {
    byte[] boundary = "BOUNDARY".getBytes();

    String body = """
            --BOUNDARY\r
            Content-Disposition: form-data; %s\r
            \r
            %s\r
            BOUNDARY--\r
            """;

    @Test
    void parsesNameFromContentDisposition() {
        byte[] body = this.body.formatted("name=\"text\"", "").getBytes();
        MultiFormPart multiFormPart = new MultiFormPart(body, boundary);
        assertEquals("text", multiFormPart.getName());
    }

    @Test
    void parsesNameHelloFromContentDisposition() {
        byte[] body = this.body.formatted("name=\"hello\"", "").getBytes();
        MultiFormPart multiFormPart = new MultiFormPart(body, boundary);
        assertEquals("hello", multiFormPart.getName());
    }

    @Test
    void parsesOptionalFileNameATxtFromContentDisposition() {
        byte[] body = this.body.formatted("name=\"hello\"; filename=\"a.txt\"", "").getBytes();
        MultiFormPart multiFormPart = new MultiFormPart(body, boundary);
        assertEquals("a.txt", multiFormPart.getFileName());
    }

    @Test
    void parsesOptionalFileNameBTxtFromContentDisposition() {
        byte[] body = this.body.formatted("name=\"hello\"; filename=\"b.txt\"", "").getBytes();
        MultiFormPart multiFormPart = new MultiFormPart(body, boundary);
        assertEquals("b.txt", multiFormPart.getFileName());
    }

    @Test
    void parsesOptionalFileNameBeforeName() {
        byte[] body = this.body.formatted("filename=\"a.txt\"; name=\"hello\"", "").getBytes();
        MultiFormPart multiFormPart = new MultiFormPart(body, boundary);
        assertEquals("a.txt", multiFormPart.getFileName());
        assertEquals("hello", multiFormPart.getName());
    }

    @Test
    void getsTheIndexOfDoubleCRLFEndFromByteArray() {
        byte[] body = this.body.formatted("filename=\"a.txt\"; name=\"hello\"", "content").getBytes();
        MultiFormPart multiFormPart = new MultiFormPart(body, boundary);
        byte[] doubleCRLF = "\r\n\r\n".getBytes();
        byte[] content = "\r\n\r\nContent".getBytes();

        assertEquals(0, multiFormPart.indexOfFirst(content, doubleCRLF));
    }

    @Test
    void getsTheIndexOfDoubleCRLFEndFromByteArrayWithSpace() {
        byte[] body = this.body.formatted("filename=\"a.txt\"; name=\"hello\"", "content").getBytes();
        MultiFormPart multiFormPart = new MultiFormPart(body, boundary);
        byte[] doubleCRLF = "\r\n\r\n".getBytes();
        byte[] content = " \r\n\r\nContent".getBytes();

        assertEquals(1, multiFormPart.indexOfFirst(content, doubleCRLF));
    }

    @Test
    void findsFirstCrlfOfMany() {
        byte[] body = this.body.formatted("filename=\"a.txt\"; name=\"hello\"", "content").getBytes();
        MultiFormPart multiFormPart = new MultiFormPart(body, boundary);
        byte[] doubleCRLF = "\r\n\r\n".getBytes();
        byte[] content = " \r\n\r\nContent\r\n\r\n".getBytes();

        assertEquals(1, multiFormPart.indexOfFirst(content, doubleCRLF));
    }

    @Test
    void parsesContentOfFile() {
        byte[] body = this.body.formatted("filename=\"a.txt\"; name=\"hello\"", "content").getBytes();
        MultiFormPart multiFormPart = new MultiFormPart(body, boundary);
        assertArrayEquals("content".getBytes(), multiFormPart.getContent());
    }

    @Test
    void parsesContentOfFileAgain() {
        byte[] body = this.body.formatted("filename=\"a.txt\"; name=\"hello\"", "different").getBytes();
        MultiFormPart multiFormPart = new MultiFormPart(body, boundary);
        assertArrayEquals("different".getBytes(), multiFormPart.getContent());
    }

    @Test
    void parsesDefaultContentType() {
        byte[] body = this.body.formatted("filename=\"a.txt\"; name=\"hello\"", "different").getBytes();
        MultiFormPart multiFormPart = new MultiFormPart(body, boundary);
        assertEquals("text/plain", multiFormPart.getContentType());
    }

    @Test
    void parsesCustomContentType() {
        byte[] body = """
            --BOUNDARY\r
            Content-Disposition: form-data; name="hello"\r
            Content-Type: text/html\r
            \r
            <h1>hello</h1>\r
            BOUNDARY--\r
            """.getBytes();
        MultiFormPart multiFormPart = new MultiFormPart(body, boundary);
        assertEquals("text/html", multiFormPart.getContentType());
    }
}
