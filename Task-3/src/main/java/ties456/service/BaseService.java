package ties456.service;

import ties456.data.BaseData;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class BaseService<T extends BaseData> {
    private AtomicLong idGen = new AtomicLong();
    protected final Map<Long, T> storage = new HashMap<>();
    
    public List<T> getAll() {
        return storage.values().stream().collect(Collectors.toList());
    }
    
    public T getById(long id) {
        return storage.get(id);
    }
    
    public void removeById(long id) {
        storage.remove(id);
    }
    
    public T add(T t) {
        t.setId(idGen.getAndIncrement());
        t.setCreated(new Date());
        t.setUpdated(new Date());
        storage.put(t.getId(), t);
        return t;
    }
    
    public T update(long id, T update) {
        T item = getById(id);
        if(item == null) return null;
        item.updateData(update);
        item.setUpdated(new Date());
        return item;
    }
}
