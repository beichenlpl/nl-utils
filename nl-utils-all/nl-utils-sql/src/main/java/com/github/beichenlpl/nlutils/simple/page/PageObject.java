package com.github.beichenlpl.nlutils.simple.page;

import java.util.List;
import java.util.Objects;

/**
 * @author lpl
 * @version 1.0
 * @since 2023.12.26
 */
public class PageObject<T> {
    private Long total;

    private List<T> list;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageObject<?> that = (PageObject<?>) o;
        return Objects.equals(total, that.total) && Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(total, list);
    }
}
