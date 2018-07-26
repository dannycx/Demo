package com.danny.service;

/**
 * Created by danny on 18-7-6.
 */

public interface DownloadListener {
    void onSuccess();
    void onFailed();
    void onProgress(int progress);
    void onPaused();
    void onCancel();
}
