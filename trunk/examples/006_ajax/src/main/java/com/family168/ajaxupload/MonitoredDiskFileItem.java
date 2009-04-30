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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.fileupload.disk.DiskFileItem;


@SuppressWarnings("serial")
public class MonitoredDiskFileItem extends DiskFileItem {
    private MonitoredOutputStream out = null;
    private OutputStreamListener listener;

    public MonitoredDiskFileItem(String fieldName, String contentType,
        boolean isFormField, String fileName, int sizeThreshold,
        File repository, OutputStreamListener listener) {
        super(fieldName, contentType, isFormField, fileName,
            sizeThreshold, repository);
        this.listener = listener;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        if (out == null) {
            out = new MonitoredOutputStream(super.getOutputStream(),
                    listener);
        }

        return out;
    }
}
