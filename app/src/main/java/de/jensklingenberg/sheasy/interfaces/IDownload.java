package de.jensklingenberg.sheasy.interfaces;

/**
 * Created by jens on 11/9/17.
 */

public interface IDownload {
    void downloadProgress(long progress, long fileSize);

    void onDownloadComplete(String localFilePath);

    void onDownloadError();
}
