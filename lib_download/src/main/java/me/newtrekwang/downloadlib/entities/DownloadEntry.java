package me.newtrekwang.downloadlib.entities;

import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

import me.newtrekwang.downloadlib.db.DownloadStatusConverter;

/**
 * @className DownloadEntry
 * @createDate 2018/7/30 17:30
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 下载任务
 *
 */
@Entity
public class DownloadEntry implements Serializable,Cloneable{
    @Transient
    public static final long  serialVersionUID = 42L;
    /**
     * 主键
     */
    @Id
    private String   id;
    /**
     * 文件名，不要后缀
     */
    private String name;
    /**
     * 文件下载链接
     */
    private String url;
    /**
     * 文件完整名
     */
    private String fileName;
    /**
     * 文件下载状态
     */
    @Convert(converter = DownloadStatusConverter.class, columnType = Integer.class)
    private DownloadStatus status = DownloadStatus.idle;
    /**
     * 文件总大小
     */
    private volatile int totalLength;
    /**
     * 文件当前下载长度
     */
    private volatile int currentLength;
    /**
     * 是否支持断点续传
     */
    private boolean isSupportRange;
    /**
     * 线程1，文件第一节，已下载长度
     */
    private volatile int indexZeroRange;
    /**
     * 线程2，文件第二节，已下载长度
     */
    private volatile int indexOneRange;
    /**
     * 线程3，文件第三节，已下载长度
     */
    private volatile int indexTwoRange;
    /**
     * 文件附加String类型属性1
     */
    private String strPropertyOne;
    /**
     * 文件附加String类型属性2
     */
    private String strPropertyTwo;
    /**
     * 文件附加String类型属性3
     */
    private String strPropertyThree;
    /**
     * 文件附加int类型属性1
     */
    private long longPropertyOne;
    /**
     * 文件附加int类型属性2
     */
    private long longPropertyTwo;
    /**
     * 文件附加int类型属性3
     */
    private long longPropertyThree;

    /**
     * 传入必要参数
     * @param url
     * @param name
     * @param fileName
     */
    public DownloadEntry(String url,String  id,String name,String fileName){
        this.url = url;
        this.id = id;
        if (!TextUtils.isEmpty(name)){
            this.name = name;
        }else{
            this.name = url.substring(url.lastIndexOf("/")+1);
        }
        this.fileName = fileName;
    }



    @Generated(hash = 1215366502)
    public DownloadEntry(String id, String name, String url, String fileName,
            DownloadStatus status, int totalLength, int currentLength,
            boolean isSupportRange, int indexZeroRange, int indexOneRange,
            int indexTwoRange, String strPropertyOne, String strPropertyTwo,
            String strPropertyThree, long longPropertyOne, long longPropertyTwo,
            long longPropertyThree) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.fileName = fileName;
        this.status = status;
        this.totalLength = totalLength;
        this.currentLength = currentLength;
        this.isSupportRange = isSupportRange;
        this.indexZeroRange = indexZeroRange;
        this.indexOneRange = indexOneRange;
        this.indexTwoRange = indexTwoRange;
        this.strPropertyOne = strPropertyOne;
        this.strPropertyTwo = strPropertyTwo;
        this.strPropertyThree = strPropertyThree;
        this.longPropertyOne = longPropertyOne;
        this.longPropertyTwo = longPropertyTwo;
        this.longPropertyThree = longPropertyThree;
    }



    @Generated(hash = 1942097257)
    public DownloadEntry() {
    }



    /**
     * 重置下载属性
     */
    public void reset() {
        this.status = DownloadStatus.idle;
        this.currentLength = 0;
        this.indexZeroRange =0;
        this.indexOneRange = 0;
        this.indexTwoRange = 0;
    }


    public enum DownloadStatus{
        idle(0),
        connecting(1),
        waiting(2),
        downloading(3),
        paused(4),
        resumed(5),
        cancelled(6),
        completed(7),
        error(8);

        public final int intValue;

        DownloadStatus(int intValue){
            this.intValue = intValue;
        }

    }

    /**
     * 重写equals
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }

        if (!(obj instanceof DownloadEntry)){
            return false;
        }
        DownloadEntry temp = (DownloadEntry) obj;
        return this.id.equals(temp.getId());
    }

    /**
     * 重写hashCode
     * @return
     */
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    /**
     * copy 属性
     * @param other
     */
    public void copyPropertyValues(DownloadEntry other){
        other.setUrl(this.url);
        other.setStatus(this.status);
        other.setName(this.name);
        other.setFileName(this.fileName);
        other.setTotalLength(this.totalLength);
        other.setCurrentLength(this.currentLength);
        other.setIndexZeroRange(this.indexZeroRange);
        other.setIndexOneRange(this.indexOneRange);
        other.setIndexTwoRange(this.indexTwoRange);
        other.setStrPropertyOne(this.strPropertyOne);
        other.setStrPropertyTwo(this.strPropertyTwo);
        other.setStrPropertyThree(this.strPropertyThree);
        other.setLongPropertyOne(this.longPropertyOne);
        other.setLongPropertyTwo(this.longPropertyTwo);
        other.setLongPropertyThree(this.longPropertyThree);
    }

    public String  getId() {
        return this.id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public synchronized DownloadStatus getStatus() {
        return this.status;
    }

    public synchronized void setStatus(DownloadStatus status) {
        this.status = status;
    }

    public synchronized int getTotalLength() {
        return this.totalLength;
    }

    public synchronized int getCurrentLength() {
        return this.currentLength;
    }

    public boolean isSupportRange() {
        return isSupportRange;
    }

    public void setSupportRange(boolean supportRange) {
        isSupportRange = supportRange;
    }

    public boolean getIsSupportRange() {
        return this.isSupportRange;
    }

    public void setIsSupportRange(boolean isSupportRange) {
        this.isSupportRange = isSupportRange;
    }

    public synchronized void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

   public synchronized void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }

    public  synchronized int getIndexZeroRange() {
        return this.indexZeroRange;
    }

    public synchronized void setIndexZeroRange(int indexZeroRange) {
        this.indexZeroRange = indexZeroRange;
    }

    public synchronized int getIndexOneRange() {
        return this.indexOneRange;
    }

    public synchronized void setIndexOneRange(int indexOneRange) {
        this.indexOneRange = indexOneRange;
    }

    public synchronized int getIndexTwoRange() {
        return this.indexTwoRange;
    }

     public synchronized void setIndexTwoRange(int indexTwoRange) {
        this.indexTwoRange = indexTwoRange;
    }


    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getStrPropertyOne() {
        return this.strPropertyOne;
    }

    public void setStrPropertyOne(String strPropertyOne) {
        this.strPropertyOne = strPropertyOne;
    }

    public String getStrPropertyTwo() {
        return this.strPropertyTwo;
    }

    public void setStrPropertyTwo(String strPropertyTwo) {
        this.strPropertyTwo = strPropertyTwo;
    }

    public String getStrPropertyThree() {
        return this.strPropertyThree;
    }

    public void setStrPropertyThree(String strPropertyThree) {
        this.strPropertyThree = strPropertyThree;
    }
    public long getLongPropertyOne() {
        return this.longPropertyOne;
    }
    public void setLongPropertyOne(long longPropertyOne) {
        this.longPropertyOne = longPropertyOne;
    }
    public long getLongPropertyTwo() {
        return this.longPropertyTwo;
    }
    public void setLongPropertyTwo(long longPropertyTwo) {
        this.longPropertyTwo = longPropertyTwo;
    }
    public long getLongPropertyThree() {
        return this.longPropertyThree;
    }
    public void setLongPropertyThree(long longPropertyThree) {
        this.longPropertyThree = longPropertyThree;
    }

    @Override
    public String toString() {
        return "DownloadEntry{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", status=" + status +
                ", totalLength=" + totalLength +
                ", currentLength=" + currentLength +
                ", isSupportRange=" + isSupportRange +
                ", indexZeroRange=" + indexZeroRange +
                ", indexOneRange=" + indexOneRange +
                ", indexTwoRange=" + indexTwoRange +
                ", strPropertyOne='" + strPropertyOne + '\'' +
                ", strPropertyTwo='" + strPropertyTwo + '\'' +
                ", strPropertyThree='" + strPropertyThree + '\'' +
                ", longPropertyOne=" + longPropertyOne +
                ", longPropertyTwo=" + longPropertyTwo +
                ", longPropertyThree=" + longPropertyThree +
                '}';
    }
}
