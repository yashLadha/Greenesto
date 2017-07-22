package io.github.yashladha.project.studentFragments.util;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.shockwave.pdfium.PdfiumCore;

public class ThumbanilPDF {

  private static String TAG = "ThumbanilPDF";
  private final Context context;

  public ThumbanilPDF(Context context) {
    this.context = context;
  }

  public void generateImageFromPdf(Uri pdfUri) {
    int pageNumber = 0;
    Log.d(TAG, "ThumbanilPDF initiated");
    PdfiumCore pdfiumCore = new PdfiumCore(context);
    try {
      ParcelFileDescriptor fd = context.getContentResolver().openFileDescriptor(pdfUri, "r");
      Log.d(TAG, fd.toString());
    } catch (Exception e) {
      Log.e(TAG, e.getMessage());
    }
  }

}
