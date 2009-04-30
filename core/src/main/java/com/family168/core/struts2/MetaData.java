package com.family168.core.struts2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.family168.core.page.Page;

import net.sf.json.JSONObject;


/**
 * AutoGrid实用的源数据.
 *
 * @author Lingo
 */
public class MetaData {
    /** * metaData. */
    private Map metaData;

    /** * 分页结果. */
    private Page page;

    /** * 字段配置. */
    private List fields;

    /** * @param page 分页结果. */
    public MetaData(Page page) {
        this.page = page;

        metaData = new HashMap();
        fields = new ArrayList();
        metaData.put("root", "result");
        metaData.put("totalProperty", "totalCount");
        metaData.put("fields", fields);
    }

    /**
     * 添加field.
     *
     * @param name 名称
     * @param header 表头
     */
    public void addField(String name, String header) {
        fields.add(new Field(name, header));
    }

    /**
     * 添加field.
     *
     * @param name 名称
     * @param header 表头
     * @param width 宽度
     * @param editor 编辑器
     */
    public void addField(String name, String header, int width,
        String editor) {
        fields.add(new Field(name, header, width, editor));
    }

    /** * @return Map. */
    public Map getMetaData() {
        return metaData;
    }

    /** * @return 数据. */
    public Object getResult() {
        return page.getResult();
    }

    /** * @return 所有记录总数. */
    public long getTotalCount() {
        return page.getTotalCount();
    }

    /**
     * 字段配置.
     */
    public static class Field {
        /** 默认宽度. */
        public static final int DEFAULT_WIDTH = 100;

        /** * 名称. */
        private String name;

        /** * 表头. */
        private String header;

        /** * 宽度. */
        private int width = DEFAULT_WIDTH;

        /** * 编辑器. */
        private JSONObject editor;

        /** * 渲染器. */
        private String renderer;

        /**
         * 构造方法.
         *
         * @param name 名称
         * @param header 表头
         */
        public Field(String name, String header) {
            this.name = name;
            this.header = header;
        }

        /**
         * 构造方法.
         *
         * @param name 名称
         * @param header 表头
         * @param width 宽度
         * @param editor 编辑器
         */
        public Field(String name, String header, int width, String editor) {
            this(name, header);

            if (width > 0) {
                this.width = width;
            }

            if (editor != null) {
                this.editor = JSONObject.fromObject(editor);
            }
        }

        /**
         * 构造方法.
         *
         * @param name 名称
         * @param header 表头
         * @param width 宽度
         * @param editor 编辑器
         * @param renderer 渲染器
         */
        public Field(String name, String header, int width, String editor,
            String renderer) {
            this(name, header, width, editor);
            this.renderer = renderer;
        }

        /** * @return name. */
        public String getName() {
            return name;
        }

        /** * @return header. */
        public String getHeader() {
            return header;
        }

        /** * @return width. */
        public int getWidth() {
            return width;
        }

        /** * @return editor. */
        public JSONObject getEditor() {
            return editor;
        }

        /** * @return renderer. */
        public String getRenderer() {
            return renderer;
        }
    }
}
