package cn.vorbote.vocabbuddy.model.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Page
 * <p>
 * Created at 16:16, 24 May 2023
 *
 * @author Zihlu WANG
 */
public class CustomizedPage<T> implements IPage<T> {

    private List<T> records;

    private long total;

    private long size;

    private long current;

    private List<OrderItem> orders;

    @Override
    public List<OrderItem> orders() {
        return orders;
    }

    public CustomizedPage<T> setOrders(List<OrderItem> orders) {
        this.orders = orders;
        return this;
    }

    public CustomizedPage<T> addOrder(OrderItem orderItem) {
        if (Objects.isNull(orders)) {
            orders = new ArrayList<>();
        }
        orders.add(orderItem);
        return this;
    }

    @Override
    public List<T> getRecords() {
        return records;
    }

    @Override
    public CustomizedPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public CustomizedPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public CustomizedPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return current;
    }

    @Override
    public CustomizedPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }
}
