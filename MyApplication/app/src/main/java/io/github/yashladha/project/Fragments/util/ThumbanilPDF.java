package io.github.yashladha.project.Fragments.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ThumbanilPDF {

  private static String TAG = "ThumbanilPDF";
  private static String FOLDER = "/sdcard";
  private final Context context;

  public ThumbanilPDF(Context context) {
    this.context = context;
  }

  public void generateImageFromPdf(Uri pdfUri) {
    int pageNumber = 0;
    Log.d(TAG, "ThumbanilPDF initiated");

    File file = new File(FOLDER);
    File tempFile = new File(pdfUri.getPath());
    File imgFile = new File(file, tempFile.getName().replace(".pdf","") +".png");
    if (imgFile.exists()) {
      Log.d(TAG, "Thumbnail already created");
      return;
    }

    PdfiumCore pdfiumCore = new PdfiumCore(context);
    try {
      ParcelFileDescriptor fd = context.getContentResolver().openFileDescriptor(pdfUri, "r");
      PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
      pdfiumCore.openPage(pdfDocument, pageNumber);
      int width = pdfiumCore.getPageWidth(pdfDocument, pageNumber);
      int height = pdfiumCore.getPageHeight(pdfDocument, pageNumber);
      Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
      pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
      saveImage(bmp, pdfUri);
      Log.d(TAG, fd.toString());
    } catch (Exception e) {
      Log.e(TAG, e.getMessage());
    }
  }

  private void saveImage(Bitmap bitmap, Uri pdfUri) {
    FileOutputStream out = null;
    try {
      File file = new File(FOLDER);
      File tempFile = new File(pdfUri.getPath());
      File imgFile = new File(file, tempFile.getName().replace(".pdf","") +".png");
      out = new FileOutputStream(imgFile);
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
    } catch (Exception e) {
      Log.e(TAG, e.getMessage());
    } finally {
      if (out != null)
        try {
          out.close();
        } catch (IOException e) {
          Log.e(TAG, e.getMessage());
        }
    }
  }

}
