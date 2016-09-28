package ties456.service;

import ties456.data.BaseData;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class BaseService<T extends BaseData> {
    private AtomicLong idGen = new AtomicLong();
    protected final Map<Long, T> storage = new HashMap<>();
    
    protected Stream<T> stream() {return storage.values().stream();}
    
    protected Stream<T> parallelStream() {return storage.values().parallelStream();}
    
    public List<T> getAll() {
        return storage.values().stream().collect(Collectors.toList());
    }
    
    public List<T> get(int start) {
        if(start < 0) return getAll();
        return stream().skip(start).collect(Collectors.toList());
    }
    
    public List<T> get(int start, int end) {
        if(start < 0 || end < 0) return getAll();
        if(start-end < 0) return getAll();
        
        return stream().skip(start).limit(end-start).collect(Collectors.toList());
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
    
    public List<T> getUpdatedAfter(Date date) {
        return search(item -> item.getUpdated().after(date));
    }
    
    public List<T> getCreatedAfter(Date date) {
        return search(item -> item.getCreated().after(date));
    }
    
    public List<T> getUpdatedBefore(Date date) {
        return search(item -> item.getUpdated().before(date));
    }
    
    public List<T> getCreatedBefore(Date date) {
        return search(item -> item.getCreated().before(date));
    }
    
    public Stream<T> searchStream(Predicate<T> filter) {
        if(filter == null) return parallelStream();
        return parallelStream().filter(filter);
    }
    
    public List<T> search(Predicate<T> filter) {
        if(filter == null) return getAll();
        return parallelStream().filter(filter).collect(Collectors.toList());
    }
    
    public List<T> search(Predicate<T> filter, int start) {
        if(filter == null || start < 0) return getAll();
        return parallelStream().filter(filter).skip(start).collect(Collectors.toList());
    }
    
    public List<T> search(Predicate<T> filter, int start, int end) {
        if(filter == null || start < 0 || end < 0 || end-start < 0) return getAll();
        return parallelStream().filter(filter).skip(start).limit(end-start).collect(Collectors.toList());
    }
}
