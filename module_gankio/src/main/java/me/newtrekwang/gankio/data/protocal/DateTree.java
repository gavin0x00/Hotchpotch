package me.newtrekwang.gankio.data.protocal;

import java.util.List;

public class DateTree<K,V> {
    private K groupItem;
    private List<V> subItems;

    public DateTree(K groupItem, List<V> subItems) {
        this.groupItem = groupItem;
        this.subItems = subItems;
    }

    public K getGroupItem() {
        return groupItem;
    }

    public void setGroupItem(K groupItem) {
        this.groupItem = groupItem;
    }

    public List<V> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<V> subItems) {
        this.subItems = subItems;
    }

    @Override
    public String toString() {
        return "DateTree{" +
                "groupItem=" + groupItem +
                ", subItems=" + subItems +
                '}';
    }
}
