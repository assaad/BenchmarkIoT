package org.kevoree.polynomial.benchmark;

import org.fusesource.leveldbjni.JniDBFactory;
import org.kevoree.modeling.api.*;
import org.kevoree.modeling.api.data.cache.KContentKey;
import org.kevoree.modeling.api.data.cdn.AtomicOperation;
import org.kevoree.modeling.api.data.cdn.KContentDeliveryDriver;
import org.kevoree.modeling.api.data.cdn.KContentPutRequest;
import org.kevoree.modeling.api.data.cdn.MemoryKContentDeliveryDriver;
import org.kevoree.modeling.api.data.manager.KDataManager;
import org.kevoree.modeling.api.msg.KMessage;

/**
 * Created by assaad on 06/05/15.
 */
public class DiskCounter implements KContentDeliveryDriver {

    private MemoryKContentDeliveryDriver mem = new MemoryKContentDeliveryDriver();
    public long counter =0;
    public void atomicGetMutate(KContentKey key, AtomicOperation operation, ThrowableCallback<String> callback) {
        mem.atomicGetMutate(key,operation,callback);

    }

    public void get(KContentKey[] keys, ThrowableCallback<String[]> callback) {
        mem.get(keys,callback);

    }

    public void put(KContentPutRequest request, Callback<Throwable> error) {
        for (int i = 0; i < request.size(); i++) {
            counter+=request.getContent(i).getBytes().length+request.getKey(i).toString().getBytes().length;
        }
        mem.put(request,error);
    }

    public void remove(String[] keys, Callback<Throwable> error) {
        mem.remove(keys,error);

    }

    public void connect(Callback<Throwable> callback) {
        mem.connect(callback);


    }

    public void close(Callback<Throwable> callback) {
        mem.close(callback);

    }

    public void registerListener(long groupId, KObject origin, KEventListener listener) {
        mem.registerListener(groupId,origin,listener);

    }

    public void registerMultiListener(long groupId, KUniverse origin, long[] objects, KEventMultiListener listener) {
        mem.registerMultiListener(groupId,origin,objects,listener);

    }

    public void unregisterGroup(long groupId) {
        mem.unregisterGroup(groupId);

    }

    public void send(KMessage msgs) {
        mem.send(msgs);

    }

    public void setManager(KDataManager manager) {
        mem.setManager(manager);

    }
}
