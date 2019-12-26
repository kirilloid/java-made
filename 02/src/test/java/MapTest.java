import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ru.mail.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MapTest {
    @Test
    public void basicTest() {
        SimpleMap<String, Integer> map = new SimpleMapImpl<>();
        String key1 = "1";
        String key2 = "2";
        Integer v1 = 1;
        Integer v2 = 2;
        assertEquals(map.get(key1), null);
        assertEquals(map.contains(key1), false);
        map.put(key1, v1);
        assertEquals(map.contains(key1), true);
        assertEquals(map.get(key1), v1);
        assertEquals(map.size(), 1);
        assertEquals(map.keySet(), new HashSet<>(Arrays.asList(key1)));
        assertEquals(map.values(), Arrays.asList(v1));
        assertEquals(map.get(key2), null);
        map.put(key1, v2);
        assertEquals(map.get(key1), v2);
        assertEquals(map.remove(key1), v2);
        assertEquals(map.remove(key1), null);
        assertEquals(map.remove(key2), null);
        assertEquals(map.get(key1), null);
    }

    @Test
    public void stressTest() {
        SimpleMap<String, Integer> map = new SimpleMapImpl<>();
        for (int i = 0; i < 100; i++) {
            String key = Integer.toString(i);
            Integer value = Integer.valueOf(i);
            map.put(key, value);
            assertEquals(map.size(), i+1);
            assertEquals(map.get(key), value);
        }
        for (int i = 99; i >= 0; i--) {
            String key = Integer.toString(i);
            Integer value = Integer.valueOf(i);
            assertEquals(map.remove(key), value);
            assertEquals(map.size(), i);
        }
        for (int i = 0; i < 100; i++) {
            String key = Integer.toString(i);
            Integer value = Integer.valueOf(i);
            map.put(key, value);
            assertEquals(map.size(), i+1);
            assertEquals(map.get(key), value);
        }
    }
}
