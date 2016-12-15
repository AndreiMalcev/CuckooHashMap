import main.CuckooHashMap;
import org.junit.Test;

import java.util.Random;

public class CuckooHashMapTest {
    @Test
    public void Test(){
        Random random = new Random(1);
        final int length=10;
        double test[] = new double[length];
        CuckooHashMap cuckoo = new CuckooHashMap<Integer, Double>();

        for (int i = 0; i < length; i++) {
            test[i] = random.nextDouble();
            cuckoo.put(i, test[i]);
        }

        for (int i = 0; i < length; i++){
            assert((double)cuckoo.get(i) == test[i]);
            System.out.println(i+")"+cuckoo.get(i));
        }

    }
}
