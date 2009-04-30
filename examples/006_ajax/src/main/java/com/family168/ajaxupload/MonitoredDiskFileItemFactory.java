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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;


public class MonitoredDiskFileItemFactory extends DiskFileItemFactory {
    private OutputStreamListener listener = null;

    public MonitoredDiskFileItemFactory(OutputStreamListener listener) {
        this.listener = listener;
    }

    public MonitoredDiskFileItemFactory(int sizeThreshold,
        File repository, OutputStreamListener listener) {
        super(sizeThreshold, repository);
        this.listener = listener;
    }

    @Override
    public FileItem createItem(final String fieldName,
        final String contentType, final boolean isFormField,
        final String fileName) {
        return new MonitoredDiskFileItem(fieldName, contentType,
            isFormField, fileName, getSizeThreshold(), getRepository(),
            listener);
    }
}
