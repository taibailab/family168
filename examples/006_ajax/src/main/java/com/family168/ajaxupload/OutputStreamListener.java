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

public interface OutputStreamListener {
    void start();

    void bytesRead(final int bytesRead);

    void error(final String message);

    void done();
}
