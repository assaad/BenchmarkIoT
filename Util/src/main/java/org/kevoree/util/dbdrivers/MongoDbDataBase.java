package org.kevoree.util.dbdrivers;

import com.mongodb.*;

import java.net.UnknownHostException;


public class MongoDbDataBase {

    private DB db = null;
    private MongoClient mongoClient = null;


    public MongoDbDataBase(String host, Integer port, String dbName) throws UnknownHostException {
        mongoClient = new MongoClient(host, port);
        db = mongoClient.getDB(dbName);
        if(db==null){
            System.out.println("null db");
        }
    }

    private static final String KMF_COL = "kmfall";

    private static final String KMF_KEY = "@key";

    private static final String KMF_VAL = "@val";

    public String[] get(String[] keys) {
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
        return result;
    }

    public void clean(){
        DBCollection table = db.getCollection(KMF_COL);
        table.drop();
    }

    public String get(String key) {
        String result = new String();
        DBCollection table = db.getCollection(KMF_COL);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(KMF_KEY, key);


        DBCursor cursor = table.find(searchQuery);
        if (cursor.count() == 1) {
                result = cursor.next().toString();
            }

        String[] tr = result.split(KMF_VAL); //{ "_id" : { "$oid" : "55422d19d4c6452fc5b47bc2"} , "@key" : "0" , "@val" : "42.0"}
     /*   tr= tr[1].split(":");
        tr[1]= tr[1].substring(0, tr[1].indexOf('}')).replace("\"","");*/
        return tr[0];
    }


    public void Arrayput(long[] t, double[] values){
        DBCollection table = db.getCollection(KMF_COL);
        BasicDBObject [] objs = new BasicDBObject[t.length];

        for(int i=0; i<t.length;i++){
            objs[i] = new BasicDBObject();
            objs[i].put(KMF_KEY, String.valueOf(t[i]));
            objs[i].put(KMF_VAL, String.valueOf(values[i]));
        }
        table.insert(objs);
    }

    public void Arrayput(Object[] t, String[] payloads){
        DBCollection table = db.getCollection(KMF_COL);
        BasicDBObject [] objs = new BasicDBObject[t.length];

        for(int i=0; i<t.length;i++){
            objs[i] = new BasicDBObject();
          //  System.out.println("key ex: " + t[i].toString());
           // System.out.println("poly ex: " + payloads[i]);
            objs[i].put(KMF_KEY, t[i].toString());
            objs[i].put(KMF_VAL, payloads[i]);
        }
        table.insert(objs);
    }

    public void put(String[] payloads) {
        DBCollection table = db.getCollection(KMF_COL);
         BasicDBObject obj = new BasicDBObject();
            obj.put(KMF_KEY, payloads[0]);
            obj.put(KMF_VAL, payloads[1]);
       table.insert(obj);
    }


}
