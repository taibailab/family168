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

import org.directwebremoting.WebContextFactory;


public class UploadMonitor {
    public UploadInfo getUploadInfo() {
        HttpServletRequest req = WebContextFactory.get()
                                                  .getHttpServletRequest();

        if (req.getSession().getAttribute("uploadInfo") == null) {
            return new UploadInfo();
        }

        return (UploadInfo) req.getSession().getAttribute("uploadInfo");
    }
}
