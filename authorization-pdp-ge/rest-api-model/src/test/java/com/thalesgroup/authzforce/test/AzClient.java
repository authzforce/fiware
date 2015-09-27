/**
 * Copyright (C) 2012-2015 Thales Services SAS.
 *
 * This file is part of AuthZForce.
 *
 * AuthZForce is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AuthZForce is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AuthZForce.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.thalesgroup.authzforce.test;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import oasis.names.tc.xacml._3_0.core.schema.wd_17.Attribute;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.AttributeValueType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.Attributes;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.Request;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.Response;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.Result;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.xml.sax.SAXException;

import com.thalesgroup.authzforce.fiware.pdp.rest.api.EndUserDomain;
import com.thalesgroup.authzforce.fiware.pdp.rest.api.EndUserDomainSet;

/**
 * Sample client code to request the Authorization PDP
 * 
 */
public class AzClient
{
	private final static JAXBContext XACML_JAXB_CONTEXT;
	static
	{
		try
		{
			XACML_JAXB_CONTEXT = JAXBContext.newInstance(Request.class, Response.class);
		} catch (JAXBException e)
		{
			throw new RuntimeException("Failed to initialize XACML schema's JAXB context for (un)marshalling Request/Response elements", e);
		}
	}

	private final static Schema XACML_SCHEMA;
	static
	{
		final InputStream xacmlPolicyXsdIn = AzClient.class.getResourceAsStream("/xml.xsd");
		final Source xacmlPolicyXsd = new StreamSource(xacmlPolicyXsdIn);
		final InputStream xacmlCtxXsdIn = AzClient.class.getResourceAsStream("/xacml-core-v3-schema-wd-17.xsd");
		final Source xacmlCtxXsd = new StreamSource(xacmlCtxXsdIn);
		try
		{
			XACML_SCHEMA = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new Source[] { xacmlPolicyXsd, xacmlCtxXsd });
		} catch (Exception e)
		{
			throw new RuntimeException("Failed to load XACML schema for validating Request/Response elements", e);
		} finally
		{
			try
			{
				if (xacmlPolicyXsdIn != null)
				{
					xacmlPolicyXsdIn.close();
				}

				if (xacmlCtxXsdIn != null)
				{
					xacmlCtxXsdIn.close();
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static final Validator XACML_SCHEMA_VALIDATOR = XACML_SCHEMA.newValidator();

	/**
	 * @param args
	 * @throws JAXBException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static void main(String[] args) throws JAXBException, SAXException, IOException
	{
		// For SSL debugging
		System.setProperty("javax.net.debug", "all");

		// final String serviceBaseURL = "https://az.testbed.fi-ware.eu/authzforce";
		final String serviceBaseURL = "http://localhost:6080/authzforce-webapp";
		final String domainId = "f1b60aaa-41cc-11e3-845a-978549de16ec";

		/**
		 * Set the SSL/TLS client parameters, change the configuration in cxfClient.xml, check your keystore/truststore paths in particular.
		 */
		final SpringBusFactory bf = new SpringBusFactory();
		// cxfClient.xml must be on the classpath
		final Bus bus = bf.createBus("/cxfClient.xml");
		BusFactory.setDefaultBus(bus);

		/**
		 * Create the REST (JAX-RS) client
		 */
		final EndUserDomainSet domainsResourceProxy = JAXRSClientFactory.create(serviceBaseURL, EndUserDomainSet.class);

		/**
		 * Request/response logging (for debugging).
		 */
		final ClientConfiguration clientConf = WebClient.getConfig(domainsResourceProxy);
		clientConf.getInInterceptors().add(new LoggingInInterceptor());
		clientConf.getOutInterceptors().add(new LoggingOutInterceptor());

		// Get your domain's resource
		final EndUserDomain myDomain = domainsResourceProxy.getEndUserDomain(domainId);

		/**
		 * Request some authorization decision from my domain's PDP First build the XACML request. First build the XACML request
		 */
		Request req = new Request();

		// Subject
		final Attributes subjectAttributes = new Attributes();
		req.getAttributes().add(subjectAttributes);
		subjectAttributes.setCategory("urn:oasis:names:tc:xacml:1.0:subject-category:access-subject");
		// Subject ID
		final Attribute subjIdAttr = new Attribute();
		subjectAttributes.getAttributes().add(subjIdAttr);
		subjIdAttr.setIssuer("http://issuer.example.com");
		subjIdAttr.setAttributeId("urn:oasis:names:tc:xacml:1.0:subject:subject-id");
		AttributeValueType subjIdAttrVal = new AttributeValueType();
		subjIdAttr.getAttributeValues().add(subjIdAttrVal);
		subjIdAttrVal.setDataType("http://www.w3.org/2001/XMLSchema#string");
		subjIdAttrVal.getContent().add("bs@simpsons.com");

		// Resource
		final Attributes resourceAttributes = new Attributes();
		req.getAttributes().add(resourceAttributes);
		resourceAttributes.setCategory("urn:oasis:names:tc:xacml:3.0:attribute-category:resource");
		// Resource ID
		final Attribute resIdAttr = new Attribute();
		resourceAttributes.getAttributes().add(resIdAttr);
		resIdAttr.setIssuer("http://issuer.example.com");
		resIdAttr.setAttributeId("urn:oasis:names:tc:xacml:1.0:resource:resource-id");
		AttributeValueType resIdAttrVal = new AttributeValueType();
		resIdAttr.getAttributeValues().add(resIdAttrVal);
		resIdAttrVal.setDataType("http://www.w3.org/2001/XMLSchema#string");
		resIdAttrVal.getContent().add("file://example/med/record/patient/BartSimpson");

		// Action
		final Attributes actionAttributes = new Attributes();
		req.getAttributes().add(actionAttributes);
		actionAttributes.setCategory("urn:oasis:names:tc:xacml:3.0:attribute-category:action");
		// Action ID
		final Attribute actIdAttr = new Attribute();
		actionAttributes.getAttributes().add(actIdAttr);
		actIdAttr.setIssuer("http://issuer.example.com");
		actIdAttr.setAttributeId("urn:oasis:names:tc:xacml:1.0:action:action-id");
		AttributeValueType actIdAttrVal = new AttributeValueType();
		actIdAttr.getAttributeValues().add(actIdAttrVal);
		actIdAttrVal.setDataType("http://www.w3.org/2001/XMLSchema#string");
		actIdAttrVal.getContent().add("read");

		// Environment
		final Attributes envAttributes = new Attributes();
		req.getAttributes().add(envAttributes);
		envAttributes.setCategory("urn:oasis:names:tc:xacml:3.0:attribute-category:environment");
		// Environment - current date
		final Attribute envAttr = new Attribute();
		envAttributes.getAttributes().add(envAttr);
		envAttr.setIssuer("http://issuer.example.com");
		envAttr.setAttributeId("urn:oasis:names:tc:xacml:1.0:environment:current-date");
		AttributeValueType envAttrVal = new AttributeValueType();
		envAttr.getAttributeValues().add(envAttrVal);
		envAttrVal.setDataType("http://www.w3.org/2001/XMLSchema#date");
		envAttrVal.getContent().add("2010-01-11");

		// Request validation against schema (a malformed XACML request will be rejected by the
		// service)
		XACML_SCHEMA_VALIDATOR.validate(new JAXBSource(XACML_JAXB_CONTEXT, req));

		// Send the XACML request to PDP
		final Response response = myDomain.getPdp().requestPolicyDecision(req);
		XACML_SCHEMA_VALIDATOR.validate(new JAXBSource(XACML_JAXB_CONTEXT, response));

		for (final Result result : response.getResults())
		{
			System.out.println("Authorization decision: " + result.getDecision() + "; status code: " + result.getStatus().getStatusCode().getValue()
					+ "; status message: " + result.getStatus().getStatusMessage() + "; "
					+ (result.getObligations() == null ? 0 : result.getObligations().getObligations().size()) + " obligation(s)");
		}
	}

}
