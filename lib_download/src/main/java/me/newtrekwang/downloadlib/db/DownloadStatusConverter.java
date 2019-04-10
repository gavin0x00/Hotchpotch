package me.newtrekwang.downloadlib.db;

import org.greenrobot.greendao.converter.PropertyConverter;

import me.newtrekwang.downloadlib.entities.DownloadEntry;

/**
 * @author newtrekWang
 * @fileName DownloadStatusConverter
 * @createDate 2018/7/30 17:58
 * @email 408030208@qq.com
 * @desc 数据库字段Status转换器
 */
public class DownloadStatusConverter  implements PropertyConverter<DownloadEntry.DownloadStatus,Integer>{

    @Override
    public DownloadEntry.DownloadStatus convertToEntityProperty(Integer databaseValue) {
        if (databaseValue == null){
            return null;
        }
        for (DownloadEntry.DownloadStatus status: DownloadEntry.DownloadStatus.values()){
            if (status.intValue == databaseValue.intValue()){
                return status;
            }
        }
        return DownloadEntry.DownloadStatus.idle;
    }

    @Override
    public Integer convertToDatabaseValue(DownloadEntry.DownloadStatus entityProperty) {
        return entityProperty ==null ? null :entityProperty.intValue;
    }
}
