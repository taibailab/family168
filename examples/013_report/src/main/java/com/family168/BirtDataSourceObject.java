package com.family168;

import java.util.ArrayList;
import java.util.List;


/**
 * eclipse birt数据源对象.
 *
 * @author efa
 * @author Lingo
 * @since 2007-03-27
 * @version 1.0
 */
public class BirtDataSourceObject {
    /**
     * 结果集.
     */
    private List resultList = new ArrayList();

    /**
     * @return Result List.
     */
    public List getResultList() {
        return resultList;
    }

    /**
     * @param resultListIn Result List.
     */
    public void setResultList(final List resultListIn) {
        resultList = resultListIn;
    }
}
