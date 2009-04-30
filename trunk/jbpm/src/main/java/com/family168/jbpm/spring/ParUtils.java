package com.family168.jbpm.spring;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jbpm.file.def.FileDefinition;

import org.jbpm.graph.def.ProcessDefinition;

import org.jbpm.jpdl.xml.JpdlXmlReader;

import org.jbpm.util.IoUtil;

import org.xml.sax.InputSource;


/**
 * par相关的工具集.
 *
 * @author Lingo
 */
public class ParUtils {
    /** * logger. */
    private static Log logger = LogFactory.getLog(ParUtils.class);

    /** * protected constructor. */
    protected ParUtils() {
    }

    /**
     * 从URL中读取par信息，发布流程.
     * TODO: jbpm里的ProcessDefinition.parseParResource()只能读取zip包，希望可以改成读取目录结构
     *
     * @param url URL
     * @return 流程定义
     * @throws Exception 异常
     */
    public static ProcessDefinition parsePar(URL url) throws Exception {
        String encoding = "GBK";

        if ("file".equals(url.getProtocol())) {
            String fileName = url.getFile();

            File file = new File(fileName);

            if (file.isFile()) {
                encoding = CharsetUtils.getCharsetFromZip(url);

                return parseZipInputStream(new FileInputStream(file),
                    encoding);
            } else {
                // TODO: 没判断文件格式下的编码
                return parseDirectory(file);
            }
        } else {
            InputStream is = url.openStream();

            // TODO: 没判断非jar压缩的编码
            return parseZipInputStream(is, encoding);
        }
    }

    /**
     * 从输入流中读取par信息，发布流程.
     *
     * @param is InputStream
     * @param encoding 文件编码
     * @return 流程定义
     * @throws Exception 异常
     */
    public static ProcessDefinition parseZipInputStream(InputStream is,
        String encoding) throws Exception {
        // TODO: 没有关闭is
        JarInputStream jarInputStream = new JarInputStream(is);

        JarEntry jarEntry = jarInputStream.getNextJarEntry();

        Map entries = new HashMap();

        while (jarEntry != null) {
            String entryName = jarEntry.getName();
            byte[] bytes = IoUtil.readBytes(jarInputStream);

            if (bytes != null) {
                if (entryName.equals("gpd.xml")
                        && (!encoding.equals("UTF-8"))) {
                    bytes = new String(bytes, encoding).getBytes("UTF-8");
                }

                // if (entryName.equals("gpd.xml")) {
                //     System.out.println(new String(bytes, "UTF-8"));
                // }
                entries.put(entryName, bytes);
            }

            jarEntry = jarInputStream.getNextJarEntry();
        }

        return createProcessDefinition(entries);
    }

    /**
     * 从文件中读取par信息，发布流程.
     *
     * @param dir 文件目录
     * @return 流程定义
     * @throws Exception 异常
     */
    private static ProcessDefinition parseDirectory(File dir)
        throws Exception {
        Map entries = new HashMap();

        for (File file : dir.listFiles()) {
            String entryName = file.getName();
            byte[] bytes = readBytes(file);

            if (bytes != null) {
                entries.put(entryName, bytes);
            }
        }

        return createProcessDefinition(entries);
    }

    /**
     * 创建流程定义.
     *
     * @param entries 一个string:byte[]组合的map
     * @return 流程定义
     * @throws Exception 异常
     */
    private static ProcessDefinition createProcessDefinition(
        Map<String, byte[]> entries) throws Exception {
        // TODO: 没有关闭is
        // getting the value
        byte[] processBytes = (byte[]) entries.get("processdefinition.xml");

        // creating the JpdlXmlReader
        InputStream processInputStream = new ByteArrayInputStream(processBytes);
        InputSource processInputSource = new InputSource(processInputStream);
        JpdlXmlReader jpdlXmlReader = new JpdlXmlReader(processInputSource,
                null);

        ProcessDefinition processDefinition = jpdlXmlReader
            .readProcessDefinition();

        // close all the streams
        jpdlXmlReader.close();
        processInputStream.close();

        FileDefinition fileDefinition = (FileDefinition) processDefinition
            .getDefinition(FileDefinition.class);

        for (Map.Entry<String, byte[]> entry : entries.entrySet()) {
            String entryName = entry.getKey();
            byte[] bytes = entry.getValue();

            if (fileDefinition == null) {
                fileDefinition = new FileDefinition();
                processDefinition.addDefinition(fileDefinition);
            }

            if (bytes != null) {
                fileDefinition.addFile(entryName, bytes);
            }
        }

        return processDefinition;
    }

    /**
     * 从文件中获得byte[].
     *
     * @param file 文件
     * @return 二进制
     */
    private static byte[] readBytes(File file) {
        byte[] bb = new byte[1024];
        int len = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);

            while ((len = fis.read(bb, 0, 1024)) != -1) {
                baos.write(bb, 0, len);
            }
        } catch (FileNotFoundException ex) {
            logger.error(ex, ex);
        } catch (IOException ex) {
            logger.error(ex, ex);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    logger.error(ex, ex);
                }
            }

            try {
                baos.flush();
                baos.close();
            } catch (IOException ex) {
                logger.error(ex, ex);
            }
        }

        return baos.toByteArray();
    }
}
