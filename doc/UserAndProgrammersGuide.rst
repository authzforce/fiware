==========================
User and Programmers Guide
==========================

This guide explains how to use the API to manage XACML-based access control policies and provide authorization decisions based on such policies and the context of a given access request.

**If you have been using a previous version of AuthzForce, check the** `release notes <https://github.com/authzforce/server/blob/release-5.4.1/CHANGELOG.md#5.4.1>`_ **to know what is changed and what is new.**

Background and Detail
=====================

This User and Programmers Guide applies to the reference implementation of the Authorization PDP GE which is part of `FIWARE Security Architecture <https://forge.fiware.org/plugins/mediawiki/wiki/fiware/index.php/Security_Architecture>`_. Please find more information about this Generic Enabler in the following `Open Specification <http://forge.fiware.org/plugins/mediawiki/wiki/fiware/index.php/FIWARE.OpenSpecification.Security.AuthorizationPDP_R5>`_.

User Guide
==========

Since the Authorization PDP is a Generic Enabler which provides backend functionality to other applications (e.g. Generic Enablers or end user facing applications) and security administrators, we do not distinguish between the User and Programmers Guide. Please refer to the Programmers Guide section for more information. 

.. _programmerGuide:

Programmer Guide
================

AuthzForce provides the following APIs:

* PDP API (PDP = Policy Decision Point in the XACML terminology): provides an API for getting authorization decisions computed by a XACML-compliant access control engine;
* PAP API (PAP = Policy Administration Point in XACML terminology): provides API for managing XACML policies to be handled by the Authorization Service PDP.

The full API (RESTful) is described by a document written in the Web Application Description Language format (WADL) and associated XML schema files available in `Authzforce rest-api-model project files <https://github.com/authzforce/rest-api-model/tree/release-5.3.1/src/main/resources>`_.

XACML is the main international OASIS standard for access control language and request-response formats, that addresses most use cases of access control. AuthzForce supports the full core XACML 3.0 language; therefore it allows to enforce generic and complex access control policies.

General recommendations for developers
--------------------------------------
In all the sample XML outputs shown in the next sections, the XML namespace prefix of any API response element, such as the XACML ``Response`` element, might vary from an AuthzForce run time to another, but it is always the same XML element as the prefix is always mapped to the same namespace, such as ``urn:oasis:names:tc:xacml:3.0:core:schema:wd-17`` (XACML 3.0 namespace) for the XACML ``Response``. Therefore, any valid (namespace-aware) XML parser will handle it equally, no matter the namespace prefix. Beware of that XML namespace-prefix mapping issue if you are parsing XML manually.

We strongly recommend developers to use XML-schema-aware software with XML schema validation enabled for all XML processing. This will make troubleshooting easier, and save you a lot of trouble. You can find the XML schemas and an example of Java client code with schema validation in the `Authzforce rest-api-model project files`_. 


Attribute-Based Access Control
------------------------------

AuthzForce provides Attribute-Based Access Control. To understand what is meant by *attribute* in the context of access control, below is the list of standard categories of attributes identified by the XACML standard:

* Subject attributes: the subject is an actor (human, program, device, etc.) requesting access to a resource; attributes may be user ID, Organization, Role, Clearance, etc. In fact, XACML enables you to be more specific about the type of subject, e.g. intermediary subject, requesting machine, etc. 
* Resource attributes: the resource is a passive entity (from the access control perspective) on which subject requests to act upon (e.g. data but also human, device, application, etc.); resource attributes may be resource ID, URL, classification, etc.
* Action attributes: the action is the action that the subject requests to perform on the resource (e.g. create, read, delete); attributes may be action ID, parameter A, parameter B, etc.
* Environment attributes: anything else, e.g. current time, CPU load of the PEP/PDP, global threat level, etc.

If this is not enough, XACML enables you to make your own attribute categories as well.

Product info API
----------------

You can get product information (name, version...) with following HTTP request:

* Method: GET
* Path: ``/version``
* Headers:

    * Accept: ``application/xml; charset=UTF-8``


Domain Management API
---------------------

The API allows AuthzForce application administrators or administration interfaces to create domains for the users, and remove domains once they are no longer used. This part of the API is described in the section :ref:`adminGuideDomainAdmin`. 

The API is provided over HTTP in order to comply with the test assertions ``urn:oasis:names:tc:xacml:3.0:profile:rest:assertion:http:client`` and ``urn:oasis:names:tc:xacml:3.0:profile:rest:assertion:http:server`` of `REST Profile of XACML v3.0 Version 1.0 <http://docs.oasis-open.org/xacml/xacml-rest/v1.0/xacml-rest-v1.0.html>`_.

Each AuthzForce domain represents an independent *RESTful XACML system*, in the context of the `REST Profile of XACML v3.0 Version 1.0`_.

End-users may retrieve the domain's resource content as follows:

* Method: GET
* Path: ``/domains/{domainId}``
* Headers:

    * Accept: ``application/xml; charset=UTF-8``
    
For example, this request gets the resource for domain ``iMnxv7sDEeWFwqVFFMDLTQ`` ::

   GET /domains/iMnxv7sDEeWFwqVFFMDLTQ 
   HTTP/1.1 
   Accept: application/xml; charset=UTF-8

If the domain exists, the response goes:

.. code-block:: xml
   :linenos:

   HTTP/1.1 200 OK 
   Content-Type: application/xml; charset=UTF-8
 
   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <domain 
    xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5" 
    xmlns:atom="http://www.w3.org/2005/Atom">
    <properties externalId="test-domain1">
     <description>Test domain</description>
    </properties>
    <childResources>
     <atom:link rel="item" href="/properties" title="Domain properties"/>
     <atom:link rel="item" href="/pap" title="Policy Administration Point"/>
     <atom:link 
      rel="http://docs.oasis-open.org/ns/xacml/relation/pdp" 
      href="/pdp" title="Policy Decision Point"/>
    </childResources>
   </domain>


If a domain with such ID does not exist, an error 404 is returned.

Therefore, in the context of the `REST Profile of XACML v3.0 Version 1.0`_, the location of the single entry point of a domain-specific RESTful XACML system is ``/domains/{domainId}``, and you may get the link to the PDP from the response for the ``GET`` request to this entry point location, looking for the link relation ``http://docs.oasis-open.org/ns/xacml/relation/pdp``. In this respect, we comply with test assertions ``urn:oasis:names:tc:xacml:3.0:profile:rest:assertion:home:documentation``, ``urn:oasis:names:tc:xacml:3.0:profile:rest:assertion:home:status`` and ``urn:oasis:names:tc:xacml:3.0:profile:rest:assertion:home:pdp`` of the `REST Profile of XACML v3.0 Version 1.0`_.

The API also allows users to update certain properties of the domain allocated to them: 

* An **externalId** (optional) for the domain, which users/clients can modify and more easily use as reference, as opposed to the unique and read-only domain ID assigned by the API - once and for all - when the domain is created;
* A **description** of the domain (optional).

You may retrieve the current domain properties as follows:

* Method: GET
* Path: ``/domains/{domainId}/properties``
* Headers:

    * Accept: ``application/xml; charset=UTF-8``

For example, this request gets the properties of domain ``iMnxv7sDEeWFwqVFFMDLTQ``. In this case, there is no specific property, which is the case by default::

   GET /domains/iMnxv7sDEeWFwqVFFMDLTQ/properties 
   HTTP/1.1 
   Accept: application/xml; charset=UTF-8

The response goes:

.. code-block:: xml
   :linenos:

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
   <domainProperties 
      xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5"
      externalId="test-domain1">
      <description>Test domain</description>
   </domainProperties> 
   

You may update the domain properties as follows:

* Method: PUT
* Path: ``/domains/{domainId}/properties``
* Headers:

   * Content-Type: ``application/xml; charset=UTF-8``
   * Accept: ``application/xml; charset=UTF-8``

* Body: new properties.

For example, this request sets the ``externalId`` property to ``my-domain-123``:

.. code-block:: xml
   :linenos:

   PUT /domains/iMnxv7sDEeWFwqVFFMDLTQ/properties 
   HTTP/1.1 
   Accept: application/xml; charset=UTF-8 
   Content-Type: application/xml; charset=UTF-8

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
   <domainProperties 
    xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5" 
    externalId="my-domain-123" />

The response is the new properties.

As a result, the domain's external ID ``my-domain-123`` points to the domain ``iMnxv7sDEeWFwqVFFMDLTQ``. Clients may only rely on the externalId under their control to recover the API-defined domain ID, before they begin to use other API operations that require the API-defined domain ID. Indeed, clients may look up the API-defined ID corresponding to a given ``externalId`` as follows::

   GET /domains?externalId=my-domain-123
   HTTP/1.1 
   Accept: application/xml; charset=UTF-8

The response gives the corresponding domain ID in a link ``href`` attribute:

.. code-block:: xml
   :linenos:

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <resources 
     xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5" 
     xmlns:atom="http://www.w3.org/2005/Atom">
     <atom:link rel="item" href="iMnxv7sDEeWFwqVFFMDLTQ" title="iMnxv7sDEeWFwqVFFMDLTQ"/>
   </resources> 


Policy Administration API
-------------------------

The PAP is used by policy administrators to manage the policy repository from which the PDP loads the enforced policies. The PAP supports multi-tenancy in the form of generic administration domains that are separate from each other. Each policy administrator (except the Superadmin) is in fact a domain administrator, insofar as he is allowed to manage the policy for one or more specific domains. Domains are typically used to support isolation of tenants (one domain per tenant).

Adding and updating Policies
++++++++++++++++++++++++++++

The PAP provides a RESTful API for adding and updating policies to a specific domain. HTTP requests to this API must be formatted as follows:

* Method: POST
* Path: ``/domains/{domainId}/pap/policies``
* Headers:

    * Content-Type: ``application/xml; charset=UTF-8``
    * Accept: ``application/xml; charset=UTF-8``
    
* Body: XACML PolicySet as defined in the XACML 3.0 schema.

Example of request given below:

.. code-block:: xml
   :linenos:

   POST /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/policies 
   HTTP/1.1 
   Accept: application/xml; charset=UTF-8 
   Content-Type: application/xml; charset=UTF-8

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <PolicySet 
    xmlns="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17" 
    PolicySetId="P1"
    Version="1.0" 
    PolicyCombiningAlgId="urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:deny-unless-permit">
    <Description>Sample PolicySet</Description> 
    <Target /> 
    <Policy 
     PolicyId="MissionManagementApp" 
     Version="1.0"
     RuleCombiningAlgId="urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:deny-unless-permit"> 
     <Description>Policy for MissionManagementApp</Description> 
     <Target>
      <AnyOf>
       <AllOf>
        <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
         <AttributeValue 
          DataType="http://www.w3.org/2001/XMLSchema#string">MissionManagementApp</AttributeValue>
         <AttributeDesignator 
          Category="urn:oasis:names:tc:xacml:3.0:attribute-category:resource"
          AttributeId="urn:oasis:names:tc:xacml:1.0:resource:resource-id" 
          DataType="http://www.w3.org/2001/XMLSchema#string" 
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
          <AttributeValue 
           DataType="http://www.w3.org/2001/XMLSchema#string">Team</AttributeValue>
          <AttributeDesignator 
           Category="urn:oasis:names:tc:xacml:3.0:attribute-category:resource"
           AttributeId="urn:thales:xacml:2.0:resource:sub-resource-id" 
           DataType="http://www.w3.org/2001/XMLSchema#string"
           MustBePresent="true" />
         </Match>
        </AllOf>
       </AnyOf> 
       <AnyOf>
        <AllOf>
         <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
          <AttributeValue 
           DataType="http://www.w3.org/2001/XMLSchema#string">manage</AttributeValue>
          <AttributeDesignator 
           Category="urn:oasis:names:tc:xacml:3.0:attribute-category:action"
           AttributeId="urn:oasis:names:tc:xacml:1.0:action:action-id" 
           DataType="http://www.w3.org/2001/XMLSchema#string"
           MustBePresent="true" />
         </Match>
        </AllOf>
       </AnyOf>
      </Target> 
      <Condition>
       <Apply FunctionId="urn:oasis:names:tc:xacml:3.0:function:any-of">
        <Function FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal" />
         <AttributeValue 
          DataType="http://www.w3.org/2001/XMLSchema#string">MissionManager</AttributeValue>
         <AttributeDesignator AttributeId="urn:oasis:names:tc:xacml:2.0:subject:role"
          DataType="http://www.w3.org/2001/XMLSchema#string" MustBePresent="false"
          Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject" />
       </Apply>
      </Condition>
     </Rule>
    </Policy>
   </PolicySet>


The HTTP response status is 200 with a link to manage the new policy, if the request was successful. The link is made of the policy ID and version separated by '/'.

Response:

.. code-block:: xml
   :linenos:

   HTTP/1.1 200 OK 
   Content-Type: application/xml; charset=UTF-8

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
   <atom:link xmlns:atom="http://www.w3.org/2005/Atom" 
     rel="item" href="P1/1.0" title="Policy 'P1' v1.0"/>

To update a policy, you add a new version of the policy, i.e. you send the same request as above, but with a higher ``Version`` value. 

**WARNING**: after you add/update a policy, it is not necessarily used, i.e.evaluated, by the PDP. The PDP starts the evaluation with the root policy specified in the `Policy Decision (PDP) Properties`_. 
Therefore, only this root policy and any other one referenced (directly or indirectly) by this root policy is applicable. 
See the section `Policy Decision (PDP) Properties`_ to learn how to verify applicable policies and change the root policy.

**WARNING**: Although AuthzForce Server supports ``application/json`` media type as well for sending/getting policies in JSON format, it is still experimental for various reasons. 
One of which is a strong limitation that you should be much aware before using it for complex XACML policies: 
XML schema type definitions using a repeated``choice`` (between different element types) or a polymorphic sequence with ``maxOccurs > 1`` are not handled properly in JSON, 
at least not in any standard way or without significant customization of JSON processing. 
For example of such polymorphic sequence, an XACML Apply element may contain multiple Expression elements in a sequence,
and an Expression may be an Apply again, or an AttributeValue, or an AttributeDesignator, or a VariableReference, etc.
For example of repeated ``choice``, a XACML PolicySet may contain P1, then PS1, then P2, then PS2, where ``P`` stands for XACML Policy and ``PS`` for XACML PolicySet.
With the well-known simple conventions like the one used by AuthzForce Server for XML-to-JSON mapping, this is mapped to two separate JSON arrays, one for Policy element(s) (``[P1, P2]``) 
and one for PolicySet element(s) (``[PS1, PS2]``). Therefore, the originally intended evaluation order is lost!
It gets even worse if you use PolicySetIdReference element(s) as well (PolicyIdReference is out of the question since not supported by the API).
Of course, there are solutions such as adding a wrapping JSON object with a key called ``PolicyOrPolicySetOrPolicySetIdReference`` with an array as value 
where each item must have a type information to inform the consumer whether it is a Policy, PolicySet or PolicySetIdReference. 
This kind of solution is used in JAXB for instance to map XML to Java model (except the array is replaced by a Java collection). 
Like in JAXB to Java, this introduces some extra complexity to JSON processing that makes the JSON alternative lose much of its appeal compared to XML.
In short, **you should not use JSON for policies either mixing XACML Policy, PolicySet or PolicySetIdReference elements within the same PolicySet; or Expressions within the same Apply**.


Getting Policies and Policy Versions
++++++++++++++++++++++++++++++++++++

Once added to the domain as shown previously, you can get the policy by its ID as follows:

* Method: GET
* Path: ``/domains/{domainId}/pap/policies/{policyId}``
* Headers:

    * Accept: ``application/xml; charset=UTF-8``

For example::
 
 GET /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/policies/P1 HTTP/1.1 Accept: application/xml; charset=UTF-8

The response is the list of links to the versions of the policy ``P1`` available in the domain ``iMnxv7sDEeWFwqVFFMDLTQ``:

.. code-block:: xml
   :linenos:
 
   HTTP/1.1 200 OK 
   Content-Type: application/xml; charset=UTF-8

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <resources 
     xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5" 
     xmlns:atom="http://www.w3.org/2005/Atom">
       <atom:link rel="item" href="1.0"/> 
       <atom:link rel="item" href="1.1"/> 
       <atom:link rel="item" href="2.0"/>
       <atom:link rel="item" href="2.1"/> 
       <atom:link rel="item" href="2.2"/> 
       ...
   </resources>

As the ``href`` values are telling you, you may get a specific version of the policy as follows:

* Method: GET
* Path: ``/domains/{domainId}/pap/policies/{policyId}/{version}``
* Headers:

    * Accept: ``application/xml; charset=UTF-8``
    
For example::

 GET /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/policies/P1/1.0 HTTP/1.1 Accept: application/xml; charset=UTF-8

The response is the policy document (XACML PolicySet) in this version.

You may use the special keyword ``latest`` as version here to get the latest version of a given policy; e.g. URL path ``/domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/policies/P1/latest`` points to the latest version of the policy ``P1`` in domain ``iMnxv7sDEeWFwqVFFMDLTQ``.

Last but not least, you may get all policies in the domain as follows:

* Method: GET
* Path: ``/domains/{domainId}/pap/policies``
* Headers:

    * Accept: ``application/xml; charset=UTF-8``

For example:

.. code-block:: xml
   :linenos:

   GET /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/policies 
   HTTP/1.1 
   Accept: application/xml; charset=UTF-8

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
   <resources 
     xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5" 
     xmlns:atom="http://www.w3.org/2005/Atom">
       <atom:link rel="item" href="root"/> 
       <atom:link rel="item" href="P1"/> 
       <atom:link rel="item" href="P2"/> 
   </resources>


Removing Policies and Policy Versions
+++++++++++++++++++++++++++++++++++++

You may remove a policy version from the domain as follows:

* Method: DELETE
* Path: ``/domains/{domainId}/pap/policies/{policyId}/{version}``
* Headers:

    * Accept: ``application/xml; charset=UTF-8``

For example::
 
 DELETE /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/policies/P1/1.0 HTTP/1.1 Accept: application/xml; charset=UTF-8
 
The response is the removed policy document (XACML PolicySet) in this version.

You may remove a policy, i.e. all versions of a policy from the domain as follows:

* Method: DELETE
* Path: ``/domains/{domainId}/pap/policies/{policyId}``
* Headers:

    * Accept: ``application/xml; charset=UTF-8``

For example::
 
 DELETE /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/policies/P1 HTTP/1.1 Accept: application/xml; charset=UTF-8

The response is the list of links to all the removed versions of the policy, similar to the the GET request on the same URL.


Re-usable Policies (e.g. for Hierarchical RBAC)
+++++++++++++++++++++++++++++++++++++++++++++++

The PAP API supports policies that have references to other policies existing in the domain. This allows to include/reuse a given policy from multiple policies, or multiple parts of the same policy, by means of XACML ``<PolicySetIdReference>`` elements. One major application of this is Hierarchical RBAC. You can refer to the `XACML v3.0 Core and Hierarchical Role Based Access Control (RBAC) Profile <http://docs.oasis-open.org/xacml/3.0/rbac/v1.0/xacml-3.0-rbac-v1.0.html>`_ specification for how to achieve hierarchical RBAC with ``<PolicySetIdReference>`` elements.

For example, I want to define a role *Employee* and a role *Manager* derived  from *Employee*. In other words, permissions of an *Employee* are included in the permissions of a *Manager*. In order to create this role hierarchy, we first add the Employee's *Permission PolicySet*:

.. code-block:: xml
   :linenos:

   POST /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/policies 
   HTTP/1.1
   Accept: application/xml; charset=UTF-8 
   Content-Type: application/xml; charset=UTF-8

   <?xml version="1.0" encoding="UTF-8"?>
   <PolicySet
    xmlns="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17"
    PolicySetId="PPS:Employee" 
    Version="1.0"
    PolicyCombiningAlgId="urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:deny-unless-permit">
    <Description>Permissions specific to the Employee role</Description> 
    <Target /> 
    <Policy 
     PolicyId="PP:Employee" 
     Version="1.0"
     RuleCombiningAlgId="urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:deny-unless-permit"> 
     <Target /> 
     <Rule RuleId="Permission_to_create_issue_ticket" Effect="Permit">
      <Target>
       <AnyOf>
        <AllOf>
         <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
          <AttributeValue 
           DataType="http://www.w3.org/2001/XMLSchema#string">https://acme.com/tickets</AttributeValue>
          <AttributeDesignator Category="urn:oasis:names:tc:xacml:3.0:attribute-category:resource"
           AttributeId="urn:oasis:names:tc:xacml:1.0:resource:resource-id"
           DataType="http://www.w3.org/2001/XMLSchema#string" MustBePresent="true" />
         </Match>
        </AllOf>
       </AnyOf> 
       <AnyOf>
        <AllOf>
         <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
          <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">POST</AttributeValue>
          <AttributeDesignator 
           Category="urn:oasis:names:tc:xacml:3.0:attribute-category:action"
           AttributeId="urn:oasis:names:tc:xacml:1.0:action:action-id" 
           DataType="http://www.w3.org/2001/XMLSchema#string"
           MustBePresent="true" />
         </Match>
        </AllOf>
       </AnyOf>
      </Target>
     </Rule>
    </Policy>
   </PolicySet>

Then we add the role-based hierarchical policy defining the Employee role and the Manager role, both with a reference (``<PolicySetIdReference>``) to the Employee's *Permission PolicySet* added previously. The Manager role has one policy more, so more permissions:

.. code-block:: xml
   :linenos:

   POST /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/policies 
   HTTP/1.1 
   Accept: application/xml; charset=UTF-8 
   Content-Type: application/xml; charset=UTF-8

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
   <PolicySet xmlns="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    PolicySetId="rbac:policyset" 
    Version="1.0"
    PolicyCombiningAlgId="urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:deny-unless-permit"> 
    <Description>Root PolicySet</Description> 
    <Target /> 
    <PolicySet PolicySetId="RPS:Employee" Version="1.0"
     PolicyCombiningAlgId="urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:deny-unless-permit">
     <Description>Employee Role PolicySet</Description> 
     <Target>
      <AnyOf>
       <AllOf>
        <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
         <AttributeValue 
          DataType="http://www.w3.org/2001/XMLSchema#string">Employee</AttributeValue>
         <AttributeDesignator 
          Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject"
          AttributeId="urn:oasis:names:tc:xacml:2.0:subject:role" 
          DataType="http://www.w3.org/2001/XMLSchema#string"
          MustBePresent="true" />
        </Match>
       </AllOf>
      </AnyOf>
     </Target> 
     <PolicySetIdReference>PPS:Employee</PolicySetIdReference>
    </PolicySet> 
    <PolicySet PolicySetId="RPS:Manager" Version="1.0"
     PolicyCombiningAlgId="urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:deny-unless-permit">
     <Description>Manager Role PolicySet</Description> 
     <Target>
      <AnyOf>
       <AllOf>
        <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
         <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">Manager</AttributeValue>
         <AttributeDesignator 
          Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject"
          AttributeId="urn:oasis:names:tc:xacml:2.0:subject:role" 
          DataType="http://www.w3.org/2001/XMLSchema#string"
          MustBePresent="true" />
        </Match>
       </AllOf>
      </AnyOf>
     </Target> 
     <Policy PolicyId="PP1:Manager" Version="1.0"
      RuleCombiningAlgId="urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:deny-unless-permit">
      <Description>Permissions specific to Manager Role</Description> 
      <Target /> 
      <Rule
       RuleId="Permission_to_create_new_project" Effect="Permit">
       <Target>
        <AnyOf>
         <AllOf>
          <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
           <AttributeValue 
            DataType="http://www.w3.org/2001/XMLSchema#string">https://acme.com/projects</AttributeValue>
           <AttributeDesignator 
            Category="urn:oasis:names:tc:xacml:3.0:attribute-category:resource"
            AttributeId="urn:oasis:names:tc:xacml:1.0:resource:resource-id"
            DataType="http://www.w3.org/2001/XMLSchema#string" MustBePresent="true" />
          </Match>
         </AllOf>
        </AnyOf> 
        <AnyOf>
         <AllOf>
          <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
           <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">POST</AttributeValue>
           <AttributeDesignator 
            Category="urn:oasis:names:tc:xacml:3.0:attribute-category:action"
            AttributeId="urn:oasis:names:tc:xacml:1.0:action:action-id"
            DataType="http://www.w3.org/2001/XMLSchema#string" MustBePresent="true"/>
          </Match>
         </AllOf>
        </AnyOf>
       </Target>
      </Rule>
     </Policy> 
     <!-- This role is senior to the Employee role, therefore includes the Employee role Permission 
      PolicySet -->
     <PolicySetIdReference>PPS:Employee</PolicySetIdReference>
    </PolicySet>
   </PolicySet>

You may add more policies for more roles as you wish. Once you are satisfied with your role hierarchy, you may apply your new RBAC policy by updating the domain's root policy reference (this may not be necessary if you reused the same root policy ID as before, in which case your policy is already active by now):

.. code-block:: xml
   :linenos:

   PUT /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/pdp.properties 
   HTTP/1.1 
   Accept: application/xml; charset=UTF-8 
   Content-Type: application/xml; charset=UTF-8

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
   <pdpPropertiesUpdate xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5">
    <rootPolicyRefExpression>rbac:policyset</rootPolicyRefExpression>
   </pdpPropertiesUpdate>

The policy is now enforced by the PDP as described in the next section.


Policy Repository (PRP) Properties
++++++++++++++++++++++++++++++++++
Administrators (global or domain-specific) may configure the policy repository with the following properties:  

 * ``maxPolicyCount``: optional, stricly positive integer that indicates the maximum number of policies on a domain, no limit if undefined.
 * ``maxVersionCountPerPolicy``: optional, stricly positive integer that indicates the maximum number of versions per policy, no limit if undefined.
 * ``versionRollingEnabled``: boolean, true if and only if policy versions should be rolled over, i.e. when ``maxVersionCountPerPolicy`` has been reached, oldest versions are automatically removed to make place.

For example, below is a HTTP GET request and response for the policy repository properties of domain ``iMnxv7sDEeWFwqVFFMDLTQ``:

.. code-block:: xml
   :linenos:

   GET /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/prp.properties
   Accept: application/xml
   
   -
   
   HTTP/1.1 200 OK
   Content-Type: application/xml
 
   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <prpProperties xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5">
      <maxPolicyCount>10</maxPolicyCount>
      <maxVersionCountPerPolicy>10</maxVersionCountPerPolicy>
      <versionRollingEnabled>true</versionRollingEnabled>
   </prpProperties>
 
The HTTP PUT request to update the properties has a body that is similar to the GET response:

.. code-block:: xml
   :linenos:

   PUT /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/prp.properties
   Content-Type: application/xml
 
   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <prpProperties xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5">
      <maxPolicyCount>4</maxPolicyCount>
      <maxVersionCountPerPolicy>2</maxVersionCountPerPolicy>
      <versionRollingEnabled>true</versionRollingEnabled>
   </prpProperties>  

The response format is the same as for the GET request.

Policy Decision (PDP) Properties
++++++++++++++++++++++++++++++++

Administrators (global or domain-specific) may configure the PDP engine with the following properties: 

* ``rootPolicyRefExpression``: reference - in the form of a `XACML PolicySetIdReference <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#_Toc325047115>`_ - to the root policy. The root policy is the policy from which the PDP starts the evaluation. A policy matching this reference must exist on the domain, therefore it must have been added in the way described in `Adding and updating Policies`_. If there is no specific ``Version`` in the reference, the latest matching policy version is selected.  
* ``feature`` elements: enable particular PDP features. Each ``feature`` has an ID, ``type`` and ``enabled`` flag saying whether the feature is enabled or not.

Supported PDP features (IDs) by ``type``: 

* Type ``urn:ow2:authzforce:feature-type:pdp:core``: PDP core engine features (as opposed to other types related to PDP extensions ).

    * ``urn:ow2:authzforce:feature:pdp:core:strict-attribute-issuer-match``: strict matching of attribute ``Issuer`` values in XACML Requests against corresponding attribute designators' ``Issuer`` values in policies. This means that an ``<AttributeDesignator>`` without ``Issuer`` only matches request Attributes without ``Issuer`` (and same AttributeId, Category...). This mode is not fully compliant with `XACML 3.0 Core specifcation of AttributeDesignator (§5.29) <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#_Toc325047134>`_, in the case that the Issuer is indeed not present on a AttributeDesignator, but it may perform better and is recommended when all AttributeDesignators have an Issuer. Reminder: `XACML 3.0 Core specifcation of AttributeDesignator (§5.29)`_ says: *If the Issuer is not present in the attribute designator, then the matching of the attribute to the named attribute SHALL be governed by AttributeId and DataType attributes alone.*
    * ``urn:ow2:authzforce:feature:pdp:core:xpath-eval``: enables support for XACML AttributeSelectors and datatype ``urn:oasis:names:tc:xacml:3.0:data-type:xpathExpression``. If this feature is disabled, only standard `XACML 3.0 Core datatypes <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#_Toc325047233>`_ marked *M*, i.e. mandatory, are supported. Since ``xpathExpression`` is optional in the standard, it is therefore not supported unless this feature is enabled. **This feature is experimental and may have a negative impact on performance. Use with caution.** 
      
* Type ``urn:ow2:authzforce:feature-type:pdp:request-preproc``: XACML (Individual) Request preprocessor (*Individual* means that even if the XACML Multiple Decision Profile is active, the request preprocessor applies to each *Individual* Decision Request as defined in the Profile). As a convention, request preprocessor IDs with suffix ``-lax`` allow multivalued attributes in form of duplicate Attribute elements (with same meta-data) in the same Attributes element of a Request, in order to accept multivalued attributes in conformance with `XACML 3.0 Core specification of Multivalued attributes (§7.3.3) <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#_Toc325047176>`_. Request preprocessor IDs with suffix ``-strict`` do not allow this behavior, i.e. multivalued attributes must be formed by grouping all AttributeValue elements in the same Attribute element (instead of duplicate Attribute elements), therefore they do not fully comply with `XACML 3.0 Core specification of Multivalued attributes (§7.3.3)`_. However, they perform usually better than their ``-lax`` counterparts since it simplifies the Request and allows parsing optimizations by the PDP. Below is an example of Request that would not be accepted by a ``-strict`` request preprocessor because of duplicate Attribute:

  .. code-block:: xml
     :linenos:
 
     <Request 
      xmlns="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17" ReturnPolicyIdList="false" CombinedDecision="false"> <Attributes Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject">
         <Attribute AttributeId="urn:oasis:names:tc:xacml:2.0:subject:role" IncludeInResult="false">
            <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">CSO</AttributeValue>
         </Attribute> <Attribute AttributeId="urn:oasis:names:tc:xacml:2.0:subject:role" IncludeInResult="false">
            <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">CTO</AttributeValue>
         </Attribute> ...
      </Attributes> ...   
     </Request>
  
  Below is the equivalent of the previous Request in a form that is accepted by a ``-strict`` request preprocessor (no duplicate Attribute):
  
  .. code-block:: xml
     :linenos:

     <Request 
      xmlns="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17" ReturnPolicyIdList="false" CombinedDecision="false"> <Attributes Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject">
         <Attribute AttributeId="urn:oasis:names:tc:xacml:2.0:subject:role" IncludeInResult="false">
            <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">CSO</AttributeValue> <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">CTO</AttributeValue>
         </Attribute> ...
      </Attributes> ...   
     </Request>
        
  Available request preprocessor IDs: 

   * *urn:ow2:authzforce:feature:pdp:request-preproc:default-lax* and *urn:ow2:authzforce:feature:pdp:request-preproc:default-strict*: supports only XACML Request elements marked as *mandatory* in `XACML 3.0 Core specification (§10.2.1) <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#_Toc325047227>`_ (in particular, **no** support for Multiple Decision Profile);
   * *urn:ow2:authzforce:feature:pdp:request-preproc:multiple:repeated-attribute-categories-lax* and *urn:ow2:authzforce:feature:pdp:request-preproc:multiple:repeated-attribute-categories-strict*: Provides the functionality identified by *urn:oasis:names:tc:xacml:3.0:profile:multiple:repeated-attribute-categories* in `XACML v3.0 Multiple Decision Profile Version 1.0 (§3.3) <http://docs.oasis-open.org/xacml/3.0/multiple/v1.0/cs02/xacml-3.0-multiple-v1.0-cs02.html#_Toc388943334>`_
   
  **Only one request preprocessor may be enabled at at time.** 

* Types ``urn:ow2:authzforce:feature-type:pdp:data-type`` and ``urn:ow2:authzforce:feature-type:pdp:function``: PDP extensions providing *non-core* XACML data types and functions respectively, i.e. not specified in XACML 3.0 Core standard §10.2.7 and §10.2.8 respectively. More information in next section `PDP Extensions`_.

 
Follow the example of request/response below to get the current PDP properties in domain ``iMnxv7sDEeWFwqVFFMDLTQ``:

.. code-block:: xml
   :linenos:

   GET /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/pdp.properties
   Accept: application/xml
   
   -
   
   HTTP/1.1 200 OK
   Content-Type: application/xml
 
   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <pdpProperties 
    xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5"
    lastModifiedTime="2016-05-28T14:21:35.730Z">
    <feature 
     type="urn:ow2:authzforce:feature-type:pdp:core" 
     enabled="false">urn:ow2:authzforce:feature:pdp:core:strict-attribute-issuer-match</feature>
    <feature 
     type="urn:ow2:authzforce:feature-type:pdp:request-preproc" 
     enabled="true">urn:ow2:authzforce:feature:pdp:request-preproc:default-lax</feature>
    <feature 
     type="urn:ow2:authzforce:feature-type:pdp:request-preproc" 
     enabled="false">urn:ow2:authzforce:feature:pdp:request-preproc:default-strict</feature>
    <feature 
     type="urn:ow2:authzforce:feature-type:pdp:request-preproc" 
     enabled="false">urn:ow2:authzforce:feature:pdp:request-preproc:multiple:repeated-attribute-categories-strict</feature>
    <feature 
     type="urn:ow2:authzforce:feature-type:pdp:request-preproc" 
     enabled="false">urn:ow2:authzforce:feature:pdp:request-preproc:multiple:repeated-attribute-categories-lax</feature>
    ...(content omitted)...
    <rootPolicyRefExpression>root</rootPolicyRefExpression>
    <applicablePolicies>
     <rootPolicyRef Version="0.1.0">root</rootPolicyRef>
     <refPolicyRef Version="1.0">PPS:Employee</refPolicyRef>
     <refPolicyRef Version="1.0">PPS:Manager</refPolicyRef>
     ...(content omitted)...
    </applicablePolicies>
   </pdpProperties>  

As you can see, the GET response provides extra information such as:

* ``lastModifiedTime``: the last time the PDP was reloaded (due to a change of root policy for instance);
* ``applicablePolicies``: the actual root policy (``rootPolicyRef`` element) version selected for evaluation according to the ``rootPolicyRefExpression``, and any policy referenced from it ((``refPolicyRef`` elements) directly or indirectly via ``PolicySetIdReference``.
 
The HTTP PUT request to update the PDP properties goes as follows:

.. code-block:: xml
   :linenos:

   PUT /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/pdp.properties
   Content-Type: application/xml
 
   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <pdpPropertiesUpdate xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5">
    <feature 
     type="urn:ow2:authzforce:feature-type:pdp:request-preproc" 
     enabled="true">urn:ow2:authzforce:feature:pdp:request-preproc:multiple:repeated-attribute-categories-lax</feature>
    <rootPolicyRefExpression>root</rootPolicyRefExpression>
   </pdpPropertiesUpdate>

This example sets the root policy reference to the latest version of the policy with ``PolicySetId = 'root'`` that must exist in the domain (see `Adding and updating Policies`_), and enables support for the XACML Multiple Decision profile with repeated attribute categories (*urn:oasis:names:tc:xacml:3.0:profile:multiple:repeated-attribute-categories*). Notice that only one feature element in the request although it is not the only one PDP feature. In this case, the API assumes that all features missing from the request must be disabled. Therefore, it is only necessary to send the **enabled** features in the request.


PDP Extensions
++++++++++++++

Non-core (not defined in XACML 3.0 Core standard) PDP behavior and features may be implemented by various types of extensions, particularly to support specific XACML Profiles:

* Attribute Datatypes: to support extra XACML datatypes, e.g. from DLP/NAC Profile;
* Functions: to support extra XACML functions, e.g. from DLP/NAC Profile;
* Attribute Providers: to customize the way attribute value are retrieved outside the PEP's Request.

.. * Request preprocessor: to customize the processing of individual decision requests;
.. * Combining algorithms: Additional alg profile

Attribute Datatype extensions
#############################

The XACML 3.0 Core standard allows to use extra attribute data types not defined in the standard. Before you can use such datatypes in Authzforce API, you must implement and provide it as an Attribute Datatype extension, or get it from a third party as such; and then you deploy it on Authzforce server and enable it on a specific domain. The AuthzForce project also provides a separate Datatype extension example for documentation and testing purposes. If you wish to make your own Attribute Datatype extension, read on the next section. If you wish to test the example provided by AuthzForce or if you have another one ready for use, you may jump to the section `Integrating an Attribute Datatype extension into AuthzForce Server`_.

Making an Attribute Datatype extension
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The steps to make your own Attribute Datatype extension for AuthzForce go as follows:

#. Create a Maven project with ``jar`` packaging type and following Maven dependency:

   .. code-block:: xml
      :linenos:
   
      ... <dependencies>
       <dependency>
        <groupId>org.ow2.authzforce</groupId> <artifactId>authzforce-ce-core-pdp-api</artifactId> <version>12.1.0</version>
       </dependency>
      ... </dependencies> ...

#. Create your attribute datatype factory and value instance class (as in the *Factory* design pattern). The factory class must be public, and implement interface ``org.ow2.authzforce.core.pdp.api.value.DatatypeFactory<AV>``, where ``AV`` stands for your *AttributeValue Implementation Class*, i.e. the concrete attribute value implementation class; and the factory class must have a public no-argument constructor or no constructor.
   
   To facilitate the implementation process, instead of implementing this ``DatatypeFactory`` interface directly, you should extend one of the following ``DatatypeFactory`` sub-classes when it applies:
   
   * ``org.ow2.authzforce.core.pdp.api.value.SimpleValue.StringContentOnlyFactory<AV>``: to be extended for implementing text-only primitive datatypes (equivalent to simple XML types). You may use `AuthzForce TestDNSNameWithPortValue class <https://github.com/authzforce/core/blob/release-5.0.2/src/test/java/org/ow2/authzforce/core/test/custom/TestDNSNameWithPortValue.java>`_ (used for AuthzForce unit tests) as an example. This example provides a test implementation of datatype ``dnsName-value`` defined in `XACML Data Loss Prevention / Network Access Control (DLP/NAC) Profile Version 1.0 <http://docs.oasis-open.org/xacml/xacml-3.0-dlp-nac/v1.0/xacml-3.0-dlp-nac-v1.0.html>`_. In this example, the static nested class ``Factory`` is the one extending ``org.ow2.authzforce.core.pdp.api.value.SimpleValue.StringContentOnlyFactory<TestDNSNameWithPortValue>``. Such a class has a factory method (``TestDNSNameWithPortValue getInstance(String val)``) that takes a string argument corresponding to the text in the XACML AttributeValue (which must not contain any XML element or attribute).
   * ``org.ow2.authzforce.core.pdp.api.value.SimpleValue.Factory<AV>``: to be extended for implementing primitive XACML datatypes with XML attributes (equivalent to complex XML types with simple content). An example of such datatype is ``xpathExpression`` which requires an XML attribute named ``XPathCategory``. Note that the datatype ``xpathExpression`` is natively supported but enabled only if feature ``urn:ow2:authzforce:feature:pdp:core:xpath-eval`` is enabled, as mentioned in section `Policy Decision (PDP) Properties`_.
   * ``org.ow2.authzforce.core.pdp.api.value.BaseDatatypeFactory<AV>``: to be extended for implementing `structured attributes (XACML 3.0 Core, §8.2) <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#_Toc325047203>`_ (equivalent to complex XML types with complex content). You may use `AuthzForce TestXACMLPolicyAttributeValue class <https://github.com/authzforce/core/blob/release-5.0.2/src/test/java/org/ow2/authzforce/core/test/custom/TestXACMLPolicyAttributeValue.java>`_ (used for AuthzForce unit tests) as an example. In this example, the static nested class ``Factory`` is the one extending ``org.ow2.authzforce.core.pdp.api.value.BaseDatatypeFactory<TestXACMLPolicyAttributeValue>``. Such a class has a factory method ``TestXACMLPolicyAttributeValue getInstance(List<Serializable> content, Map<QName, String> otherAttributes, ...)`` that creates an instance of your *AttributeValue Implementation Class*, i.e. ``TestXACMLPolicyAttributeValue`` in this case. where the argument ``otherAttributes`` represents the XML attributes and argument ``content`` the mixed content of a XACML AttributeValue `parsed by JAXB <https://jaxb.java.net/tutorial/section_2_2_12_7-Mixed-Content.html>`_. 

#. When your implementation class is ready, create a text file ``org.ow2.authzforce.core.pdp.api.PdpExtension`` in folder ``src/main/resources/META-INF/services`` (you have to create the folder first) and put the fully qualified name of your implementation class on the first line of this file, like in the `example from Authzforce source code <https://github.com/authzforce/core/blob/release-5.0.2/src/test/resources/META-INF/services/org.ow2.authzforce.core.pdp.api.PdpExtension>`_.
   
#. Run Maven ``package`` to produce a JAR from the Maven project.

Now you have an Attribute Datatype extension ready for integration into AuthzForce Server, as explained in the next section.

Integrating an Attribute Datatype extension into AuthzForce Server
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

This section assumes you have an Attribute Datatype extension in form of a JAR, typically produced by the process described in the previous section. You may use AuthzForce PDP Core Tests JAR if you only wish to test the examples in this documentation. This JAR is `available on Maven Central <http://central.maven.org/maven2/org/ow2/authzforce/authzforce-ce-core-pdp-testutils/10.1.0/authzforce-ce-core-pdp-testutils-10.1.0.jar>`_.

The steps to integrate the extension into the AuthzForce Server go as follows:

#. Make the JAR - and any extra dependency - visible from the AuthzForce webapp in Tomcat. One way to do it consists to copy the JAR (e.g. ``authzforce-ce-core-pdp-testutils-10.1.0.jar`` in our example) into ``/opt/authzforce-ce-server/webapp/WEB-INF/lib``. For other ways, please refer to `Tomcat HowTo <http://wiki.apache.org/tomcat/HowTo#How_do_I_add_JARs_or_classes_to_the_common_classloader_without_adding_them_to_.24CATALINA_HOME.2Flib.3F>`_.

#. Finally, restart Tomcat to apply changes.

Enabling an Attribute Datatype extension on a domain
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Once you have deployed the extension on Authzforce, following previous instructions, you are ready to enable it on a specific domain's PDP by updating the PDP properties with an enabled ``feature`` of type ``urn:ow2:authzforce:feature-type:pdp:data-type`` and value equal to the ID returned by the method ``getId()`` of the extension's factory implementation class. The following example enables the datatype ``dnsName-value`` (defined in DLP/NAC profile) on the PDP, provided that the AuthzForce PDP Core Tests JAR has been deployed (see previous section):

.. code-block:: xml
   :linenos:

   PUT /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/pdp.properties
   Content-Type: application/xml
 
   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <pdpPropertiesUpdate xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5">
    <feature 
     type="urn:ow2:authzforce:feature-type:pdp:data-type" 
     enabled="true">urn:oasis:names:tc:xacml:3.0:data-type:dnsName-value</feature>
    <rootPolicyRefExpression>root</rootPolicyRefExpression>
   </pdpPropertiesUpdate>


Function Extensions
###################

The XACML 3.0 Core standard allows to use extra functions not defined in the standard. Before you can use such functions in Authzforce API, you must implement and provide it as an Function extension, or get it from a third party as such; and then you deploy it on Authzforce server and enable it on a specific domain. The AuthzForce project also provides a separate Function extension example for documentation and testing purposes. If you wish to make your own Function extension, read on the next section. If you wish to test the example provided by AuthzForce or if you have another one ready for use, you may jump to the section `Integrating a Function extension into AuthzForce Server`_.

Making a Function extension
^^^^^^^^^^^^^^^^^^^^^^^^^^^

The steps to make your own Function extension go as follows:

#. Create a Maven project with ``jar`` packaging type and following Maven dependency:

   .. code-block:: xml
      :linenos:
   
      ... <dependencies>
       <dependency>
        <groupId>org.ow2.authzforce</groupId> <artifactId>authzforce-ce-core-pdp-api</artifactId> <version>7.1.1</version>
       </dependency> ...
      </dependencies> ...

#. If you want to implement one/some/all of the equivalent of XACML 3.0 standard bag functions (§A.3.10) or set functions (§A.3.11) for a new attribute datatype (provided by an Attribute Datatype extension), create a Java class either extending class ``org.ow2.authzforce.core.pdp.api.func.BaseFunctionSet`` or, as second resort, implementing interface ``org.ow2.authzforce.core.pdp.api.func.FunctionSet``, and, in either case, use ``org.ow2.authzforce.core.pdp.api.func.FirstOrderBagFunctions#getFunctions(DatatypeFactory<AV>)`` to create all the bag functions from the new attribute datatype factory. 
   
   Else create a Java class either extending class ``org.ow2.authzforce.core.pdp.api.func.BaseFunction`` or, as second resort, implementing interface ``org.ow2.authzforce.core.pdp.api.func.Function``; this class must have a public no-argument constructor or no constructor. Instead of implementing this ``Function`` interface directly, you should extend one of the following ``Function`` sub-classes when it applies:
   
   * ``org.ow2.authzforce.core.pdp.api.func.ComparisonFunction``: to be extended for implementing comparison functions ``type-greater-than``, ``type-greater-than-or-equal``, ``type-less-than`` and ``type-less-than-or-equal``. Examples from XACML 3.0 Core standard: see §A.3.6 and §A.3.8.
   * ``org.ow2.authzforce.core.pdp.api.func.EqualTypeMatchFunction``: to be extended for implementing match functions with two parameters of same type`. Examples from XACML 3.0 Core standard: equality functions in §A.3.1, ``x500name-match``, ``string-starts-with``. You may use `AuthzForce TestDNSNameValueEqualFunction class <https://github.com/authzforce/core/blob/release-5.0.2/src/test/java/org/ow2/authzforce/core/test/custom/TestDNSNameValueEqualFunction.java>`_ (used for AuthzForce unit tests) as an example. This example provides a test implementation of function ``dnsName-value-equal`` defined in `XACML Data Loss Prevention / Network Access Control (DLP/NAC) Profile Version 1.0 <http://docs.oasis-open.org/xacml/xacml-3.0-dlp-nac/v1.0/xacml-3.0-dlp-nac-v1.0.html>`_. 
   * ``org.ow2.authzforce.core.pdp.api.func.NonEqualTypeMatchFunction``: to be extended for implementing match functions with two parameters of different type. Examples from XACML 3.0 Core standard: ``rfc822Name-match``, ``anyURI-starts-with``, ``dnsName-regexp-match``.
   * ``org.ow2.authzforce.core.pdp.api.func.HigherOrderBagFunction``: to be extended for implementing higher-order bag functions. Examples from XACML 3.0 Core standard are functions in §A.3.12.
   * ``org.ow2.authzforce.core.pdp.api.func.FirstOrderFunction.SingleParameterTyped``: to be extended for implementing first-order functions having all parameters of the same type, when previous cases do not apply. Examples from XACML 3.0 Core standard are logical ``and``, ``or`` or ``not`` in §A.3.5.
   * ``org.ow2.authzforce.core.pdp.api.func.FirstOrderFunction.MultiParameterTyped``: to be extended for implementing first-order functions having at least two different types of parameters, when previous cases do not apply. Examples from XACML 3.0 Core standard are logical ``n-of`` and ``*-substring`` functions.
   * ``org.ow2.authzforce.core.pdp.api.func.FirstOrderFunction.BaseFunction``: to be extended for implementing functions when none of the previous cases apply.

#. When your implementation class is ready, create a text file ``org.ow2.authzforce.core.pdp.api.PdpExtension`` in folder ``src/main/resources/META-INF/services`` (you have to create the folder first) and put the fully qualified name of your implementation class on the first line of this file, like in the `example from Authzforce source code <https://github.com/authzforce/core/blob/release-5.0.2/src/test/resources/META-INF/services/org.ow2.authzforce.core.pdp.api.PdpExtension>`_.
   
#. Run Maven ``package`` to produce a JAR from the Maven project.

Now you have a Function extension ready for integration into AuthzForce Server, as explained in the next section.

Integrating a Function extension into AuthzForce Server
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

This section assumes you have a Function extension in form of a JAR, typically produced by the process described in the previous section. You may use AuthzForce PDP Core Tests JAR if you only wish to test the examples in this documentation. This JAR is `available on Maven Central <http://repo1.maven.org/maven2/org/ow2/authzforce/authzforce-ce-core/5.0.2/authzforce-ce-core-5.0.2-tests.jar>`_.

The steps to integrate the extension into the AuthzForce Server go as follows:

#. Make the JAR - and any extra dependency - visible from the AuthzForce webapp in Tomcat. One way to do it consists to copy the JAR (e.g. ``authzforce-ce-core-5.0.2-tests.jar`` in our example) into ``/opt/authzforce-ce-server/webapp/WEB-INF/lib``. For other ways, please refer to `Tomcat HowTo <http://wiki.apache.org/tomcat/HowTo#How_do_I_add_JARs_or_classes_to_the_common_classloader_without_adding_them_to_.24CATALINA_HOME.2Flib.3F>`_.

#. Finally, restart Tomcat to apply changes.

Enabling a Function extension on a domain
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Once you have deployed the extension on Authzforce, following previous instructions, you are ready to enable it on a specific domain's PDP by updating the PDP properties with an enabled ``feature`` of type ``urn:ow2:authzforce:feature-type:pdp:function-set`` if the extension extends ``BaseFunctionSet`` class or implements directly its superinterface ``FunctionSet``; else use the feature type ``urn:ow2:authzforce:feature-type:pdp:function``, and value equal to the ID returned by the method ``getId()`` of the extension implementation class. The following example enables the function ``dnsName-value-equal`` and required datatype ``dnsName-value`` (defined in DLP/NAC profile) on the PDP, provided that the AuthzForce PDP Core Tests JAR has been deployed (see previous section):

.. code-block:: xml
   :linenos:

   PUT /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/pdp.properties
   Content-Type: application/xml
 
   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <pdpPropertiesUpdate xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5">
    <feature 
     type="urn:ow2:authzforce:feature-type:pdp:data-type" 
     enabled="true">urn:oasis:names:tc:xacml:3.0:data-type:dnsName-value</feature>
    <feature 
     type="urn:ow2:authzforce:feature-type:pdp:data-type" 
     enabled="true">urn:oasis:names:tc:xacml:3.0:data-type:dnsName-value-equal</feature>
    <rootPolicyRefExpression>root</rootPolicyRefExpression>
   </pdpPropertiesUpdate>


Combining Algorithm Extensions
##############################

The XACML 3.0 Core standard allows to use extra policy/rule combining algorithms not defined in the standard. Before you can use such algorithms in Authzforce API, you must implement and provide it as an Combining Algorithm extension, or get it from a third party as such; and then you deploy it on Authzforce server and enable it on a specific domain. The AuthzForce project also provides a separate Combining Algorithm extension example for documentation and testing purposes. If you wish to make your own Combining Algorithm extension, read on the next section. If you wish to test the example provided by AuthzForce or if you have another one ready for use, you may jump to the section `Integrating a Combining Algorithm extension into AuthzForce Server`_.

Making a Combining Algorithm extension
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The steps to make your own Combining Algorithm extension go as follows:

#. Create a Maven project with ``jar`` packaging type and following Maven dependency:

   .. code-block:: xml
      :linenos:
   
      ... <dependencies>
       <dependency>
        <groupId>org.ow2.authzforce</groupId> <artifactId>authzforce-ce-core-pdp-api</artifactId> <version>7.1.1</version>
       </dependency> ...
      </dependencies> ...

#. Create the Java implementation class, either extending class *org.ow2.authzforce.core.pdp.api.combining.BaseCombiningAlg<D>* or, as second resort, implementing interface *org.ow2.authzforce.core.pdp.api.combining.CombiningAlg<D>*, where the type parameter ``D`` represents the type of elements combined by the algorithm implementation (policy or rule), more precisely ``D`` must be one of the following:
   
   * ``org.ow2.authzforce.core.pdp.api.Decidable`` (recommended option) for a policy/rule combining algorithm implementation, i.e. combining policies and rules equally. For example, although the XACML standard specifies two distinct identifiers for the policy combining version and rule combining version of the *deny-unless-permit* algorithm, the normative algorithm specification in pseudo-code is the same, and is actually implemented by one single Java class in AuthzForce. We strongly recommend this type parameter for your implementation as it makes it more generic and maximizes its reuse.  
   * ``org.ow2.authzforce.core.pdp.api.policy.PolicyEvaluator`` for a policy-only combining algorithm, e.g. the XACML Core standard *only-one-applicable* algorithm, or the *on-permit-apply-second* policy combining algorithm from `XACML 3.0 Additional Combining Algorithms Profile Version 1.0 <http://docs.oasis-open.org/xacml/xacml-3.0-combalgs/v1.0/xacml-3.0-combalgs-v1.0.html>`_. You may use `AuthzForce TestOnPermitApplySecondCombiningAlg class <https://github.com/authzforce/core/blob/release-5.0.2/src/test/java/org/ow2/authzforce/core/test/custom/TestOnPermitApplySecondCombiningAlg.java>`_ (used for AuthzForce unit tests) as an example of implementation for this algorithm.
    
   This class must have a public no-argument constructor or no constructor.

#. When your implementation class is ready, create a text file ``org.ow2.authzforce.core.pdp.api.PdpExtension`` in folder ``src/main/resources/META-INF/services`` (you have to create the folder first) and put the fully qualified name of your implementation class on the first line of this file, like in the `example from Authzforce source code <https://github.com/authzforce/core/blob/release-5.0.2/src/test/resources/META-INF/services/org.ow2.authzforce.core.pdp.api.PdpExtension>`_.
   
#. Run Maven ``package`` to produce a JAR from the Maven project.

Now you have a Combining Algorithm extension ready for integration into AuthzForce Server, as explained in the next section.

Integrating a Combining Algorithm extension into AuthzForce Server
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

This section assumes you have a Combining Algorithm extension in form of a JAR, typically produced by the process described in the previous section. You may use AuthzForce PDP Core Tests JAR if you only wish to test the examples in this documentation. This JAR is `available on Maven Central <http://repo1.maven.org/maven2/org/ow2/authzforce/authzforce-ce-core/5.0.2/authzforce-ce-core-5.0.2-tests.jar>`_.

The steps to integrate the extension into the AuthzForce Server go as follows:

#. Make the JAR - and any extra dependency - visible from the AuthzForce webapp in Tomcat. One way to do it consists to copy the JAR (e.g. ``authzforce-ce-core-5.0.2-tests.jar`` in our example) into ``/opt/authzforce-ce-server/webapp/WEB-INF/lib``. For other ways, please refer to `Tomcat HowTo <http://wiki.apache.org/tomcat/HowTo#How_do_I_add_JARs_or_classes_to_the_common_classloader_without_adding_them_to_.24CATALINA_HOME.2Flib.3F>`_.

#. Finally, restart Tomcat to apply changes.

Enabling a Combining Algorithm extension on a domain
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Once you have deployed the extension on Authzforce, following previous instructions, you are ready to enable it on a specific domain's PDP by updating the PDP properties with an enabled ``feature`` of type ``urn:ow2:authzforce:feature-type:pdp:combining-algorithm``. The following example enables the combining algorithm ``on-permit-apply-second`` on the PDP, provided that the AuthzForce PDP Core Tests JAR has been deployed (see previous section):

.. code-block:: xml
   :linenos:

   PUT /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/pdp.properties
   Content-Type: application/xml
 
   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <pdpPropertiesUpdate xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5">
    <feature 
     type="urn:ow2:authzforce:feature-type:pdp:combining-algorithm" 
     enabled="true">urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:on-permit-apply-second</feature>
    <rootPolicyRefExpression>root</rootPolicyRefExpression>
   </pdpPropertiesUpdate>


Request Filter Extensions
#########################

With AuthzForce *Request Filter* extensions, you can customize the way XACML ``<Request>`` elements are processed before they are evaluated by the PDP against policies. Before you can use such extensions in Authzforce API, you must implement one or get it from a third party as such; and then you deploy it on Authzforce server and enable it on a specific domain. Beware that AuthzForce already provides a Request Filter implementing the functionality identified by *urn:oasis:names:tc:xacml:3.0:profile:multiple:repeated-attribute-categories* in `XACML v3.0 Multiple Decision Profile Version 1.0 (§3.3)`_. More information in section `Policy Decision (PDP) Properties`_. If you wish to make your own Request Filter extension, read on the next section. If you wish to test the example provided by AuthzForce or if you have another one ready for use, you may jump to the section `Integrating a Request Filter extension into AuthzForce Server`_.

Making a Request Filter extension
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The steps to make your own Request Filter extension for AuthzForce go as follows:

#. Create a Maven project with ``jar`` packaging type and following Maven dependency:

   .. code-block:: xml
      :linenos:
   
      ... <dependencies>
       <dependency>
        <groupId>org.ow2.authzforce</groupId> <artifactId>authzforce-ce-core-pdp-api</artifactId> <version>7.1.1</version>
       </dependency> ...
      </dependencies> ...

#. Create a Java class implementing interface ``org.ow2.authzforce.core.pdp.api.RequestFilter.Factory``.    This class must have a public no-argument constructor or no constructor. This factory class's main goal is to create instances of ``org.ow2.authzforce.core.pdp.api.RequestFilter``. As the latter is an interface, you need a concrete subclass for your implementation. Instead of implementing the interface ``RequestFilter`` directly to do so, you should extend class ``org.ow2.authzforce.core.pdp.api.BaseRequestFilter`` to facilitate the process whenever possible. You may use AuthzForce `DefaultRequestFilter.LaxFilterFactory (resp. DefaultRequestFilter.StrictFilterFactory) class <https://github.com/authzforce/core/blob/release-5.0.2/src/main/java/org/ow2/authzforce/core/pdp/impl/DefaultRequestFilter.java>`_ as an example for *-lax* (resp. *-strict*) request preprocessor. This class implements the minimal XACML 3.0 Core-compliant request preprocessor identified by ``urn:ow2:authzforce:feature:pdp:request-preproc:default-lax`` (resp. ``urn:ow2:authzforce:feature:pdp:request-preproc:default-strict``). For more information on this request preprocessor and *-lax* versus *-strict*, please refer to section `Policy Decision (PDP) Properties`_. 

#. When your implementation class is ready, create a text file ``org.ow2.authzforce.core.pdp.api.PdpExtension`` in folder ``src/main/resources/META-INF/services`` (you have to create the folder first) and put the fully qualified name of your implementation class on the first line of this file, like in the `example from Authzforce source code <https://github.com/authzforce/core/blob/release-5.0.2/src/test/resources/META-INF/services/org.ow2.authzforce.core.pdp.api.PdpExtension>`_.
   
#. Run Maven ``package`` to produce a JAR from the Maven project.

Now you have a Request Filter extension ready for integration into AuthzForce Server, as explained in the next section.

Integrating a Request Filter extension into AuthzForce Server
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

This section assumes you have a Request Filter extension in form of a JAR, typically produced by the process described in the previous section. The steps to integrate the extension into the AuthzForce Server go as follows:

#. Make the JAR - and any extra dependency - visible from the AuthzForce webapp in Tomcat. One way to do it consists to copy the JAR (e.g. ``authzforce-ce-core-5.0.2-tests.jar`` in our example) into ``/opt/authzforce-ce-server/webapp/WEB-INF/lib``. For other ways, please refer to `Tomcat HowTo <http://wiki.apache.org/tomcat/HowTo#How_do_I_add_JARs_or_classes_to_the_common_classloader_without_adding_them_to_.24CATALINA_HOME.2Flib.3F>`_.

#. Finally, restart Tomcat to apply changes.

Enabling a Request Filter extension on a domain
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Once you have deployed the extension on Authzforce, following previous instructions, you are ready to enable it on a specific domain's PDP by updating the PDP properties with an enabled ``feature`` of type ``urn:ow2:authzforce:feature-type:pdp:request-preproc`` and value equal to the ID returned by the method ``getId()`` of the extension's factory implementation class. Please refer to `Policy Decision (PDP) Properties`_ for examples.


Result Filter Extensions
########################

With AuthzForce *Result Filter* extensions, you can customize the way the PDP's decision ``<Result>`` elements are processed before making the final XACML ``<Response>`` returned to the client, e.g. PEPs. Before you can use such extensions in Authzforce API, you must implement one or get it from a third party as such; and then you deploy it on Authzforce server and enable it on a specific domain. The AuthzForce project also provides a separate Result Filter extension example for documentation and testing purposes. If you wish to make your own Result Filter extension, read on the next section. If you wish to test the example provided by AuthzForce or if you have another one ready for use, you may jump to the section `Integrating a Result Filter extension into AuthzForce Server`_.

Making a Result Filter extension
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The steps to make your own Result Filter extension go as follows:

#. Create a Maven project with ``jar`` packaging type and following Maven dependency:
  
   .. code-block:: xml
      :linenos:
    
      ... <dependencies>
       <dependency>
        <groupId>org.ow2.authzforce</groupId> <artifactId>authzforce-ce-core-pdp-api</artifactId> <version>7.1.1</version>
       </dependency> ...
      </dependencies> ...

#. Create a Java implementation class implementing interface *org.ow2.authzforce.core.pdp.api.DecisionResultFilter*.    This class must have a public no-argument constructor or no constructor. You may use `AuthzForce TestCombinedDecisionResultFilter class <https://github.com/authzforce/core/blob/release-5.0.2/src/test/java/org/ow2/authzforce/core/test/custom/TestCombinedDecisionResultFilter.java>`_ (used for AuthzForce unit tests) as an example. This example provides a test implementation of feature ``urn:oasis:names:tc:xacml:3.0:profile:multiple:combined-decision`` from `XACML v3.0 Multiple Decision Profile Version 1.0 <http://docs.oasis-open.org/xacml/3.0/multiple/v1.0/xacml-3.0-multiple-v1.0.html>`_. 

#. When your implementation class is ready, create a text file ``org.ow2.authzforce.core.pdp.api.PdpExtension`` in folder ``src/main/resources/META-INF/services`` (you have to create the folder first) and put the fully qualified name of your implementation class on the first line of this file, like in the `example from Authzforce source code <https://github.com/authzforce/core/blob/release-5.0.2/src/test/resources/META-INF/services/org.ow2.authzforce.core.pdp.api.PdpExtension>`_.
   
#. Run Maven ``package`` to produce a JAR from the Maven project.

Now you have a Result Filter extension ready for integration into AuthzForce Server, as explained in the next section.

Integrating a Result Filter extension into AuthzForce Server
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

This section assumes you have a Combining Algorithm extension in form of a JAR, typically produced by the process described in the previous section. You may use AuthzForce PDP Core Tests JAR if you only wish to test the examples in this documentation. This JAR is `available on Maven Central <http://repo1.maven.org/maven2/org/ow2/authzforce/authzforce-ce-core/5.0.2/authzforce-ce-core-5.0.2-tests.jar>`_.

The steps to integrate the extension into the AuthzForce Server go as follows:

#. Make the JAR - and any extra dependency - visible from the AuthzForce webapp in Tomcat. One way to do it consists to copy the JAR (e.g. ``authzforce-ce-core-5.0.2-tests.jar`` in our example) into ``/opt/authzforce-ce-server/webapp/WEB-INF/lib``. For other ways, please refer to `Tomcat HowTo <http://wiki.apache.org/tomcat/HowTo#How_do_I_add_JARs_or_classes_to_the_common_classloader_without_adding_them_to_.24CATALINA_HOME.2Flib.3F>`_.

#. Finally, restart Tomcat to apply changes.

Enabling a Result Filter extension on a domain
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Once you have deployed the extension on Authzforce, following previous instructions, you are ready to enable it on a specific domain's PDP by updating the PDP properties with an enabled ``feature`` of type ``urn:ow2:authzforce:feature-type:pdp:result-postproc``. The following example enables Authzforce combined decision result postprocessor (implementing the feature ``urn:oasis:names:tc:xacml:3.0:profile:multiple:combined-decision`` from `XACML v3.0 Multiple Decision Profile Version 1.0`_ for testing) on the PDP, provided that the AuthzForce PDP Core Tests JAR has been deployed (see previous section):

.. code-block:: xml
   :linenos:

   PUT /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/pdp.properties
   Content-Type: application/xml
 
   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <pdpPropertiesUpdate xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5">
    <feature 
     type="urn:ow2:authzforce:feature-type:pdp:result-postproc" 
     enabled="true">urn:ow2:authzforce:feature:pdp:result-postproc:multiple:test-combined-decision</feature>
    <rootPolicyRefExpression>root</rootPolicyRefExpression>
   </pdpPropertiesUpdate>


Attribute Providers
###################

The API allows to manage PDP attribute providers. These are PDP extensions that enable the PDP to get attributes from other sources than PEPs' requests. Such sources may be remote services, databases, etc. The AuthzForce Server distribution does not provide attribute providers out of the box, but allows you to plug in custom-made one(s) from your own invention or from third parties. The AuthzForce project also provides a separate Attribute Provider example, for testing and documentation purposes only. If you wish to make your own attribute provider, read on the next section. If you wish to test the example provided by AuthzForce or have another one ready for use, you may jump to the section `Integrating an Attribute Provider into AuthzForce Server`_.

Making an Attribute Provider
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The steps to make your own PDP Attribute Provider extension for AuthzForce go as follows:

#. Create a Maven project with ``jar`` packaging type.

#. Create an XML schema file with ``.xsd`` extension in the ``src/main/resources`` folder of your Maven project. Make sure this filename is potentially unique on a Java classpath, like your usual Java class names. One way to make sure is to use a filename prefix following the same conventions as the `Java package naming conventions <https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html>`_. In this schema file, define an XML type for your attribute provider configuration format. This type must extend ``AbstractAttributeProvider`` from namespace ``http://authzforce.github.io/xmlns/pdp/ext/3``. You may use the `schema of AuthzForce Test Attribute Provider <https://github.com/authzforce/core/blob/release-5.0.2/src/test/resources/org.ow2.authzforce.core.test.xsd>`_ (used for AuthzForce unit tests only) as an example. In this example, the XSD filename is ``org.ow2.authzforce.core.test.xsd`` and the defined XML type extending ``AbstractAttributeProvider`` is ``TestAttributeProvider``.

#. Copy the files ``bindings.xjb`` and ``catalog.xml`` `from Authzforce source code <https://github.com/authzforce/core/blob/release-5.0.2/src/main/jaxb>`_ into the ``src/main/jaxb`` folder (you have to create this folder first) of your Maven project.

#. Add the following Maven dependency and build plugin configuration to your Maven POM:
  
   .. code-block:: xml
      :linenos:
 
      ... <dependencies>
       <dependency>
        <groupId>org.ow2.authzforce</groupId> <artifactId>authzforce-ce-core-pdp-api</artifactId> <version>7.1.1</version>
       </dependency> ...
      </dependencies> ...

      <build>
       ... <plugins>
        <plugin>
         <groupId>org.jvnet.jaxb2.maven2</groupId> <artifactId>maven-jaxb2-plugin</artifactId> <version>0.13.0</version> <configuration>
          <debug>false</debug> <strict>false</strict> <verbose>false</verbose> <removeOldOutput>true</removeOldOutput> <extension>true</extension> <useDependenciesAsEpisodes>false</useDependenciesAsEpisodes> <episodes>
           <episode>
            <groupId>org.ow2.authzforce</groupId> <artifactId>authzforce-ce-pdp-ext-model</artifactId> <version>3.4.0</version>
           </episode>
          </episodes> <catalog>src/main/jaxb/catalog.xml</catalog> <bindingDirectory>src/main/jaxb</bindingDirectory> <schemaDirectory>src/main/resources</schemaDirectory>
         </configuration> <executions>
          <execution>
           <id>jaxb-generate-compile-sources</id> <phase>generate-sources</phase> <goals>
            <goal>generate</goal>
           </goals>
          </execution>
         </executions>
        </plugin> ...
       </plugins>
      </build> ...

#. Run Maven ``generate-sources``. This will generate the JAXB-annotated class(es) from the XML schema into the folder ``target/generated-sources/xjc``, one of which corresponds to your attribute provider XML type defined in the second step, therefore has the same name and also extends ``org.ow2.authzforce.xmlns.pdp.ext.AbstractAttributeProvider`` class corresponding to ``AbstractAttributeProvider`` type in the XML schema. For example, in the case of the Authzforce *Test Attribute Provider* aforementioned, the corresponding generated class is ``org.ow2.authzforce.core.xmlns.test.TestAttributeProvider``. In your case and in general, we will refer to it as your *Attribute Provider Model Class*.

#. Create your Attribute Provider factory and concrete implementation class (as in the *Factory* design pattern). The factory class must be public, and extend ``org.ow2.authzforce.core.pdp.api.CloseableAttributeProviderModule.FactoryBuilder<APM>``, where ``APM`` stands for your *Attribute Provider Model Class*; and the factory class must have a public no-argument constructor or no constructor. You may use the `AuthzForce TestAttributeProviderModule class <https://github.com/authzforce/core/blob/release-5.0.2/src/test/java/org/ow2/authzforce/core/test/custom/TestAttributeProviderModule.java>`_ (used for AuthzForce unit tests only) as an example. In this example, the static nested class ``Factory`` is the one extending ``CloseableAttributeProviderModule.FactoryBuilder<TestAttributeProvider>``. Such a class has a factory method ``getInstance(APM configuration)`` (``getInstance(TestAttributeProvider conf)`` in the example) that, from an instance of your ``APM`` representing the XML input (``TestAttributeProvider`` in the example), creates an instance of your Attribute Provider implementation class (``TestAttributeProviderModule`` in the example). Indeed, your Attribute Provider implementation class must implement the interface ``CloseableAttributeProviderModule`` (package ``org.ow2.authzforce.core.pdp.api``). To facilitate the implementation process, instead of implementing this interface directly, you should extend ``BaseAttributeProviderModule`` (same package) in your implementation class, whenever possible. This class already implements the required interface. There are cases where it is not possible; for instance, since ``BaseAttributeProviderModule`` is an abstract class, if your implementation needs to extend another abstract class, you have no choice but to implement the interface directly, because a Java class cannot extend multiple abstract classes. In any case, as mandated by the interface, your implementation class must implement the method ``get(attributeGUID, attributeDatatype, context))`` in charge of actually retrieving the extra attributes (``TestAttributeProviderModule#get(...)`` in the example). The ``attributeGUID`` identifies an XACML attribute category, ID and Issuer that the PDP is requesting from your attribute provider; the ``attributeDatatype`` is the expected attribute datatype; and ``context`` is the request context, including the content from the current XACML Request and possibly extra attributes retrieved so far by other Attribute Providers.

#. When your implementation class is ready, create a text file ``org.ow2.authzforce.core.pdp.api.PdpExtension`` in folder ``src/main/resources/META-INF/services`` (you have to create the folder first) and put the fully qualified name of your implementation class on the first line of this file, like in the `example from Authzforce source code <https://github.com/authzforce/core/blob/release-5.0.2/src/test/resources/META-INF/services/org.ow2.authzforce.core.pdp.api.PdpExtension>`_.
   

#. Run Maven ``package`` to produce a JAR from the Maven project.

Now you have an Attribute Provider extension ready for integration into AuthzForce Server, as explained in the next section.


Integrating an Attribute Provider into AuthzForce Server
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

This section assumes you have an Attribute Provider extension in form of a JAR, typically produced by the process in the previous section. You may use AuthzForce PDP Core Tests JAR if you only wish to test the examples in this documentation. This JAR is `available on Maven Central <http://repo1.maven.org/maven2/org/ow2/authzforce/authzforce-ce-core/5.0.2/authzforce-ce-core-5.0.2-tests.jar>`_.

The steps to integrate the extension into the AuthzForce Server go as follows:

#. Make the JAR - and any extra dependency - visible from the AuthzForce webapp in Tomcat. One way to do it consists to copy the JAR (e.g. ``authzforce-ce-core-5.0.2-tests.jar`` in our example) into ``/opt/authzforce-ce-server/webapp/WEB-INF/lib``. For other ways, please refer to `Tomcat HowTo <http://wiki.apache.org/tomcat/HowTo#How_do_I_add_JARs_or_classes_to_the_common_classloader_without_adding_them_to_.24CATALINA_HOME.2Flib.3F>`_.

#. Import your attribute provider XML schema in the XML schema file ``/opt/authzforce-ce-server/conf/authzforce-ext.xsd``, using ``namespace`` **only** (no ``schemaLocation``), like in the `example from Authzforce code <https://github.com/authzforce/server/blob/release-5.4.1/webapp/src/test/server.conf/authzforce-ce/authzforce-ext.xsd>`_ with this schema import for Authzforce ``TestAttributeProvider``:

   .. code-block:: xml
      :linenos:

      <xs:import namespace="http://authzforce.github.io/core/xmlns/test/3" />

#. Add a ``uri`` element to XML catalog file ``/opt/authzforce-ce-server/conf/catalog.xml``, with your attribute Provider XML namespace as ``name`` attribute value, and, the location of your XML schema file within the JAR, as ``uri`` attribute value, prefixed by ``classpath:``. For example, in the `sample XML catalog from Authzforce source code <https://github.com/authzforce/server/blob/release-5.4.1/webapp/src/test/server.conf/authzforce-ce/catalog.xml>`_, we add the following line for Authzforce ``TestAttributeProvider``:

   .. code-block:: xml
      :linenos:

      <uri 
       name="http://authzforce.github.io/core/xmlns/test/3" uri="classpath:org.ow2.authzforce.core.test.xsd"/>

#. Finally, restart Tomcat to apply changes.

Managing attribute providers configuration
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Once you have deployed a new attribute provider extension on Authzforce, following previous instructions, you are ready to use it on a domain:

* Method: PUT
* Path: ``/domains/{domainId}/pap/attribute.providers``
* Headers:

   * Content-Type: ``application/xml; charset=UTF-8``
   * Accept: ``application/xml; charset=UTF-8``

* Body: new attribute providers.

For example, this request instantiates a specific ``TestAttributeProvider`` configuration on domain ``iMnxv7sDEeWFwqVFFMDLTQ`` (as mentioned in the previous section, ``TestAttributeProvider`` is merely an example for testing and documentation purposes, it is not available in a default installation of Authzforce):

.. code-block:: xml
   :linenos:

   PUT /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/attribute.providers 
   HTTP/1.1 
   Accept: application/xml; charset=UTF-8
   Content-Type: application/xml; charset=UTF-8

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
   <attributeProviders 
    xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5"
    xmlns:xacml="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17"> 
    <attributeProvider 
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
     xmlns:test="http://authzforce.github.io/core/xmlns/test/3"
     xsi:type="test:TestAttributeProvider" id="test"> 
     <xacml:Attributes
      Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject">
      <xacml:Attribute AttributeId="urn:oasis:names:tc:xacml:1.0:example:attribute:role" 
       IncludeInResult="false">
       <xacml:AttributeValue 
        DataType="http://www.w3.org/2001/XMLSchema#string">Physician</xacml:AttributeValue>
      </xacml:Attribute>
     </xacml:Attributes>
    </attributeProvider>
   </attributeProviders>

The response is the new attribute provider configuration from the request.

In this second example, we disable all PDP attribute providers of domain ``iMnxv7sDEeWFwqVFFMDLTQ`` by sending an empty element:

.. code-block:: xml
   :linenos:

   PUT /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/attribute.providers 
   HTTP/1.1 
   Accept: application/xml; charset=UTF-8
   Content-Type: application/xml; charset=UTF-8

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
   <attributeProviders xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5" />

Finally, you may get the current attribute providers anytime as follows:

* Method: GET
* Path: ``/domains/{domainId}/pap/attribute.providers``
* Headers:

    * Accept: ``application/xml; charset=UTF-8``

For example, this request gets the PDP attribute providers of domain ``iMnxv7sDEeWFwqVFFMDLTQ``:

.. code-block:: xml
   :linenos:

   GET /domains/iMnxv7sDEeWFwqVFFMDLTQ/pap/attribute.providers 
   HTTP/1.1 
   Accept: application/xml; charset=UTF-8
   
   <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
   <attributeProviders xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5">
     ...
   </attributeProviders>


Policy Decision API
-------------------

The PDP API returns an authorization decision based on the currently enforced policy, access control attributes provided in the request and possibly other attributes resolved by the PDP itself. The Authorization decision is typically ``Permit`` or ``Deny``. The PDP is able to resolve extra attributes not provided directly in the request, such as the current date/time (environment attribute).

The PDP provides an HTTP RESTful API for requesting authorization decisions, that complies with test assertions ``urn:oasis:names:tc:xacml:3.0:profile:rest:assertion:pdp:xacml:status``, ``urn:oasis:names:tc:xacml:3.0:profile:rest:assertion:pdp:xacml:body`` and ``urn:oasis:names:tc:xacml:3.0:profile:rest:assertion:pdp:xacml:invalid`` of `REST Profile of XACML v3.0 Version 1.0`_. 

The HTTP request must be formatted as follows:

* Method: POST
* Path: ``/domains/{domainId}/pdp``
* Headers:

    * Content-Type: ``application/xml; charset=UTF-8``
    * Accept: ``application/xml; charset=UTF-8``
    
* Body: XACML Request as defined in the XACML 3.0 schema.

The HTTP response body is a XACML Response as defined in the XACML 3.0 schema.

Example of request given below:

.. code-block:: xml
   :linenos:

   POST /domains/iMnxv7sDEeWFwqVFFMDLTQ/pdp 
   HTTP/1.1 
   Accept: application/xml; charset=UTF-8
   Content-Type: application/xml; charset=UTF-8

   <?xml version='1.0' encoding='UTF-8' standalone='yes'?> 
   <Request xmlns="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17"
    CombinedDecision="false" ReturnPolicyIdList="false"> 
    <Attributes 
     Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject"> 
     <Attribute
      AttributeId="urn:oasis:names:tc:xacml:1.0:subject:subject-id"
      IncludeInResult="false"> 
      <AttributeValue 
      DataType="http://www.w3.org/2001/XMLSchema#string">joe</AttributeValue>
     </Attribute> 
     <Attribute AttributeId="urn:oasis:names:tc:xacml:2.0:subject:role" 
      IncludeInResult="false"> <AttributeValue 
      DataType="http://www.w3.org/2001/XMLSchema#string">Manager</AttributeValue>
     </Attribute>
    </Attributes> 
    <Attributes 
     Category="urn:oasis:names:tc:xacml:3.0:attribute-category:resource"> 
     <Attribute
      AttributeId="urn:oasis:names:tc:xacml:1.0:resource:resource-id"
      IncludeInResult="false"> 
      <AttributeValue 
       DataType="http://www.w3.org/2001/XMLSchema#string">MissionManagementApp</AttributeValue>
     </Attribute> 
     <Attribute 
      AttributeId="urn:thales:xacml:2.0:resource:sub-resource-id" IncludeInResult="false"> 
      <AttributeValue
       DataType="http://www.w3.org/2001/XMLSchema#string">Team</AttributeValue>
     </Attribute>
    </Attributes> 
    <Attributes 
     Category="urn:oasis:names:tc:xacml:3.0:attribute-category:action"> 
     <Attribute
      AttributeId="urn:oasis:names:tc:xacml:1.0:action:action-id"
      IncludeInResult="false"> 
      <AttributeValue 
       DataType="http://www.w3.org/2001/XMLSchema#string">manage</AttributeValue>
     </Attribute>
    </Attributes> 
    <Attributes 
     Category="urn:oasis:names:tc:xacml:3.0:attribute-category:environment" />
   </Request>

Response:

.. code-block:: xml
   :linenos:

   HTTP/1.1 200 OK 
   Content-Type: application/xml; charset=UTF-8

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
   <Response xmlns="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17">
    <Result>
        <Decision>Permit</Decision>
    </Result>
   </Response>

If the XACML request was invalid (invalid format), an error 400 is returned.


Fast Infoset
------------

Fast Infoset is an `ITU-T/ISO standard <http://www.itu.int/en/ITU-T/asn1/Pages/Fast-Infoset.aspx>`_ for representing XML (XML Information Set to be accurate) using binary encodings, designed for use cases to provide smaller encoding sizes and faster processing than a W3C XML representation as text. The open source Fast Infoset project provide some `performance results <https://fi.java.net/performance.html>`_ and more information about the `standardisation status <https://fi.java.net/standardization.html>`_. There are several `use cases <http://www.itu.int/en/ITU-T/asn1/Pages/Fast-Infoset.aspx>`_ at the origin of Fast Infoset. A major one comes from the `Web3D <http://www.web3d.org/>`_ consortium that is responsible for open standards in real-time 3D communication, and that `adopted <http://www.web3d.org/documents/specifications/19776-3/V3.3/Part03/concepts.html#Fast-Infoset>`_ Fast Infoset for the serialization and compression of `X3D <http://www.web3d.org/x3d/what-x3d>`_ documents. X3D is a standard for representing 3D scenes and objects using XML.

AuthzForce Server API offers experimental support for Fast Infoset (use with caution). This feature is disabled by default and needs to be enabled explicitly by the administrator as told in the :ref:`adminGuideFastInfoset`. When it is enabled, provided that your API client supports Fast Infoset as well, you may use Fast Infoset on the server API by replacing the media type ``application/xml`` with ``application/fastinfoset`` in your API request headers (*Accept*/*Content-Type*). 


Integration with the IdM and PEP Proxy GEs (e.g. for OAuth)
-----------------------------------------------------------
AuthzForce integrates with the Identity Management (KeyRock) and PEP Proxy GE (Wilma) reference implementations. For an overview of the main interactions, please refer to the Basic and Advanced sections of `Wilma programmer guide <http://fiware-pep-proxy.readthedocs.org/en/latest/user_guide/#level-2-basic-authorization>`_.

After you `installed and configured KeyRock <http://fiware-idm.readthedocs.org/en/latest/admin_guide.html>`_, to connect it to Authzforce, you modify the properties with names prefixed by ``ACCESS_CONTROL_`` in the configuration file ``fiware-idm/horizon/openstack_dashboard/local/local_settings.py`` (`example on KeyRock Github repository <https://github.com/ging/horizon/blob/master/openstack_dashboard/local/local_settings.py.example>`_) according to your AuthzForce instance properties. For example:

.. code-block:: javascript
   :linenos:

   // ACCESS CONTROL GE
   // URL to Authzforce server (http(s)://HOST:PORT)
   ACCESS_CONTROL_URL = 'http://127.0.0.1:8080'
   // Magic key, required only if securing the AZF with a PEP Proxy
   ACCESS_CONTROL_MAGIC_KEY = 'undefined'
 
**WARNING**: If you are using KeyRock v5.3.0 or older, you also have to change the content of IDM's template file ``openstack_dashboard/templates/access_control/policy_properties.xacml`` to this (basically the only change consists to remove the ``ns2`` namespace prefix):

.. code-block:: xml
   :linenos:

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <pdpPropertiesUpdate xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5">
    <rootPolicyRefExpression>{{ policy_id }}</rootPolicyRefExpression>
   </pdpPropertiesUpdate>

Then restart the IdM to apply changes, and go to IdM web interface, and check that the permissions and roles are well configured for your application. You may have to 'trigger' the policy generation in IdM by going to your application > *Manage roles* and click *Save* to trigger the XACML generation. More information in `KeyRock installation and administration guide <http://fiware-idm.readthedocs.org/en/latest/admin_guide.html>`_.

Then, after you `installed and configured Wilma <http://fiware-pep-proxy.readthedocs.org/en/latest/admin_guide/>`_, to connect it to Authzforce, you modify the settings in ``config.azf`` object of configuration file ``config.js`` (`example <https://github.com/ging/fiware-pep-proxy/blob/master/config.js.template>`_) according to your AuthzForce instance properties. More information in `Wilma installation and administration guide <http://fiware-pep-proxy.readthedocs.org/en/latest/admin_guide/>`_.

Software Libraries for clients of AuthzForce or other Authorization PDP GEis
----------------------------------------------------------------------------
The full API (RESTful) is described by a document written in the Web Application Description Language format (WADL) and associated XML schema files available in `Authzforce rest-api-model project files`_. Therefore, you can use any WADL-supporting REST framework for clients; for instance in Java: Jersey, Apache CXF. From that, you can use WADL-to-code generators to generate your client code. For example in Java, 'wadl2java' tools allow to generate code for JAX-RS compatible frameworks such as Apache CXF and Jersey. Actually, we can provide a CXF-based Java library created with this tool to facilitate the development of clients.
