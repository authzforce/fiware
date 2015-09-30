AuthZForce - User and Programmers Guide
_______________________________________


AuthZForce is the reference implementation of the Authorization PDP GE. In this regard, it provides an API to manage XACML-based access control policies and provide authorization decisions based on such policies and the context of a given access request. This guide explains how to use the API.

Background and Detail
=====================

This User and Programmers Guide relates to the reference implementation of the Authorization PDP GE which is part of `FIWARE Security Architecture <https://forge.fiware.org/plugins/mediawiki/wiki/fiware/index.php/Security_Architecture>`_.
Please find more information about this Generic Enabler in the following `Open Specification <http://forge.fiware.org/plugins/mediawiki/wiki/fiware/index.php/FIWARE.OpenSpecification.Security.AuthorizationPDP_R4>`_.

User Guide
==========

Since the Authorization PDP is a Generic Enabler which provides backend functionality to other applications (e.g. Generic Enablers or end user facing applications) and security administrators, we do not distinguish between the User and Programmers Guide. Please refer to the Programmers Guide section for more information. 

.. _programmerGuide:

Programmer Guide
================

AuthZForce provides the following APIs:

* PDP API (PDP = Policy Decision Point in the XACML terminology): provides an API for getting authorization decisions computed by a XACML-compliant access control engine;
* PAP API (PAP = Policy Administration Point in XACML terminology): provides API for managing XACML policies to be handled by the Authorization Service PDP.

The full API (RESTful) is described by a document written in the Web Application Description Language format (WADL) and associated XML schema files available on the `FIWARE catalogue <http://catalogue.fiware.org/enablers/authorization-pdp-authzforce/downloads>`_ in the package ``FIWARE-AuthorizationPDP-REST-API-Model-XXX-src.zip`` where ``XXX`` is the current version.

XACML is the main international OASIS standard for access control language and request-response formats, that addresses most use cases of access control. AuthZForce supports the full core XACML 3.0 language; therefore it allows to enforce very generic and complex access control policies.

Attribute-Based Access Control
------------------------------

AuthZForce provides Attribute-Based Access Control. To understand what is meant by "attribute" in the context of access control, below is the list of categories of attributes identified by the XACML standard:

* Subject attributes: the subject is an actor (human, program, device, etc.) requesting access to a resource; attributes may be user ID, Organization, Role, Clearance, etc.
* Resource attributes: the resource is a passive entity (from the access control perspective) on which subject requests to act upon (e.g. data but also human, device, application, etc.); resource attributes may be resource ID, URL, classification, etc.
* Action attributes: the action is the action that the subject requests to perform on the resource (e.g. create, read, delete); attributes may be action ID, parameter A, parameter B, etc.
* Environment attributes: anything else, e.g. current time, CPU load of the PEP/PDP, global threat level, etc.

Policy Administration API
-------------------------

The PAP is used by policy administrators to manage the policy repository from which the PDP loads the enforced policies.
The PAP supports multi-tenancy in the form of generic administration domains that are separate from each other. Each policy administator (except the Superadmin) is in fact a domain administrator, insofar as he is allowed to manage the policy for one or more specific domains. Domains are typically used to support isolation of tenants (one domain per tenant).

Policy Management
+++++++++++++++++

The PAP provides a RESTful API for creating/updating policies for a specific domain, i.e. the top-level a.k.a. root XACML PolicySet of the domain.
HTTP requests to this API must be formatted as follows:

* Method: PUT
* Path: /domains/{domainId}/pap/policySet
* Headers:
    * Content-Type: application/xml
    * Accept: application/xml
* Body: XACML PolicySet as defined in the XACML 3.0 schema.

Example of request given below::

 PUT /domains/3b39dad9-1380-4c5b-8662-50cac998c644/pap/policySet
 HTTP/1.1
 Host: 127.0.0.1:8080
 Accept: application/xml
 Accept-Encoding: gzip, deflate
 Connection: keep-alive
 Content-Type: application/xml; charset=UTF-8
 Content-Length: 2631

 <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 <PolicySet xmlns="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17" PolicySetId="P1"
     Version="1.0" PolicyCombiningAlgId="urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:deny-unless-permit">
     <Description>Sample PolicySet</Description>
     <Target />
     <Policy PolicyId="MissionManagementApp" Version="1.0"
         RuleCombiningAlgId="urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:deny-unless-permit">
         <Description>Policy for MissionManagementApp</Description>
         <Target>
             <AnyOf>
                 <AllOf>
                     <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                         <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">MissionManagementApp</AttributeValue>
                         <AttributeDesignator Category="urn:oasis:names:tc:xacml:3.0:attribute-category:resource"
                             AttributeId="urn:oasis:names:tc:xacml:1.0:resource:resource-id" DataType="http://www.w3.org /2001/XMLSchema#string"
                            MustBePresent="true" />
                    </Match>
                </AllOf>
            </AnyOf>
        </Target>
        <Rule RuleId="MissionManager_role_can_manage_team" Effect="Permit">
            <Description>Only MissionManager role authorized to manage the mission team</Description>
            <Target>
                <AnyOf>
                    <AllOf>
                        <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                            <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">Team</AttributeValue>
                            <AttributeDesignator Category="urn:oasis:names:tc:xacml:3.0:attribute-category:resource"
                                AttributeId="urn:thales:xacml:2.0:resource:sub-resource-id" DataType="http://www.w3.org/2001/XMLSchema#string"
                                MustBePresent="true" />
                        </Match>
                    </AllOf>
                </AnyOf>
                <AnyOf>
                    <AllOf>
                        <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                            <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">manage</AttributeValue>
                            <AttributeDesignator Category="urn:oasis:names:tc:xacml:3.0:attribute-category:action"
                                AttributeId="urn:oasis:names:tc:xacml:1.0:action:action-id" DataType="http://www.w3.org/2001/XMLSchema#string"
                                MustBePresent="true" />
                        </Match>
                    </AllOf>
                </AnyOf>
            </Target>
            <Condition>
                <Apply FunctionId="urn:oasis:names:tc:xacml:3.0:function:any-of">
                    <Function FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal" />
                    <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">MissionManager</AttributeValue>
                    <AttributeDesignator AttributeId="urn:oasis:names:tc:xacml:2.0:subject:role"
                        DataType="http://www.w3.org/2001/XMLSchema#string" MustBePresent="false"
                        Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject" />
                </Apply>
            </Condition>
        </Rule>
    </Policy>
 </PolicySet>


The HTTP response status is 200 if the policy has been successfully created/updated.
It is not possible to delete a policy as a minimal policy must always be in place. If you want a *Permit All* (resp. *Deny All*), you have to update with such a policy: Target All, no condition, effect is Permit (resp. Deny).

Response (body is the PolicySet uploaded in the request)::

 HTTP/1.1 200 OK
 Server: Apache-Coyote/1.1
 Content-Type: application/xml
 Content-Length: 2631
 Date: Mon, 03 Dec 2014 10:12:43 GMT

 <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 <PolicySet ...
 ...content omitted...
 </PolicySet>


Re-usable Policies (e.g. for Hierarchical RBAC)
+++++++++++++++++++++++++++++++++++++++++++++++

The PAP provides a RESTful API for creating/updating <PolicySet>s that can be referred to from the root <PolicySet> for inclusion. This allows to include/reuse a given <PolicySet>s from multiple points of the domain's <PolicySet>, by means of XACML <PolicySetIdReference>s. One major application of this is Hierarchical RBAC. You can refer to the ''Core and hierarchical role based access control (RBAC) profile of XACML v3.0'' specification for how to achieve Hierarchical RBAC with <PolicySetIdReference>s.
HTTP requests to this API must be formatted as follows:

* Method: PUT
* Path: /domains/{domainId}/pap/refPolicySets
* Headers:
    * Content-Type: application/xml
    * Accept: application/xml
* Body: 0 or more XACML <PolicySet>s in a <policySets> element from XML namespace ``http://thalesgroup.com/authz/model`` [#]_.

.. [#] This is not a browsable URL, only an XML namespace URI.

Example of request given below::

 PUT /domains/3b39dad9-1380-4c5b-8662-50cac998c644/pap/refPolicySets
 HTTP/1.1
 Host: 127.0.0.1:8080
 Accept: application/xml
 Accept-Encoding: gzip, deflate
 Connection: keep-alive
 Content-Type: application/xml; charset=UTF-8
 Content-Length: 2631

 <?xml version="1.0" encoding="UTF-8"?>
 <az:policySets xmlns:az="http://thalesgroup.com/authz/model/3.0" xmlns="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17">
    <PolicySet PolicySetId="PPS:Employee" Version="1.0"
        PolicyCombiningAlgId="urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:deny-unless-permit">
        <Description>Permissions specific to the Employee role</Description>
        <Target />
        <Policy PolicyId="PP:Employee" Version="1.0"
            RuleCombiningAlgId="urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:deny-unless-permit">
            <Target />
            <Rule RuleId="Permission_to_create_issue_ticket" Effect="Permit">
                <Target>
                    <AnyOf>
                        <AllOf>
                            <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                                <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">https://acme.com/ticketmanagementservice/tickets</AttributeValue>
                                <AttributeDesignator Category="urn:oasis:names:tc:xacml:3.0:attribute-category:resource"
                                    AttributeId="urn:oasis:names:tc:xacml:1.0:resource:resource-id" DataType="http://www.w3.org/2001/XMLSchema#string"
                                    MustBePresent="true" />
                            </Match>
                        </AllOf>
                    </AnyOf>
                    <AnyOf>
                        <AllOf>
                            <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                                <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">POST</AttributeValue>
                                <AttributeDesignator Category="urn:oasis:names:tc:xacml:3.0:attribute-category:action" AttributeId="urn:oasis:names:tc:xacml:1.0:action:action-id"
                                    DataType="http://www.w3.org/2001/XMLSchema#string" MustBePresent="true" />
                            </Match>
                        </AllOf>
                    </AnyOf>
                </Target>
            </Rule>
        </Policy>
    </PolicySet>
    <!-- <PolicySet PolicySetId="PPS:Technician" PolicyCombiningAlgId="urn:oasis:names:tc:xacml:1.1:policy-combining-algorithm:ordered-permit-overrides">
        ...content omitted... </PolicySet> ... -->
 </az:policySets>


The HTTP response status is 200 if the policy has been successfully created/updated.
Response (body is the PolicySet uploaded in the request)::

 HTTP/1.1 200 OK
 Server: Apache-Coyote/1.1
 Content-Type: application/xml
 Content-Length: 2631
 Date: Mon, 03 Dec 2014 10:12:43 GMT

 <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 <az:policySets ...
 ...content omitted (same as request body)...
 </az:policySets>


AFTER uploading the ''policySets'' above, the PolicySet ''PPS:Employee'' becomes available for use in <PolicySetIdReference>s within any root <PolicySet> you upload from now on, with the API feature of the previous section of this guide. For example, now you can use such a root policySet (bare the <PolicySetIdReference> in particular)::

 <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 <PolicySet xmlns="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    PolicySetId="root:policyset" Version="1.0"
    PolicyCombiningAlgId="urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:deny-unless-permit">
    <Description>Root PolicySet</Description>
    <Target />
    <PolicySet PolicySetId="RPS:Employee" Version="1.0"
        PolicyCombiningAlgId="urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:deny-unless-permit">
        <Description>
            Employee Role PolicySet
        </Description>
        <Target>
            <AnyOf>
                <AllOf>
                    <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                        <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">Employee</AttributeValue>
                        <AttributeDesignator Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject" AttributeId="urn:oasis:names:tc:xacml:2.0:subject:role"
                            DataType="http://www.w3.org/2001/XMLSchema#string" MustBePresent="true" />
                    </Match>
                </AllOf>
            </AnyOf>
        </Target>
        <PolicySetIdReference>PPS:Employee</PolicySetIdReference>
    </PolicySet>
    <PolicySet PolicySetId="RPS:Manager" Version="1.0"
        PolicyCombiningAlgId="urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:deny-unless-permit">
        <Description>
            Manager Role PolicySet
        </Description>
        <Target>
            <AnyOf>
                <AllOf>
                    <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                        <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">Manager</AttributeValue>
                        <AttributeDesignator Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject" AttributeId="urn:oasis:names:tc:xacml:2.0:subject:role"
                            DataType="http://www.w3.org/2001/XMLSchema#string" MustBePresent="true" />
                    </Match>
                </AllOf>
            </AnyOf>
        </Target>
        <Policy PolicyId="PP1:Manager" Version="1.0"
            RuleCombiningAlgId="urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:deny-unless-permit">
            <Description>Permissions specific to Manager Role</Description>
            <Target />
            <Rule RuleId="Permission_to_create_new_project" Effect="Permit">
                <Target>
                    <AnyOf>
                        <AllOf>
                            <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                                <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">https://acme.com/ticketmanagementservice/projects</AttributeValue>
                                <AttributeDesignator Category="urn:oasis:names:tc:xacml:3.0:attribute-category:resource" AttributeId="urn:oasis:names:tc:xacml:1.0:resource:resource-id"
                                    DataType="http://www.w3.org/2001/XMLSchema#string" MustBePresent="true" />
                            </Match>
                        </AllOf>
                    </AnyOf>
                    <AnyOf>
                        <AllOf>
                            <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                                <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">POST</AttributeValue>
                                <AttributeDesignator Category="urn:oasis:names:tc:xacml:3.0:attribute-category:action" AttributeId="urn:oasis:names:tc:xacml:1.0:action:action-id"
                                    DataType="http://www.w3.org/2001/XMLSchema#string" MustBePresent="true"/>
                            </Match>
                        </AllOf>
                    </AnyOf>
                </Target>
            </Rule>
        </Policy>
        <!-- This role is senior to the Employee role, therefore includes the Employee role Permission PolicySet -->
        <PolicySetIdReference>PPS:Employee</PolicySetIdReference>
    </PolicySet>
 </PolicySet>


Policy Decision API
-------------------

The PDP API returns an authorization decision based on the currently enforced policy, access control attributes provided in the request and possibly other attributes resolved by the PDP itself. The Authorization decision is typically Permit or Deny. The PDP is able to resolve extra attributes not provided directly in the request, such as the current date/time (environment attribute).

The PDP provides an HTTP RESTful API for requesting authorization decisions.
The HTTP request must be formatted as follows:

* Method: POST
* Path: /domains/{domainId}/pdp
* Headers:
    * Content-Type: application/xml
    * Accept: application/xml
* Body: XACML Request as defined in the XACML 3.0 schema.

The HTTP response body is a XACML Response as defined in the XACML 3.0 schema.

Example of request given below::

 POST /domains/3b39dad9-1380-4c5b-8662-50cac998c644/pdp
 HTTP/1.1
 Host: 127.0.0.1:8080
 Accept: application/xml
 Accept-Encoding: gzip, deflate
 Connection: keep-alive
 Content-Type: application/xml; charset=UTF-8
 Content-Length: 954

 <?xml version='1.0' encoding='UTF-8' standalone='yes'?>
 <Request xmlns='urn:oasis:names:tc:xacml:3.0:core:schema:wd-17' CombinedDecision="false"
    ReturnPolicyIdList="false">
    <Attributes Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject">
        <Attribute AttributeId='urn:oasis:names:tc:xacml:1.0:subject:subject-id'
            IncludeInResult="false">
            <AttributeValue DataType='http://www.w3.org/2001/XMLSchema#string'>joe</AttributeValue>
        </Attribute>
        <Attribute AttributeId="urn:oasis:names:tc:xacml:2.0:subject:role" IncludeInResult="false">
            <AttributeValue DataType='http://www.w3.org/2001/XMLSchema#string'>Manager</AttributeValue>
        </Attribute>
    </Attributes>
        <Attributes Category="urn:oasis:names:tc:xacml:3.0:attribute-category:resource">
            <Attribute AttributeId='urn:oasis:names:tc:xacml:1.0:resource:resource-id'
                IncludeInResult="false">
                <AttributeValue DataType='http://www.w3.org/2001/XMLSchema#string'>MissionManagementApp</AttributeValue>
            </Attribute>
            <Attribute AttributeId='urn:thales:xacml:2.0:resource:sub-resource-id' IncludeInResult="false">
                <AttributeValue DataType='http://www.w3.org/2001/XMLSchema#string'>Team</AttributeValue>
            </Attribute>
        </Attributes>
        <Attributes Category="urn:oasis:names:tc:xacml:3.0:attribute-category:action">
            <Attribute AttributeId='urn:oasis:names:tc:xacml:1.0:action:action-id'
                IncludeInResult="false">
                <AttributeValue DataType='http://www.w3.org/2001/XMLSchema#string'>manage</AttributeValue>
            </Attribute>
        </Attributes>
        <Attributes Category="urn:oasis:names:tc:xacml:3.0:attribute-category:environment" />
 </Request>

Response::

 HTTP/1.1 200 OK
 Server: Apache-Coyote/1.1
 Content-Type: application/xml
 Content-Length: 355
 Date: Mon, 03 Dec 2014 14:06:26 GMT

 <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 <Response xmlns="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17">
    <Result>
        <Decision>Permit</Decision>
        <Status>
            <StatusCode Value="urn:oasis:names:tc:xacml:1.0:status:ok" />
        </Status>
    </Result>
 </Response>


Integration with the IdM GE (e.g. for OAuth)
--------------------------------------------
The easy way to integrate with IdM is to delegate the integration to the PEP up-front, i.e. we assume the PEP got all the required IdM-related info and forwards it to the Authorization PDP in the XACML request; the PEP Proxy by UPM can provide such a feature.

Software Libraries for clients of AuthZForce or other Authorization PDP GEis
----------------------------------------------------------------------------
The full API (RESTful) is described by a document written in the Web Application Description Language format (WADL) and associated XML schema files available on the `FIWARE catalogue <http://catalogue.fiware.org/enablers/authorization-pdp-authzforce/downloads>`_ in the package ``FIWARE-AuthorizationPDP-REST-API-Model-XXX-src.zip``, where ``XXX`` is the current version. Therefore, you can use any WADL-supporting REST framework for clients; for instance in Java: Jersey, Apache CXF. From that, you can use WADL-to-code generators to generate your client code. For example in Java, 'wadl2java' tools allow to generate code for JAX-RS compatible frameworks such as Apache CXF and Jersey. Actually, we can provide a CXF-based Java library created with this tool to facilitate the development of clients.
