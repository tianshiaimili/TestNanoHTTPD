package com.example.mynanohttpd;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class StreamingServer extends NanoHTTPD {
	public static interface OnRequestListen {
		public abstract InputStream onRequest();

		public abstract void requestDone();
	}

	private OnRequestListen myRequestListen = null;
	private File homeDir;
	private Response streamingResponse = null;

	public StreamingServer(int port, String wwwroot) throws IOException {
		super(port, new File(wwwroot).getAbsoluteFile());
		homeDir = new File(wwwroot);
	}

	public void setOnRequestListen(OnRequestListen orl) {
		myRequestListen = orl;
	}

	public Response serve(String uri, String method, Properties header,
			Properties parms, Properties files) {
		LogUtils.d("serve== " + method + " '" + uri + "' ");

		if (uri.equalsIgnoreCase("/live.flv")) {
			
			Response res = new Response(HTTP_NOTFOUND, MIME_PLAINTEXT,
					"Error 404, file not found......===");
			if (myRequestListen == null) {
				return res;
			} else {

				InputStream ins;
				ins = myRequestListen.onRequest();
				if (ins == null) {
					return res;
				}

				if (streamingResponse == null) {
					Random rnd = new Random();
					String etag = Integer.toHexString(rnd.nextInt());

					res = new Response(HTTP_OK, "video/x-flv", ins);
					res.addHeader("Connection", "Keep-alive");
					res.addHeader("ETag", etag);
					res.isStreaming = true;
					streamingResponse = res;
					LogUtils.d("Starting streaming server");
				}
				return res;
			}
		} else if (uri.contains("?")) {

			String parem = uri.substring(uri.indexOf("?"), uri.length());
			if (parem.equals("test")) {

				return new Response(HTTP_NOTFOUND, MIME_PLAINTEXT,
						"Error 404, file not found.");

			}

		} else if (uri.contains("test")) {

			Response res = new Response(HTTP_NOTFOUND, MIME_PLAINTEXT,
					"Error 404, file not found.");
			if (myRequestListen == null) {
				return res;
			} else {

				InputStream ins;
				ins = myRequestListen.onRequest();
				if (ins == null) {
					return res;
				}

				if (streamingResponse == null) {
					Random rnd = new Random();
					String etag = Integer.toHexString(rnd.nextInt());

					res = new Response(HTTP_OK, "video/x-flv", ins);
					res.addHeader("Connection", "Keep-alive");
					res.addHeader("ETag", etag);
					res.isStreaming = true;
					streamingResponse = res;
					LogUtils.d("Starting streaming server");
				}
				return res;
			}

		} else {
			LogUtils.e("uri  =" + uri);
			LogUtils.e("homeDir  =" + homeDir);
			return serveFile(uri, header, homeDir, true);
		}

		return null;
	}

	public void serveDone(Response r) {
		LogUtils.i("*********serveDone");
		if (r.mimeType.equalsIgnoreCase("video/x-flv")
				&& r == streamingResponse) {
			if (myRequestListen != null) {
				myRequestListen.requestDone();
				streamingResponse = null;
			}
		}
	}

}
