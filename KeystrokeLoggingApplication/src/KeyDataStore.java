import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nikhilagrawal on 5/4/18.
 */
public class KeyDataStore {

    LinkedHashMap<Integer, TypedKeyObject> store;

    KeyDataStore(){

        this.store = new LinkedHashMap<Integer, TypedKeyObject>();
    }

    public void initialize(){
        this.store = new LinkedHashMap<Integer, TypedKeyObject>();
    }

    public void storeTypedObject(Integer e, TypedKeyObject typedKeyObject){
        this.store.put(e,typedKeyObject);
    }

    public TypedKeyObject getKey(Integer e){
        if(this.store.size()>0){
            return this.store.get(e);
        }
        return null;
    }

    public void removeContainsOfMap(){

        this.store.clear();
    }

    /**
     * {“fields”:[“H.period”,“DD.period.t”,“UD.period.t”,“H.t”,“DD.t.i”,“UD.t.i”,“H.i”,“DD.i.e”,“UD.i.e”,“H.e”,
     * “DD.e.five”,“UD.e.five”,“H.five”,“DD.five.Shift.r”,“UD.five.Shift.r”,“H.Shift.r”,“DD.Shift.r.o”,“UD.Shift.r.o”
     * ,“H.o”,“DD.o.a”,“UD.o.a”,“H.a”,“DD.a.n”,“UD.a.n”,“H.n”,“DD.n.l”,“UD.n.l”,“H.l”,“DD.l.Return”,“UD.l.Return”,
     * “H.Return”],
     * “values”:[
     * [0.175,0.2034,0.0284,0.1227,0.1737,0.051,0.1301,0.146,0.0159,0.1159,0.3732,0.2573,
     * 0.1151,0.5741,0.459,0.155,0.1912,0.0362,0.1143,0.183,0.0687,0.1185,0.1196,0.0011,0.0895,
     * 0.2423,0.1528,0.1243,0.4899,0.3656,0.1206 ]
     * ]}
     */
    public List<Double> process(){
        /**
         * Key1 , Key2
         * Key1.HOLDTIME => Key1 releasetime - Key1 presstime.
         * Key1.DOWNDOWNTIME => KEY2 presstime - Key1 presstime
         * Key1.UPDOWNTIME => KEY2 presstime - key1 releasetime.
         */
        if(this.store.size()<2){
            System.out.print("number of keys pressed should be atleast 2"+this.store.toString());
            return null;
        }
        List<Double> strokes = new ArrayList<Double>();

        TypedKeyObject current = null;
        Long lastKeysHoldTime = null;
        for(Map.Entry<Integer, TypedKeyObject> entry: this.store.entrySet()){
            if(current == null){
                current = entry.getValue();
            }else{
                TypedKeyObject next = entry.getValue();
                Long key1HoldTime = current.releaseTime - current.pressTime;
                Long key1Key2DownTime = next.pressTime - current.pressTime;
                Long key1Key2UPDownTime = next.pressTime - current.releaseTime;
                strokes.add(key1HoldTime/1000.0);
                strokes.add(key1Key2DownTime/1000.0);
                strokes.add(key1Key2UPDownTime/1000.0);



                current = entry.getValue();
                lastKeysHoldTime = current.releaseTime-current.pressTime;
            }
        }
        strokes.add(lastKeysHoldTime/1000.0);

        return strokes;
    }

    public List<Long> processInNano(){
        /**
         * Key1 , Key2
         * Key1.HOLDTIME => Key1 releasetime - Key1 presstime.
         * Key1.DOWNDOWNTIME => KEY2 presstime - Key1 presstime
         * Key1.UPDOWNTIME => KEY2 presstime - key1 releasetime.
         */
        if(this.store.size()<2){
            System.out.print("number of keys pressed should be atleast 2"+this.store.toString());
            return null;
        }
        List<Long> strokes = new ArrayList<Long>();

        TypedKeyObject current = null;
        Long lastKeysHoldTime = null;
        for(Map.Entry<Integer, TypedKeyObject> entry: this.store.entrySet()){
            if(current == null){
                current = entry.getValue();
            }else{
                TypedKeyObject next = entry.getValue();
                Long key1HoldTime = current.releaseTimeinNano - current.pressTimeinNano;
                Long key1Key2DownTime = next.pressTimeinNano - current.pressTimeinNano;
                Long key1Key2UPDownTime = next.pressTimeinNano - current.releaseTimeinNano;
                strokes.add(key1HoldTime);
                strokes.add(key1Key2DownTime);
                strokes.add(key1Key2UPDownTime);
                current = entry.getValue();
                lastKeysHoldTime = current.releaseTimeinNano-current.pressTimeinNano;
            }
        }
        strokes.add(lastKeysHoldTime);
        return strokes;
    }

}

