package deftsoft.iground.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import deftsoft.android.iground.utility.HttpUploader;

public class MyHttpClient {

	HttpURLConnection con = null;
	OutputStream os = null;

	HttpUploader uploader;
	private String delimiter = "--";
	private String boundary = "SwA" + Long.toString(System.currentTimeMillis())
			+ "SwA";

	// public String getResponse(HttpURLConnection cony) {
	// // TODO Auto-generated method stub
	// StringBuffer buffer = new StringBuffer();
	// InputStream is = null;
	// try {
	// is = con.getInputStream();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// byte[] b = new byte[1024];
	// try {
	// while (is.read(b) != -1)
	// buffer.append(new String(b));
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// con.disconnect();
	//
	// return buffer.toString();
	// }
	
//	public void uploadFile(String myImagePath) {
//
//		Bitmap myBitmap = BitmapFactory.decodeFile(myImagePath);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//		double width = myBitmap.getWidth();
//
//		double height = myBitmap.getHeight();
//
//		double ratio = 400 / width;
//
//		int newheight = (int) (ratio * height);
//
//		myBitmap = Bitmap.createScaledBitmap(myBitmap, 400, newheight, true);
//		myBitmap.compress(Bitmap.CompressFormat.JPEG, 95, baos);
//		try {
//			HttpClient client = new HttpClient();
//			client.connectForMultipart();
//			// client.addFormPart("param1", param1);
//			// client.addFormPart("param2", param2);
//			client.addFilePart("img", "logo.png", baos.toByteArray());
//			client.finishMultipart();
//			String data = client.getResponse();
//		} catch (Throwable t) {
//			t.printStackTrace();
//		}
//
//	}

	public void finishMultipart() throws Exception {
		os.write((delimiter + boundary + delimiter + "\r\n").getBytes());
	}

	public String getResponse() throws Exception {
		InputStream is = con.getInputStream();
		byte[] b1 = new byte[1024];
		StringBuffer buffer = new StringBuffer();

		while (is.read(b1) != -1)
			buffer.append(new String(b1));

		con.disconnect();

		return buffer.toString();
	}

	public HttpURLConnection connectForMultipart() throws Exception {
		con = (HttpURLConnection) (new URL("http://deftsoft.info/familytree//iGroundApp/images/upload.php")).openConnection();
		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ boundary);
		con.connect();
		os = con.getOutputStream();
		return con;
	}

	public void addFilePart(String paramName, String fileName, byte[] data)
			throws Exception {
		os.write((delimiter + boundary + "\r\n").getBytes());
		os.write(("Content-Disposition: form-data; name=\"" + paramName
				+ "\"; filename=\"" + fileName + "\"\r\n").getBytes());
		os.write(("Content-Type: application/octet-stream\r\n").getBytes());
		os.write(("Content-Transfer-Encoding: binary\r\n").getBytes());
		os.write("\r\n".getBytes());

		os.write(data);

		os.write("\r\n".getBytes());
	}

	public void addFormPart(String paramName, String value) throws Exception {
		writeParamData(paramName, value);
	}

	private void writeParamData(String paramName, String value)
			throws Exception {
		os.write((delimiter + boundary + "\r\n").getBytes());
		os.write("Content-Type: text/plain\r\n".getBytes());
		os.write(("Content-Disposition: form-data; name=\"" + paramName + "\"\r\n")
				.getBytes());

		os.write(("\r\n" + value + "\r\n").getBytes());

	}

}
