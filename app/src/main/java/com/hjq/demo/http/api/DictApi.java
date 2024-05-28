package com.hjq.demo.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestPath;

import java.io.Serializable;

/**
 *    desc   : 字典api
 * @author flight
 */
public final class DictApi implements IRequestApi {


    @Override
    public String getApi() {
        return "system/dict/data/type/" + dictType;
    }

    private String dictType;

    public DictApi setDictType(String dictType) {
        this.dictType = dictType;
        return this;
    }

    public String getDictType() {
        return dictType;
    }

    public final static class DictDataInfo  implements Serializable {
        private static final long serialVersionUID = 1L;

        /** 字典编码 */
        private Long dictCode;

        /** 字典排序 */
        private Long dictSort;

        /** 字典标签 */
        private String dictLabel;

        /** 字典键值 */
        private String dictValue;

        /** 字典类型 */
        private String dictType;

        public Long getDictCode() {
            return dictCode;
        }

        public void setDictCode(Long dictCode) {
            this.dictCode = dictCode;
        }

        public Long getDictSort() {
            return dictSort;
        }

        public void setDictSort(Long dictSort) {
            this.dictSort = dictSort;
        }

        public String getDictLabel() {
            return dictLabel;
        }

        public void setDictLabel(String dictLabel) {
            this.dictLabel = dictLabel;
        }

        public String getDictValue() {
            return dictValue;
        }

        public void setDictValue(String dictValue) {
            this.dictValue = dictValue;
        }

        public String getDictType() {
            return dictType;
        }

        public void setDictType(String dictType) {
            this.dictType = dictType;
        }
    }
}