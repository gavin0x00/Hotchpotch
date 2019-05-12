package me.newtrekwang.gankio.data.protocal;

public class ItemStatus {
    public static final int ITEM_VIEW_TYPE_GROUPITEM = 0;
    public static final int ITEM_VIEW_TYPE_SUBITEM = 1;

    private int viewType;
    private int groupItemIndex = 0;
    private int subItemIndex = -1;

    public ItemStatus() {
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getGroupItemIndex() {
        return groupItemIndex;
    }

    public void setGroupItemIndex(int groupItemIndex) {
        this.groupItemIndex = groupItemIndex;
    }

    public int getSubItemIndex() {
        return subItemIndex;
    }

    public void setSubItemIndex(int subItemIndex) {
        this.subItemIndex = subItemIndex;
    }
}
