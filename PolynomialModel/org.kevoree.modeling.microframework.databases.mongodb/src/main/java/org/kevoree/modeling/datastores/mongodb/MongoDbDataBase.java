package org.kevoree.modeling.datastores.mongodb;

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


    public void put(String[][] payloads) {
        DBCollection table = db.getCollection(KMF_COL);
        List<DBObject> objs = new ArrayList<DBObject>();
        for (int i = 0; i < payloads.length; i++) {
            BasicDBObject obj = new BasicDBObject();
            obj.put(KMF_KEY, payloads[i][0]);
            obj.put(KMF_VAL, payloads[i][1]);
        }
        table.aggregate(objs);
    }


}
