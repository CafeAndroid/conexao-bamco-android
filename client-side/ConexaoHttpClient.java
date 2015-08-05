package com.lucasdam.guia.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

/*
 * @author Lucas Marciano
 * @version 1.0 
 */

/*
 * Classe responsável pela conexão com o servidor.
 * Ela não pode ser alterar.
 */
public class ConexaoHttpClient {
	
	//Timeout do servidor de 30 segundos.
	public static final int HTTP_TIMEOUT = 30000;
	
	
	/*
	 * Metodo que executa uma requisição do tipo POST no servidor PHP,
	 * retornando a resposta se deu certou ou não a requisição pedida.
	 * @param url - endereço do servidor.
	 * @param parametrosPost - parametros para serem usados caso de um SELECT ou INSERT.
	 * @return stringBuffer.toString() - resposta do servidor.
	 */
	public static String executaHttpPost(String url,ArrayList<NameValuePair> parametrosPost) {
		BufferedReader bufferedReader = null;
		try {
			HttpParams httpParameters = new BasicHttpParams();  
		    HttpConnectionParams.setConnectionTimeout(httpParameters, HTTP_TIMEOUT);  
		    HttpConnectionParams.setSoTimeout(httpParameters, HTTP_TIMEOUT); 
			
		    DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(parametrosPost));
			httpclient.setParams(httpParameters);
			
			HttpResponse response = httpclient.execute(httppost);
			bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator");//\s quebra de linha
			while((line = bufferedReader.readLine()) != null){
				stringBuffer.append(line+LS);
			}
			bufferedReader.close();
		    return stringBuffer.toString();
		} catch (ClientProtocolException e) {
			Log.e("PHP","Erro ClientProtocolException: " + e.getMessage().toString());
			return null;
		} catch (IOException e) {
			Log.e("PHP","Erro IOException: " + e.getMessage().toString());
			return null;
		}
		
		
	}
	
	/*
	 * Metodo que executa uma requisição do tipo GET no servidor PHP,
	 * retornando a requisição SELECT do banco de dados.
	 * @param url - endereço do servidor.
	 * @return stringBuffer.toString().
	 */
	public static String executaHttpGet(String url) throws URISyntaxException {
		BufferedReader bufferedReader = null;
		try {
			HttpParams httpParameters = new BasicHttpParams();  
		    HttpConnectionParams.setConnectionTimeout(httpParameters, HTTP_TIMEOUT);  
		    HttpConnectionParams.setSoTimeout(httpParameters, HTTP_TIMEOUT); 
			
		    DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setURI(new URI(url));
			httpclient.setParams(httpParameters);
			HttpResponse response = httpclient.execute(httpGet);
			bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator");//\s quebra de linha
			while((line = bufferedReader.readLine()) != null){
				stringBuffer.append(line+LS);
			}
			bufferedReader.close();
			return stringBuffer.toString();
		} catch (ClientProtocolException e) {
			Log.e("PHP","Erro ClientProtocolException: " + e.getMessage().toString());
			return null;
		} catch (IOException e) {
			Log.e("PHP","Erro IOException: " + e.getMessage().toString());
			return null;
		}
		
		
	}
	
}
