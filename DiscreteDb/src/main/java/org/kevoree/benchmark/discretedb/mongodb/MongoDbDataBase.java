package org.kevoree.benchmark.discretedb.mongodb;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MongoDbDataBase {

    private DB db = null;
    private MongoClient mongoClient = null;


    public MongoDbDataBase(String host, Integer port, String dbName) throws UnknownHostException {
        mongoClient = new MongoClient(host, port);
        db = mongoClient.getDB(dbName);
    }

    private static final String KMF_COL = "kmfall";

    private static final String KMF_KEY = "@key";

    private static final String KMF_VAL = "@val";

    public void get(String[] keys) {
        String[] result = new String[keys.length];
        DBCollection table = db.getCollection(KMF_COL);
        for (int i = 0; i < result.length; i++) {
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put(KMF_KEY, keys[i]);
            DBCursor cursor = table.find(searchQuery);
            if (cursor.count() == 1) {
                result[i] = cursor.next().toString();
            }
        }
    }


    public void put(String[] payloads) {
        DBCollection table = db.getCollection(KMF_COL);
         BasicDBObject obj = new BasicDBObject();
            obj.put(KMF_KEY, payloads[0]);
            obj.put(KMF_VAL, payloads[1]);

       table.insert(obj);
    }


}
