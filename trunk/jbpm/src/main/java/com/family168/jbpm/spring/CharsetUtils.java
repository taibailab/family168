package com.family168.jbpm.spring;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;


/**
 * 检测文件编码工具.
 * TODO: 此工具类过分关注gpd.xml中的编码，期望配置成通用形式
 *
 * @author Lingo
 */
public class CharsetUtils {
    /** * protected constructor. */
    protected CharsetUtils() {
    }

    /**
     * 从zip文件中检测gpd.xml的编码方式.
     *
     * @param url zip文件的URL
     * @return charset
     * @throws IOException io异常
     */
    public static String getCharsetFromZip(URL url) throws IOException {
        return getCharsetFromZip(new JarInputStream(url.openStream()));
    }

    /**
     * 从jar中直接获得gpd.xml的编码方式.
     *
     * @param jarInputStream jar输入流
     * @return charset
     * @throws IOException io异常
     */
    public static String getCharsetFromZip(JarInputStream jarInputStream)
        throws IOException {
        JarEntry jarEntry = jarInputStream.getNextJarEntry();

        while (jarEntry != null) {
            if (jarEntry.getName().equals("gpd.xml")) {
                return getCharset(jarInputStream);
            }

            jarEntry = jarInputStream.getNextJarEntry();
        }

        return "GBK";
    }

    /**
     * 获得url文件的编码.
     *
     * @param url 文件URL
     * @return charset
     * @throws IOException io异常
     */
    public static String getCharset(URL url) throws IOException {
        return getCharset(url.openStream());
    }

    /**
     * 获得输入流文件的编码.
     *
     * @param is 输入流
     * @return charset
     */
    public static String getCharset(InputStream is) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];

        try {
            boolean checked = false;

            BufferedInputStream bis = new BufferedInputStream(is);
            bis.mark(0);

            int read = bis.read(first3Bytes, 0, 3);

            // System.out.println(Integer.toHexString(first3Bytes[0]));
            // System.out.println(Integer.toHexString(first3Bytes[1]));
            // System.out.println(Integer.toHexString(first3Bytes[2]));
            if (read == -1) {
                return charset;
            }

            if ((first3Bytes[0] == (byte) 0xFF)
                    && (first3Bytes[1] == (byte) 0xFE)) {
                charset = "UTF-16LE";
                checked = true;
            } else if ((first3Bytes[0] == (byte) 0xFE)
                    && (first3Bytes[1] == (byte) 0xFF)) {
                charset = "UTF-16BE";
                checked = true;
            } else if ((first3Bytes[0] == (byte) 0xEF)
                    && (first3Bytes[1] == (byte) 0xBB)
                    && (first3Bytes[2] == (byte) 0xBF)) {
                charset = "UTF-8";
                checked = true;
            }

            // System.out.println(charset);
            // System.out.println(checked);
            bis.reset();

            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0) {
                        break;
                    }

                    if ((0x80 <= read) && (read <= 0xBF)) { //单独出现BF以下的，也算是GBK

                        break;
                    }

                    if ((0xC0 <= read) && (read <= 0xDF)) {
                        read = bis.read();

                        if ((0x80 <= read) && (read <= 0xBF)) { //双字节 (0xC0 - 0xDF) (0x80 - 0xBF),也可能在GB编码内

                            continue;
                        } else {
                            break;
                        }
                    } else if ((0xE0 <= read) && (read <= 0xEF)) { //也有可能出错，但是几率较小
                        read = bis.read();

                        if ((0x80 <= read) && (read <= 0xBF)) {
                            read = bis.read();

                            if ((0x80 <= read) && (read <= 0xBF)) {
                                charset = "UTF-8";

                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }

            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return charset;
    }
}
