/**
 *
 */
package de.sulzer.REST.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * @author Sulzer GmbH
 *
 */
public final class ReceiveResponse {

	public ReceiveResponse() {

	}

	public String getReceiveResponse(CloseableHttpResponse response) throws UnsupportedOperationException, IOException {

    	//
    	String responseJson = "";
    	//
    	InputStream content = null;
    	BufferedReader in = null;

        try {

            HttpEntity entity = response.getEntity();

            // check for null, if no response received
            if (entity == null) {
            	return "";
            }

    		content = entity.getContent();
    		in = new BufferedReader(new InputStreamReader(content));

    		String line;

    		/*
    		 * start debug output; only used when debug necessary
    		 */
//            System.out.println("----------------------------------------");

    		while ((line = in.readLine()) != null) {
    			responseJson = line;
//    			System.out.println(line);
    		}

//            System.out.println(response.getStatusLine());
//            System.out.println("----------------------------------------");
    		/*
    		 * end debug output
    		 */

            EntityUtils.consume(entity);

        } finally {

        	if (null != in) {
        		in.close();
        	}

        	if (null != content) {
        		content.close();
        	}

        	if (response != null) {
        		response.close();
        	}

            response.close();
        }

        //
        return responseJson;

	}

}
