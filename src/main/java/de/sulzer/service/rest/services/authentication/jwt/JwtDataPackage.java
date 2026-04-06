/**
 * 
 */
package de.sulzer.service.rest.services.authentication.jwt;

import java.io.Serializable;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonReader;

/**
 * @author GCH9F5D
 *
 */

public class JwtDataPackage implements Serializable {

	private static final long serialVersionUID = 1807030509574826343L;

	private String expirationPeriod;

	private String header;

	private String payload;

	public JwtDataPackage(String expirationPeriod, String header, String payload) {

		this.expirationPeriod = expirationPeriod;
		this.header = header;
		this.payload = payload;

	}

	/**
	 * @return the expirationPeriod
	 */
	public String getExpirationPeriod() {
		return expirationPeriod;
	}

	/**
	 * @return the header
	 */
	public JsonReader getHeader() {
		return Json.createReader(new StringReader(header));
	}

	/**
	 * @return the payload
	 */
	public JsonReader getPayload() {
		return Json.createReader(new StringReader(payload));
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

}