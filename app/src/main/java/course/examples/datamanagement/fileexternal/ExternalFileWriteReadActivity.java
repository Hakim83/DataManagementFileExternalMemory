package course.examples.datamanagement.fileexternal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

public class ExternalFileWriteReadActivity extends Activity {
	private final String fileName = "painter.png";
	private String TAG = "ExternalFileWriteReadActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			File outFile = new File(
					getExternalFilesDir(Environment.DIRECTORY_PICTURES),
					fileName);
			
			if (!outFile.exists())
				copyImageToFile(outFile);

			ImageView imageview = findViewById(R.id.image);
			imageview.setImageURI(Uri.parse("file://" + outFile.getAbsolutePath()));
		
		}
	}

	private void copyImageToFile(File outFile) {
		try {

			BufferedOutputStream os = new BufferedOutputStream(
					new FileOutputStream(outFile));

			BufferedInputStream is = new BufferedInputStream(getResources()
					.openRawResource(R.raw.painter));

			byte[] data = new byte[is.available()];
			is.read(data);
			os.write(data);
			is.close();
			os.close();

		} catch (FileNotFoundException e) {
			Log.e(TAG, "FileNotFoundException");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}