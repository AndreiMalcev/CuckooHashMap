package main;

import java.util.*;

public class CuckooHashMap<K,V>
        {
            Random random = new Random(1);
            int size =  0;
            int h1 = 0;
            int h2 = 0;
            int n = 0;
            Bucket<K,V>[] bucket1;

            public CuckooHashMap() {
                n = 1000000;
                bucket1 = new Bucket[n];
                while (h1 == h2) {
            h1 = ((random.nextInt()) * 100 + 99);
            h2 = ((random.nextInt()) * 100 + 99);
        }
    }

    public int hashF1(K key) {return Math.abs(h1 * key.hashCode() ) % (bucket1.length-2);
    }
    public  int hashF2(K key){
        return  Math.abs(h2 * key.hashCode() ) % (bucket1.length-2);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (size == 0){
            return true;
        }
        else return false;
    }

    public boolean containsKey(Object key) {
        return (keySet().contains(key));
    }

    public boolean containsValue(Object value) {
        return (values().contains(value));
    }

    public V get(K key) {
        if (bucket1[hashF1(key)] != null) {
            if (key.equals(bucket1[hashF1(key)].getKey())) {
                return bucket1[hashF1(key)].getValue();
            }
        }
        else
            if (bucket1[hashF2(key)] != null) {
            if (key.equals(bucket1[hashF1(key)].getKey())) {
                return bucket1[hashF2(key)].getValue();
            }
        }
        if (bucket1[hashF1(key)] == null && bucket1[hashF2(key)] == null)
            return null;

        return  null;
    }

public  V put (K key, V value) {
    int hash1 = hashF1(key);
    int hash2 = hashF2(key);
    Bucket temp = new Bucket(key, value);
    Bucket t = new Bucket(key, value);
    int a = 0;
    boolean b = true;

    while  (b) {
        if (bucket1[hash1] != null && bucket1[hash2] != null) {
            K k1 = bucket1[hash1].getKey();
            K k2 = bucket1[hash2].getKey();
            V v1 = bucket1[hash1].getValue();
            V v2 = bucket1[hash2].getValue();
            if(a % 2 == 0){
                bucket1[hash1] = temp;
                temp = new Bucket(k1,v1);
                hash2 = hashF2(k1);
                a ++;
                if (temp == t) b = false;

            }
            else {
                bucket1[hash2] = temp;
                temp = new Bucket(k2,v2);
                hash1 = hashF2(k2);
                a ++;
                if (temp == t) b= false;
            }
        } else {
            if (bucket1[hash1] == null) {
                bucket1[hash1] = temp;
                b = false;
            }
            else {
                bucket1[hash2] = temp;
                b = false;
            }
            size++;
        }
    }
    if (a !=0 && (bucket1[hash1] == t || bucket1[hash2] == t)){
        CuckooHashMap cuckoo = new CuckooHashMap<K, V>();
        for (int i = 0; i < bucket1.length; i++) {
            if (bucket1[i] != null) {
                K kA = bucket1[i].getKey();
                V vA = bucket1[i].getValue();
                cuckoo.put(kA, vA);
            }
        }
        cuckoo.put(key, value);
    }
    return value;
}

    public V remove(K key) {
        Bucket<K, V> b1 = bucket1[hashF1(key)];
        if (b1 != null) {
            if (key.equals(b1.getKey())) {
                V value = bucket1[hashF1(key)].getValue();
                bucket1[hashF1((K) key)] = null;
                size -= 1;
                return value;
            } else return null;
        }else return null;
    }

    public void putAll(CuckooHashMap m) {
            Set<Bucket> entrySet = m.entrySet();
            for (Object bucket : entrySet) {
                this.put(((Map.Entry<K, V>)bucket).getKey(), (((Map.Entry<K, V>)bucket).getValue()));
            }

        }

    public void clear() {
       bucket1 = new Bucket [10];
        while (h1 != h2){
            h1 =((random.nextInt())*100+99);
            h2 =((random.nextInt())*100+99);
            size = 0;
        }
    }

    public Set<K> keySet() {
        Set<K> kList = new TreeSet<>();
        for (int i = 0; i < bucket1.length; i++) {
            if (bucket1[i] != null) {
                kList.add(bucket1[i].getKey());
            }
        }
        return kList;
    }

    public Collection values() {
        Set<V> kList = new TreeSet<>();
        for (int i = 0; i < bucket1.length; i++) {
            if (bucket1[i] != null) {
                kList.add(bucket1[i].getValue());
            }
        }
        return kList;
    }

    public Set<Bucket> entrySet() {
        Set<Bucket> kList = new TreeSet<>();
        for (int i = 0; i < bucket1.length; i++) {
            if (bucket1[i] != null) {
                kList.add(bucket1[i]);
            }
        }
        return kList;
    }

    public class Bucket<K , V> implements Map.Entry<K,V> {

        private K key;
        private V value;

        public Bucket(K key,V value) {
            this.key = key;
            this.value = value;

        }

        public K getKey() {
            if (this != null)
            return key;
            else return null;
        }

        public V getValue() {
            if (this != null)
                return value;
            else return null;
        }

        public V setValue(V value) {
            this.value = value;
            return value;
        }
        public boolean Empty() {
            if (this == null){
                return true;
            }
            else return false;
        }
    }

}

