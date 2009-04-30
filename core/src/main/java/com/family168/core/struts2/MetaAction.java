package com.family168.core.struts2;

import com.family168.core.page.Page;
import com.family168.core.utils.JsonUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 支持AutoGrid的action基类.
 *
 * @author Lingo
 * @param <T> 泛型
 */
public class MetaAction<T> extends ExtAction<T> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * logger. */
    private static Log logger = LogFactory.getLog(MetaAction.class);

    /** * 是否使用AutoGrid形式的数据. */
    protected boolean meta;

    /**
     * 分页查询.
     *
     * @throws Exception 可能抛出任何异常
     */
    @Override
    public void pagedQuery() throws Exception {
        logger.debug(meta);

        if (meta) {
            Page page = entityDao.pagedQuery(start, limit, sort, dir,
                    filterTxt, filterValue);
            MetaData metaData = new MetaData(page);
            configMeta(metaData);
            JsonUtils.write(metaData, out);
        } else {
            Page page = entityDao.pagedQuery(start, limit, sort, dir,
                    filterTxt, filterValue);
            JsonUtils.write(page, out);
        }
    }

    /**
     * 子类的回调函数，配置metaData.
     *
     * @param metaData AutoGrid使用的源数据
     */
    protected void configMeta(MetaData metaData) {
        //
    }

    /** * @param meta boolean. */
    public void setMeta(boolean meta) {
        this.meta = meta;
    }
}
