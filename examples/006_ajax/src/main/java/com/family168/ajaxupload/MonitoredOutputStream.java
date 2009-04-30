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

import java.io.IOException;
import java.io.OutputStream;


public class MonitoredOutputStream extends OutputStream {
    private OutputStream out;
    private OutputStreamListener listener;

    public MonitoredOutputStream(OutputStream target,
        OutputStreamListener listener) {
        this.out = target;
        this.listener = listener;
        this.listener.start();
    }

    @Override
    public void write(final byte[] b, final int off, final int len)
        throws IOException {
        out.write(b, off, len);
        listener.bytesRead(len - off);
    }

    @Override
    public void write(final byte[] b) throws IOException {
        out.write(b);
        listener.bytesRead(b.length);
    }

    @Override
    public void write(final int b) throws IOException {
        out.write(b);
        listener.bytesRead(1);
    }

    @Override
    public void close() throws IOException {
        out.close();
        listener.done();
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }
}
