/**
 *
 */
package com.gpt.service.productDetails.Impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.gpt.service.productDetails.ProductDetailsService;

import org.json.JSONArray;
import org.json.JSONObject;





/**
 * @author Suvarna Latha
 *
 */
public class ProductDetailsServiceImpl implements ProductDetailsService
{

	private final String PRODUCT_DESCRIPTION_URL = "https://api.openai.com/v1/chat/completions";
	private final String PRODUCT_IMAGE_URL = "https://api.openai.com/v1/images/generations";
	private final String TRANSLATION_URL = "https://api.openai.com/v1/completions";
	private final String CONTENTTYPE = "Content-Type";
	private final String JSON = "application/json";
	private final String AUTHORIZATION = "Authorization";
	private final String KEY = "Bearer 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";
	private final String TRANSLATION = "Translate this into 1. Spanish (Colombia), 2. Indonesia, 3. Portuguese,4.French ,5.Russian,6.Chinese,7.Japanese,8.Italy,9.Korean,10.German,11.Spanish,12.Chinese,13.English,14.Hindi,15.Czech,16.Hungarian,17.Polish:";
	private final String TEMPARATURE = "temperature";
	private final String MAX_TOKENS = "max_tokens";
	private final String FREQUENCY_PENALTY = "frequency_penalty";
	private final String PRESENCE_PENALTY = "presence_penalty";
	private final String TOP_P = "top_p";
	private final String PROMPT = "prompt";
	private final String PROMPT_VALUE = "mobile";
	private final String SIZE = "size";
	private final String VALUE = "256x256";
	private final String MODEL_FOR_TRANSLATION = "model";
	private final String MODEL_VALUE = "text-davinci-003";
	private final String MODEL_FOR_PRODUCTDESCRIPTION = "model";
	private final String MODEL_VALUE1 = "gpt-3.5-turbo";
	private final String CONTENT = "content";
	private final String ROLE = "role";
	private final String USER = "user";
	private final String MESSAGES = "messages";
	private final String CHOICES = "choices";
	private final String DATA = "data";
	private final String URL = "url";
	private final String TEXT = "text";
	private final String MESSAGE = "message";


	@Override
	public String getProductDescription(final String ProductName) throws IOException
	{
		String content = null;
		final HttpClient httpClient = HttpClientBuilder.create().build();
		final HttpPost request = new HttpPost(PRODUCT_DESCRIPTION_URL);
		request.addHeader(CONTENTTYPE, JSON);
		request.addHeader(AUTHORIZATION, KEY);
		final JSONObject object = new JSONObject();
		final JSONArray array = new JSONArray();
		final JSONObject object1 = new JSONObject();
		object1.put(ROLE, USER);
		object1.put(CONTENT, ProductName);
		array.put(object1);
		object.put(MESSAGES, array);
		object.put(MODEL_FOR_PRODUCTDESCRIPTION, MODEL_VALUE1);
		final StringEntity params = new StringEntity(object.toString());
		request.setEntity(params);
		final HttpResponse response = httpClient.execute(request);
		final String response1 = EntityUtils.toString(response.getEntity());
		final JSONObject jsonObject = new JSONObject(response1);
		final List<String> list = new ArrayList<String>();
		final JSONArray jsonArray = jsonObject.getJSONArray(CHOICES);
		final JSONObject obj = (JSONObject) jsonArray.get(0);
		final HashMap<Integer, String> obj2 = ((HashMap<Integer, String>) obj.toMap().get(MESSAGE));
		content = obj2.get(CONTENT);
		return content;
	}

	@Override
	public String getProductImages(final String ProductName) throws Exception
	{
		final HttpClient httpClient = HttpClientBuilder.create().build();
		final HttpPost request = new HttpPost(PRODUCT_IMAGE_URL);
		request.addHeader(CONTENTTYPE, JSON);
		request.addHeader(AUTHORIZATION, KEY);
		final JSONObject object = new JSONObject();
		object.put(PROMPT, PROMPT_VALUE);
		object.put("n", 1);
		object.put(SIZE, VALUE);
		final StringEntity params = new StringEntity(object.toString());
		request.setEntity(params);
		final HttpResponse response = httpClient.execute(request);
		final String response1 = EntityUtils.toString(response.getEntity());
		final JSONObject jsonObject = new JSONObject(response1);
		final String url = ((List<HashMap<Integer, String>>) jsonObject.toMap().get(DATA)).get(0).get(URL);
		return url;
	}

	@Override
	public String getTranslatedMessage(final String contentToTranslate) throws Exception
	{
		final HttpClient httpClient = HttpClientBuilder.create().build();
		final HttpPost request = new HttpPost(TRANSLATION_URL);
		request.addHeader(CONTENTTYPE, JSON);
		request.addHeader(AUTHORIZATION, KEY);
		final JSONObject object = new JSONObject();
		object.put(MODEL_FOR_TRANSLATION, MODEL_VALUE);
		object.put(PROMPT, TRANSLATION + contentToTranslate);
		object.put(TEMPARATURE, 0.3);
		object.put(MAX_TOKENS, 4000);
		object.put(TOP_P, 1.0);
		object.put(FREQUENCY_PENALTY, 0.0);
		object.put(PRESENCE_PENALTY, 0.0);
		final StringEntity params = new StringEntity(object.toString());
		request.setEntity(params);
		final HttpResponse response = httpClient.execute(request);
		final int code = response.getStatusLine().getStatusCode();
		if (code == 200)
		{
			final String response1 = EntityUtils.toString(response.getEntity());
			final JSONObject jsonObject = new JSONObject(response1);
			final JSONObject object2 = (JSONObject) jsonObject.getJSONArray(CHOICES).get(0);
			final Object translatedMessage = object2.get(TEXT);
			return translatedMessage.toString();
		}
		else
		{
			return null;
		}

	}


}
