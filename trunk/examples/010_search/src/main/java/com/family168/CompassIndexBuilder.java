package com.family168;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.compass.gps.CompassGps;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.util.Assert;


/**
 * 自动随Spring ApplicationContext启动而重建索引的Builder.
 * 会启动后延时数秒新开线程调用compassGps.index()函数.
 * 默认会在Web应用每次启动时重建索引,可以设置buildIndex属性为false来禁止此功能.
 * 也可以不用本Builder, 编写手动调用compassGps.index()的代码.
 *
 * @author calvin
 */
public class CompassIndexBuilder implements InitializingBean {
    private static Log logger = LogFactory.getLog(CompassIndexBuilder.class);

    // 是否需要建立索引，可被设置为false使本Builder失效.
    private boolean buildIndex = true;

    // 索引操作线程延时启动的时间，单位为秒
    private int lazyTime = 10;

    // Compass封装
    private CompassGps compassGps;

    // 索引线程
    private Thread indexThread = new Thread() {
            @Override
            public void run() {
                try {
                    if (!compassGps.isPerformingIndexOperation()) {
                        Thread.sleep(lazyTime * 1000);

                        logger.info("begin compass index...");

                        long beginTime = System
                            .currentTimeMillis();
                        // 重建索引.
                        // 如果compass实体中定义的索引文件已存在，索引过程中会建立临时索引，
                        // 索引完成后再进行覆盖.
                        compassGps.index();

                        long costTime = System.currentTimeMillis()
                            - beginTime;
                        logger.info("compss index finished.");
                        logger.info("costed " + costTime + " milliseconds");
                    }
                } catch (InterruptedException e) {
                    // simply proceed
                }
            }
        };

    /**
     * 实现<code>InitializingBean</code>接口，在完成注入后调用启动索引线程.
     *
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        if (buildIndex) {
            Assert.notNull(compassGps,
                "CompassIndexBuilder not set CompassGps yet.");
            indexThread.setDaemon(true);
            indexThread.setName("Compass Indexer");
            indexThread.start();
        }
    }

    public void setBuildIndex(boolean buildIndex) {
        this.buildIndex = buildIndex;
    }

    public void setLazyTime(int lazyTime) {
        this.lazyTime = lazyTime;
    }

    public void setCompassGps(CompassGps compassGps) {
        this.compassGps = compassGps;
    }
}
