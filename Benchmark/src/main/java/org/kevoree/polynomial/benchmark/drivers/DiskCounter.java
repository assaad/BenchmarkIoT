package org.kevoree.polynomial.benchmark.drivers;

import org.fusesource.leveldbjni.JniDBFactory;
import org.kevoree.modeling.KCallback;
import org.kevoree.modeling.KContentKey;
import org.kevoree.modeling.KObject;
import org.kevoree.modeling.KUniverse;
import org.kevoree.modeling.cdn.KContentDeliveryDriver;
import org.kevoree.modeling.cdn.KContentPutRequest;
import org.kevoree.modeling.cdn.KMessageInterceptor;
import org.kevoree.modeling.cdn.impl.MemoryContentDeliveryDriver;
import org.kevoree.modeling.event.KEventListener;
import org.kevoree.modeling.event.KEventMultiListener;
import org.kevoree.modeling.memory.manager.KMemoryManager;
import org.kevoree.modeling.message.KMessage;

/**
 * Created by assaad on 06/05/15.
 */
public class DiskCounter implements KContentDeliveryDriver {
    private MemoryContentDeliveryDriver mem = new MemoryContentDeliveryDriver();
    public long counter = 0;

    public void get(KContentKey[] keys, KCallback<String[]> callback) {
        mem.get(keys, callback);
    }

    public void atomicGetIncrement(KContentKey key, KCallback<Short> cb) {
        mem.atomicGetIncrement(key, cb);
    }

    public void put(KContentPutRequest request, KCallback<Throwable> error) {
        for (int i = 0; i < request.size(); i++){
            counter += request.getContent(i).getBytes().length + request.getKey(i).toString().getBytes().length;}
        mem.put(request, error);
    }

    public void remove(String[] keys, KCallback<Throwable> error) {
        mem.remove(keys, error);

    }

    public void connect(KCallback<Throwable> callback) {
        mem.connect(callback);

    }

    public void close(KCallback<Throwable> callback) {
        mem.close(callback);

    }

    public void registerListener(long groupId, KObject origin, KEventListener listener) {
        mem.registerListener(groupId, origin, listener);

    }

    public void registerMultiListener(long groupId, KUniverse origin, long[] objects, KEventMultiListener listener) {
        mem.registerMultiListener(groupId, origin, objects, listener);
    }

    public void unregisterGroup(long groupId) {
        mem.unregisterGroup(groupId);
    }

    public void send(KMessage msgs) {
        mem.send(msgs);
    }

    public int addMessageInterceptor(KMessageInterceptor interceptor) {
        return mem.addMessageInterceptor(interceptor);

    }

    public void removeMessageInterceptor(int id) {
        mem.removeMessageInterceptor(id);

    }

    public void setManager(KMemoryManager manager) {
        mem.setManager(manager);
    }
}
