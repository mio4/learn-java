package _01_Algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @param <K> Key——物理页号
 * @param <V> Value——存储的具体值
 */
class Node<K,V>{
    private K key;
    private V value;
    Node<K,V> before;
    Node<K,V> next;

    public Node(){

    }

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

}

/**
 * 需要维护两个数据结构：（1）cacheMap （2）双向链表
 * cacheMap是通过双向链表的变化来保证是否需要删除entry...
 * @param <K>
 * @param <V>
 */
public class LRU<K,V> {
    private final Map<K,V> cacheMap = new HashMap<>();
    private int cacheSize; //最大缓存大小
    private int nodeCount; //节点大小
    private Node<K,V> header; //头节点
    private Node<K,V> tailer; //尾节点

    public LRU(int cacheSize){
        this.cacheSize = cacheSize;
        header = new Node<>();
        header.before = null;

        tailer = new Node<>();
        tailer.next = null;

        header.next = tailer;
        tailer.before = header;
    }

    public void put(K key,V value){
        cacheMap.put(key,value);
        addNode(key,value); //双向链表添加节点
    }

    public V get(K key){
        Node<K,V> node = getNode(key); //获取该节点
        moveToHead(node);  //节点移动到链表头部
        return cacheMap.get(key);
    }

    //辅助函数

    public Node<K,V> getNode(K key){
        Node<K,V> t = header;
        while(t != null){
            if(t.getKey().equals(key)){
                return t;
            }

            t = t.next;
        }
        return null;
    }

    public void moveToHead(Node<K,V> node){
        //如果本来是尾部节点

        //如果本来是头部节点
        if(node.before == null){
            return;
        }

        //
    }

    public void addNode(K key,V value){
        //TODO
    }

}
