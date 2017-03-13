package ImplementationDAO;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Андрей on 13.03.2017.
 */
class SuperExtdTest {


    @org.junit.jupiter.api.Test
    void openFile() throws IOException {
        assertEquals(false, false);
        assertEquals(false, SuperExtd.OpenFile(null));
        assertEquals(true, SuperExtd.OpenFile("D:\\Лаба1.doc"));
    }

}