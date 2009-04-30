/* Licence:
 *   Use this however/wherever you like, just don't blame me if it breaks anything.
 *
 * Credit:
 *   If you're nice, you'll leave this bit:
 *
 *   Class by Pierre-Alexandre Losson -- http://www.telio.be/blog
 *   email : plosson@users.sourceforge.net
 */
package com.family168.ajaxupload;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class UploadListener implements OutputStreamListener {
    private static final Log log = LogFactory.getLog(UploadListener.class);
    private HttpServletRequest request;
    private long delay = 0;
    private long startTime = 0;
    private int totalToRead = 0;
    private int totalBytesRead = 0;
    private int totalFiles = -1;

    public UploadListener(HttpServletRequest request, long debugDelay) {
        this.request = request;
        this.delay = debugDelay;
        totalToRead = request.getContentLength();
        this.startTime = System.currentTimeMillis();
    }

    public void bytesRead(final int bytesRead) {
        totalBytesRead = totalBytesRead + bytesRead;
        updateUploadInfo("progress");

        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public void done() {
        updateUploadInfo("done");
    }

    public void error(final String message) {
        updateUploadInfo("error");
    }

    public void start() {
        totalFiles++;
        updateUploadInfo("start");
    }

    private void updateUploadInfo(final String status) {
        long delta = (System.currentTimeMillis() - startTime) / 1000;
        request.getSession()
               .setAttribute("uploadInfo",
            new UploadInfo(totalFiles, totalToRead, totalBytesRead, delta,
                status));
    }
}
