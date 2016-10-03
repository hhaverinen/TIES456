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
    
    /**
     * @return Stream of Storages values
     */
    protected Stream<T> stream() {
        return storage.values().stream();
    }
    
    /**
     * @return parallelStream of Storage values
     */
    protected Stream<T> parallelStream() {
        return storage.values().parallelStream();
    }
    
    /**
     * @return Gets All Items, every call creates new List
     */
    public List<T> getAll() {
        return storage.values().stream().collect(Collectors.toList());
    }
    
    /**
     * Gets Item
     * Throws an exception if item not found
     *
     * @param id item id
     * @return item or null if not found
     */
    public T getById(long id) {
        return storage.get(id);
    }
    
    /**
     * Removes item with given id
     *
     * @param id id
     * @return true if actually removed something, else false
     */
    public boolean removeById(long id) {
        return storage.remove(id) != null;
    }
    
    /**
     * Adds given item to Storage
     *
     * @param t item
     * @return newly added item
     */
    public T add(T t) {
        t.setId(idGen.getAndIncrement());
        t.setCreated(new Date());
        t.setUpdated(new Date());
        storage.put(t.getId(), t);
        return t;
    }
    
    /**
     * Updates given item
     *
     * @param id     item id
     * @param update update to be made
     * @return updated item or null if not found
     */
    public T update(long id, T update) {
        T item = getById(id);
        if (item == null) return null;
        item.updateData(update);
        item.setUpdated(new Date());
        return item;
    }
    
    /*
     * Gets and Searches Below
     */
    
    /**
     * @param start index to start from
     * @return List
     */
    public List<T> getFrom(int start) {
        if (start < 0) return getAll();
        return stream().skip(start).collect(Collectors.toList());
    }
    
    /**
     * @param start index to start
     * @param end   index to stop
     * @return List
     */
    public List<T> getFromTo(int start, int end) {
        if (start < 0 || end < 0) return getAll();
        if (start - end < 0) return getAll();
        
        return stream().skip(start).limit(end - start).collect(Collectors.toList());
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
        if (filter == null) return parallelStream();
        return parallelStream().filter(filter);
    }
    
    public List<T> search(Predicate<T> filter) {
        if (filter == null) return getAll();
        return parallelStream().filter(filter).collect(Collectors.toList());
    }
    
    public List<T> search(Predicate<T> filter, int start) {
        if (filter == null || start < 0) return getAll();
        return parallelStream().filter(filter).skip(start).collect(Collectors.toList());
    }
    
    public List<T> search(Predicate<T> filter, int start, int end) {
        if (filter == null || start < 0 || end < 0 || end - start < 0) return getAll();
        return parallelStream().filter(filter).skip(start).limit(end - start).collect(Collectors.toList());
    }
}
