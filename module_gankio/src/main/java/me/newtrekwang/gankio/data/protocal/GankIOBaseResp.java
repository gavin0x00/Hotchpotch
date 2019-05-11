package me.newtrekwang.gankio.data.protocal;

import java.util.List;

/**
 * @className GankIOBaseResp
 * @createDate 2019/5/11 17:36
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc Gankio基本的响应基本数据格式
 *
 */
public class GankIOBaseResp<T> {
    /**
     * 目录列表
     */
    private List<String>  category;
    /**
     * 是否出错
     */
    private boolean error;
    /**
     * 结果
     */
    private T  results;

    public GankIOBaseResp(List<String> category, boolean error, T results) {
        this.category = category;
        this.error = error;
        this.results = results;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "GankIOBaseResp{" +
                "category=" + category +
                ", error=" + error +
                ", results=" + results +
                '}';
    }
}
