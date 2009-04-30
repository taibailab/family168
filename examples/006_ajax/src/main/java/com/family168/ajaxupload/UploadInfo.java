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

public class UploadInfo {
    private long totalSize = 0;
    private long bytesRead = 0;
    private long elapsedTime = 0;
    private String status = "done";
    private int fileIndex = 0;

    public UploadInfo() {
    }

    public UploadInfo(int fileIndex, long totalSize, long bytesRead,
        long elapsedTime, String status) {
        this.fileIndex = fileIndex;
        this.totalSize = totalSize;
        this.bytesRead = bytesRead;
        this.elapsedTime = elapsedTime;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(final long totalSize) {
        this.totalSize = totalSize;
    }

    public long getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(final long bytesRead) {
        this.bytesRead = bytesRead;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(final long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public boolean isInProgress() {
        return "progress".equals(status) || "start".equals(status);
    }

    public int getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(final int fileIndex) {
        this.fileIndex = fileIndex;
    }
}
