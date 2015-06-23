package hk.code4.newsdiffhk.Util;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class OkHTTPClient {
    private OkHttpClient client = new OkHttpClient();

    public final String get(String path) {
		client.interceptors().add(new StethoInterceptor());
        try {
            Request request = new Request.Builder()
                    .url(path)
                    .build();

            Response response = client.newCall(request).execute();

            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

	public static final MediaType JSON
			= MediaType.parse("application/json; charset=utf-8");

	String post(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

//	public String post(String path, ArrayList<NameValuePair> list, String encode){
//		AndroidHttpClient client = null;
//		try {
//			UrlEncodedFormEntity entity=new UrlEncodedFormEntity(list, encode);
//
//			HttpPost httpPost = new HttpPost(path);
//			httpPost.setEntity(entity);
//
//			client = AndroidHttpClient.newInstance("");
//
//			HttpConnectionParams.setConnectionTimeout(client.getParams(), 3000);
//			HttpConnectionParams.setSoTimeout(client.getParams(), 5000);
//
//			HttpResponse httpResponse = client.execute(httpPost);
//
//			if(httpResponse.getStatusLine().getStatusCode()==200){
//				InputStream inputStream=httpResponse.getEntity().getContent();
//				return toString(inputStream,encode);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(client != null) {
//				client.close();
//			}
//		}
//		return null;
//	}
	
	private String toString(InputStream inputStream, String encode) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len;
		String result="";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data,0,len);
				}
				result=new String(outputStream.toByteArray(),encode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

//	String bowlingJson(String player1, String player2) {
//		return "{'winCondition':'HIGH_SCORE',"
//				+ "'name':'Bowling',"
//				+ "'round':4,"
//				+ "'lastSaved':1367702411696,"
//				+ "'dateStarted':1367702378785,"
//				+ "'players':["
//				+ "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
//				+ "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
//				+ "]}";
//	}

}