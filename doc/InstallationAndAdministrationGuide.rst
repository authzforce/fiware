=====================================
Installation and Administration Guide
=====================================

This guide provides the procedure to install the `AuthzForce server <https://github.com/authzforce/server>`_, including system requirements and troubleshooting instructions. 

System Requirements
===================

The system requirements are the following:

* CPU frequency: 2.6 GHz min
* CPU architecture: i686/x86_64
* RAM: 4GB min
* Disk space: 10 GB min
* File system: ext4
* Operating System: Ubuntu 16.04 LTS 
* Java environment: 

    * JRE 8 either from OpenJDK or Oracle; 
    * Tomcat 8.x.

Installation
============
If you are already using an older version of AuthzForce and wish to migrate your setup to the new version, 
please backup the folder ``/opt/authzforce-ce-server`` first because it will be overwritten by the new version, then proceed with the `Minimal setup`_ below, to install the new version; 
finally, proceed with the `Upgrade`_ section that follows, to transfer data from the old version.

Minimal setup
-------------

#. Install a JRE 8 if you don't have one already, using either of these two methods depending on your JDK preference:

   * If you prefer OpenJDK: ``$ sudo apt install openjdk-8-jre``
   * If you prefer Oracle JDK, follow the instructions from `WEB UPD8 <http://www.webupd8.org/2012/09/install-oracle-java-8-in-ubuntu-via-ppa.html>`_. 
     In the end, you should have the package ``oracle-java8-installer`` installed.
#. Install Tomcat 8: ``$ sudo apt install tomcat8``.
#. Each AuthzForce Server version number has the form MAJOR.MINOR.PATH (Semantic Versioning). Identify the latest binary (Ubuntu package with ``.deb`` extension) release of AuthzForce Server
   on `Maven Central Repository <http://repo1.maven.org/maven2/org/ow2/authzforce/authzforce-ce-server-dist/>`_ that matches the MAJOR.MINOR version of this documentation. 
   This is the current latest software version to which this documentation version applies. 
   If you want to use a different software version, go to the latest documentation version with matching MAJOR.MINOR and follow instructions there.
   Else you may download the software version. We will refer to its version number as ``M.m.P`` (please replace accordingly):
    
    $ wget http://repo1.maven.org/maven2/org/ow2/authzforce/authzforce-ce-server-dist/M.m.P/authzforce-ce-server-dist-M.m.P.deb
    
    You should get a file called ``authzforce-ce-server-dist-M.m.P.deb``.
#. Copy this file to the host where you want to install the software.
#. On the host, from the directory where you copied this file, run the following commands::

    $ sudo aptitude install gdebi curl
    $ sudo gdebi authzforce-ce-server-dist-M.m.P.deb
#. At the end, you will see a message giving optional instructions to go through. Please follow them as necessary.

Note that Tomcat default configuration may specify a very low value for the Java ``Xmx`` flag, causing the Authzforce webapp startup to fail. 
In that case, make sure Tomcat with ``Xmx`` at 1 Go or more (2 Go recommended). 
You can fix it as follows::
 
 $ sudo sed -i "s/-Xmx128m/-Xmx1024m/" /etc/default/tomcat
 $ sudo service tomcat8 restart
 
**Known issue: lack of entropy may cause delays in Tomcat start up on virtual machines in particular**: 
`more info on Entropy Source issue <https://wiki.apache.org/tomcat/HowTo/FasterStartUp#Entropy_Source>`_. **So beware.**

Upgrade
-------
If you are still using an older version of AuthzForce and wish to migrate your setup to the new version, assuming you made a backup in a separate location, as told previously, please follow these steps:

#. Download AuthzForce server `upgrader distribution (.tar.gz extension) from Maven Central Repository <http://repo1.maven.org/maven2/org/ow2/authzforce/authzforce-ce-server-upgrader/>`_ 
   in the same version as the Server version you want to upgrade to. 
   You get a file called ``authzforce-ce-server-upgrader-M.m.P.tar.gz`` (replace ``M.m.P`` with the corresponding version).
#. Copy this file to the host where the old AuthzForce Server is installed, and unzip it and change directory::

    $ tar xvzf authzforce-ce-server-upgrader-M.m.P.tar.gz
    $ cd authzforce-ce-server-upgrader-M.m.P

#. Follow the instructions in file ``README.html``.

Advanced setup
--------------

The `Minimal setup`_ gave you minimal installation steps to get started testing the features of the GE API. This may be enough for testing purposes, but barely for production. 
If you are targeting a production environment, 
you have to carry out extra installation and configuration steps to address non-functional aspects: security (including availability), performance, etc. 
The Appendix_ also gives some recommendations on what you should do.


Administration
==============

Tomcat
------

For configuring and managing Tomcat, please refer to the `official user guide <http://tomcat.apache.org/tomcat-7.0-doc/index.html>`_.

Authzforce webapp
-----------------

The Authzforce webapp configuration directory is located here: ``/opt/authzforce-ce-server/conf``. 

In particular, the file ``logback.xml`` configures the logging for the webapp (independently from Tomcat). By default, Authzforce-specific logs go to ``/var/log/tomcat8/authzforce-ce/error.log``.

Restart Tomcat to apply any configuration change::
 
   $ sudo service tomcat8 restart


.. _adminGuideFastInfoset:

Fast Infoset mode
-----------------

Fast Infoset is an `ITU-T/ISO standard <http://www.itu.int/en/ITU-T/asn1/Pages/Fast-Infoset.aspx>`_ for representing XML (XML Information Set to be accurate) using binary encodings, 
designed for use cases to provide smaller encoding sizes and faster processing than a W3C XML representation as text. 
The open source Fast Infoset project provide some `performance results <https://fi.java.net/performance.html>`_ and more information about the `standardisation status <https://fi.java.net/standardization.html>`_.
There are several `use cases <http://www.itu.int/en/ITU-T/asn1/Pages/Fast-Infoset.aspx>`_ at the origin of Fast Infoset. 
A major one comes from the `Web3D <http://www.web3d.org/>`_ consortium that is responsible for open standards in real-time 3D communication, 
and that `adopted <http://www.web3d.org/documents/specifications/19776-3/V3.3/Part03/concepts.html#Fast-Infoset>`_ Fast Infoset 
for the serialization and compression of `X3D <http://www.web3d.org/x3d/what-x3d>`_ documents. X3D is a standard for representing 3D scenes and objects using XML.

AuthzForce Server offers experimental support for Fast Infoset (use with caution). This feature is disabled by default. 
To enable Fast Infoset support, change the value of the parameter ``spring.profiles.active`` to ``+fastinfoset`` in the webapp context configuration file ``/etc/tomcat8/Catalina/localhost/authzforce-ce.xml``; 
then restart Tomcat as shown in the previous section in order to apply changes.

.. _adminGuideDomainAdmin:

Policy Domain Administration
----------------------------

The Concept of Policy Domain
++++++++++++++++++++++++++++
                  
The application is multi-tenant, i.e. it allows users or organizations to work on authorization policies in complete isolation from each other. In this document, we use the term *domain* instead of *tenant*. 
In this context, a policy domain consists of:

* Various metadata about the domain: ID assigned by the Authzforce API, external ID (assigned by the provisioning client), description;
* A policy repository;
* Attribute Providers configuration: attribute providers provide attributes that the PEP does NOT directly provide in the XACML <Request>. 
  For example, an attribute provider may get attribute values from an external database. 

The reasons for creating different domains: 

* Users or organizations do not want others to access their data, or even be impacted by others working on the same application.
* The same user or organization may want to work on different domains for different use cases; e.g. work with one policy for production environment, another for testing, another for a specific use case project, etc.

Default Domain Settings
+++++++++++++++++++++++

Administrators can set default settings for all domains to make sure domains are created in a proper configuration according to an administrative policy, or, in more simple terms, the administrator's preferences.
The administrator may change these settings in the various XML files inside the folder ``/opt/authzforce-ce-server/conf/domain.tmpl``:

* ``pdp.xml``:

  * ``maxVariableRefDepth``: optional, positive integer that indicates the maximum depth of Variable reference chaining allowed in policies: ``VariableDefinition`` 1 -> ``VariableDefinition`` 2 -> ..., where *->* 
    represents a `XACML VariableReference <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#_Toc325047129>`_. No limit if undefined. 
    This property applies only to policies loaded by the PDP, i.e. the root policy 
    and policies referenced from it directly or indirectly via `XACML PolicySetIdReference <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#_Toc325047115>`_.
  * ``maxPolicyRefDepth``: optional, positive integer that indicates the maximum depth of Policy(Set) reference chaining: ``PolicySet`` 1 -> ``PolicySet`` 2 -> ... -> ``PolicySet`` N; where *->* 
    represents a `XACML PolicySetIdReference`_. No limit if undefined. This property applies only to policies loaded by the PDP, i.e. the root policy 
    and policies referenced from it directly or indirectly via `XACML PolicySetIdReference`_.
  * ``clientRequestErrorVerbosityLevel``: optional, positive integer (default: 0) that sets the level of detail in the XACML StatusDetail element returned in the Indeterminate Decision Result in case of bad Request (XACML syntax/content is invalid). 
    Increasing this value usually helps better pinpoint the reason why a particular Request was rejected by the XACML parser. 
    This only applies to the content of the HTTP request body (XACML), it does not apply to HTTP-level errors (e.g. bad HTTP headers), 
    in which case you get a HTTP status code 400 without any XACML response since the request is rejected before the body is passed to the XACML parser.

* ``policies/cm9vdA/0.1.0.xml``: the default root `XACML PolicySet <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#_Toc325047106>`_ enforced by the PDP on the domain. 
  As an administrator, you may change the content of this policy on two conditions:
  
  #. You **must not** change the ``PolicySetId``.
  #. If you change the ``Version`` (e.g. to ``1.2.3``), you **must** change the filename prefix (before ``.xsd`` extension) to the same value (e.g. ``1.2.3.xsd``).   

* ``properties.xml``: other domain properties, more specifically:

  * ``maxPolicyCount``: optional, stricly positive integer that indicates the maximum number of policies on a domain, no limit if undefined.
  * ``maxVersionCountPerPolicy``: optional, stricly positive integer that indicates the maximum number of versions per policy, no limit if undefined.
  * ``versionRollingEnabled``: boolean, true if and only if policy versions should be rolled over, i.e. when ``maxVersionCountPerPolicy`` has been reached, oldest versions are automatically removed to make place.
  

Domain Creation
+++++++++++++++

You create a domain by doing a HTTP POST request with XML payload to URL: ``http://${SERVER_NAME}:${PORT}/authzforce-ce/domains``. Replace ``${SERVER_NAME}`` and ``${PORT}`` with your server hostname and port for HTTP. 
You can do it with ``curl`` tool with the the following content in a XML file (``domainProperties.xml`` in this example) as the HTTP request body::

 $ cat domainProperties.xml
 <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
 <domainProperties 
  xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5" 
  externalId="external0"> 
  <description>This is my domain</description> 
 </domainProperties>  
 
 $ curl --verbose --request "POST" \
 --header "Content-Type: application/xml;charset=UTF-8" \
 --data @domainProperties.xml \
 --header "Accept: application/xml" \
   http://${SERVER_NAME}:${PORT}/authzforce-ce/domains
 
 ...
 > POST /authzforce-ce/domains HTTP/1.1
 > Content-Type: application/xml;charset=UTF-8
 > Accept: application/xml
 > Content-Length: 227
 >
 ...
 < HTTP/1.1 200 OK
 < Server: Authorization System
 < Date: Mon, 04 Aug 2016 13:00:12 GMT
 < Content-Type: application/xml
 < Transfer-Encoding: chunked
 <
 <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 <link xmlns="http://www.w3.org/2005/Atom" 
   rel="item" href="h_D23LsDEeWFwqVFFMDLTQ" 
   title="h_D23LsDEeWFwqVFFMDLTQ"/> 

**WARNING**: Mind the leading and trailing single quotes for the ``--data`` argument. Do not use double quotes instead of these single quotes, otherwise curl will remove the double quotes in the XML payload itself, 
and send invalid XML which will be rejected by the server. 
You may use the ``--trace-ascii -`` argument (the last dash here means *stdout*) instead of ``--verbose``, in order to check the actual request body sent by ``curl``. 
So use it only if you need to dump the outgoing (and incoming) data, in particular the request body, on *stdout*.  

The ``href`` value in the response above gives you the domain ID (in the form of a Base64-encoded UUID) assigned by the API. You need this ID for any further operation on the domain.

Domain Removal
++++++++++++++

You remove a domain by doing a HTTP DELETE request with XML payload to URL: ``http://${SERVER_NAME}:${PORT}/authzforce-ce/domains/{domain_ID}``. 
For example with ``curl`` tool::

 $ curl --verbose --request DELETE \
  --header "Content-Type: application/xml;charset=UTF-8" \
  --header "Accept: application/xml" \ 
  http://${SERVER_NAME}:${PORT}/authzforce-ce/domains/h_D23LsDEeWFwqVFFMDLTQ

Policy administration is part of the Authorization Server API, addressed more extensively in the *User and Programmers Guide*.


High Availability
=================

In order to achieve high availability with multiple AuthzForce Server instances (AuthzForce Server cluster), you need to make sure that the following directories are synchronized on all instances:

* Configuration directory: ``/opt/authzforce-ce-server/conf``. 
  This directory is not modified by the API but only by administrators having access to the directory, and any change to it requires restarting Tomcat to apply.  
  Therefore, this directory requires synchronization only after a manual change by a server admin, which should not occur very often. 
  When it occurs, the server administrators may reproduce the changes on each instance manually; or, if there are too many instances for this to be practical, 
  they may use automatic file synchronization solutions, or a distributed filesystems (e.g. NFS) combined with file monitoring solutions. Both kinds of solutions must be capable of executing a specific command, 
  to restart Tomcat in this case, whenever a filesystem change in the directory is detected on a instance node. 
  For example, `csync2 <http://linuxaria.com/howto/csync2-a-filesystem-syncronization-tool-for-linux>`_ is a solution of the first kind that is free and open source.
* Data directory: ``/opt/authzforce-ce-server/data``. This is where the Server API persists and retrieves domain data such as policies.
  Therefore, it is critical to keep this directory synchronized across all the nodes in the high availability cluster, using either file synchronization solutions 
  such as `csync2 <http://linuxaria.com/howto/csync2-a-filesystem-syncronization-tool-for-linux>`_, or distributed file systems such as NFS.
  Besides, for usability and performance reasons, the AuthzForce server caches certain objects in memory such as domains' PDPs and ID-externalId mappings (more info in the *User and Programmers Guide*).
  Therefore, it is also critical to re-sync the AuthzForce Server cache after certain changes done directly by aforementioned solutions to the local data directory. 
  There are two ways to do that:
   
  * **REST API**: you can keep the server in sync with the data directory by calling the following API operations, dependending on the type of change:  
    
    * HEAD ``/domains``: to be used after any global change to the data directory. 
      Inappropriate and largely suboptimal if there are many domains but changes concern only one or a few of them, in which case the next operations should be preferred.
    * HEAD ``/domains/{domainId}``: to be used after a specific domain directory ``/opt/authzforce-ce-server/data/domains/{domainId}`` is created. 
    * DELETE ``/domains/{domainId}``: to be used after a specific domain directory ``/opt/authzforce-ce-server/data/domains/{domainId}`` is deleted.
    * HEAD ``/domains/{domainId}/properties``: to be used after a specific domain's properties file ``/opt/authzforce-ce-server/data/domains/{domainId}/properties.xml`` is modified 
      (especially the ``externalId`` property).
    * HEAD ``/domains/{domainId}/pap/pdp.properties``: to be used after a specific domain's PDP configuration file ``/opt/authzforce-ce-server/data/domains/{domainId}/pdp.xml`` 
      or policies directory ``/opt/authzforce-ce-server/data/domains/{domainId}/policies`` is modified.
        
    In these operations, you may use ``GET`` method instead of ``HEAD`` as well. However, ``HEAD`` is recommended for better performances as it does not return any content (response body), on the contrary to ``GET``.
    Beware that the ``Content-Length`` returned by a ``HEAD`` is still the same as would be returned by the ``GET`` equivalent.
    In any case, if you opt for the file synchronization solution as mentioned earlier, you would have to make it call one of these operations depending on the type of change detected. 
    If you opt for the distributed file system, you would need a file monitoring solution to detect changes and make such calls.
  * **Embedded file monitoring threads**: it is possible to enable file monitoring threads embedded in AuthzForce Server. 
    These threads check for changes to the local data directory periodically, and synchronize the cache automatically. This feature is disabled by default. 
    To enable it, change the value of the parameter ``org.ow2.authzforce.domains.sync.interval`` to a strictly positive integer 
    in the webapp context configuration file ``/etc/tomcat8/Catalina/localhost/authzforce-ce.xml``. 
    The parameter value indicates the period between two checks for changes, in seconds. 
    Beware that this feature creates one extra thread per domain. Therefore, the impact on memory and CPU usage increases with the number of domains.
    Last but not least, **use this feature only on filesystems that support millisecond or higher resolution of file timestamps**, such as ``ext4`` (supports nanosecond resolution).
    Indeed, Authzforce file monitoring threads use file timestamps to detect changes. As a result, if the resolution of the filesystem is coarser than the millisecond, and  
    a file change occurred in less than a second after the last check, it will go undetected (the file's *mtime* timestamp is not updated), and synchronization will not work as expected.
   

Sanity check procedures
=======================
The Sanity Check Procedures are the steps that a System Administrator will take to verify that the installation is ready to be tested. 
This is therefore a preliminary set of tests to ensure that obvious or basic malfunctioning is fixed before proceeding to unit tests, integration tests and user validation.

End to End testing
------------------
To check the proper deployment and operation of the Authorization Server, perform the following steps:

#. Get the list of policy administration domains by doing the following HTTP request, replacing ``${host}`` with the server hostname, and ``${port}`` with the HTTP port of the server, for example with ``curl`` tool::

    $ curl --verbose --show-error --write-out '\n' \
      --request GET http://${host}:${port}/authzforce-ce/domains
#. Check the response which should have the following headers and body (there may be more headers which do not require checking here)::

    Status Code: 200 OK
    Content-Type: application/xml
    
    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <resources 
      xmlns="http://authzforce.github.io/rest-api-model/xmlns/authz/5" 
      xmlns:atom="http://www.w3.org/2005/Atom">
      <atom:link rel="item" href="1XepFknrEea2mQAdYFsFBQ" title="1XepFknrEea2mQAdYFsFBQ"/>
      ... list of links to other policy domains omitted here... 
    </resources>

You can check the exact body format in the representation element of response code 200 for method ``getDomains``, and all other API resources and operations in general, 
in the WADL (Web Application Description Language) document available at the following URL::
 
    http://${host}:${port}/authzforce-ce/?_wadl

List of Running Processes
-------------------------
* One or more ``java`` processes for Tomcat.

Network interfaces Up & Open
----------------------------
* TCP 22;
* TCP 8080.

The port 8080 can be replaced by any other port Tomcat is listening to for HTTP connections to the webapp.

Databases
---------
None.

Diagnosis Procedures
====================
#. Perform the test described in `End to End testing`_.
#. If you get a Connection Refused/Error, check whether Tomcat is started::

    $ sudo service tomcat8 status
#. If status stopped, start Tomcat::

    $ sudo service tomcat8 start
#. If Tomcat fails to start, check for any Tomcat high-level error in Tomcat log directory: ``/var/log/tomcat8``
#. If Tomcat is successfully started (no error in server logs), perform the test described in `End to End testing`_ again.
#. If you still get a Connection Refused/error, check whether Tomcat is not listening on a different port::
   
    $ sudo netstat -lataupen|grep java
#. If you still get a connection refused/error, especially if you are connecting remotely, check whether you are able to connect locally, then check the network link, 
   i.e. whether any network filtering is in place on the host or on the access network, or other network issue: network interface status, DNS/IP adress resolution, routing, etc.
#. If you get an error ``404 Not Found``, make sure the webapp is deployed and enabled in Tomcat. Check for any webapp deployment error in file: ``/var/log/tomcat8/authzforce-ce/error.log``.


Resource availability
---------------------
To have a healthy enabler, the resource requirements listed in `System Requirements`_ must be satisfied, in particular:

* Minimum RAM: 4GB;
* Minimum CPU: 2.6 GHz;
* Minimum Disk space: 10 GB.

Remote Service Access
---------------------
None.

Resource consumption
--------------------
The resource consumption strongly depends on the number of concurrent clients and requests per client, the number of policy domains (a.k.a. tenants in this context) managed by the Authorization Server, 
and the complexity of the policies defined by administrators of each domain.

The memory consumption shall remain under 80% of allocated RAM. See `System Requirements`_ for the minimum required RAM.

The CPU usage shall remain  under 80% of allocated CPU. See `System Requirements`_ for the minimum required CPU.

As for disk usage, at any time, there should be 1GB free space left on the disk.

I/O flows
---------
* HTTPS flows with possibly large XML payloads to port 8443 or whatever port Tomcat is listening to for HTTPS connections to the webapp;
* HTTP flows with possibly large XML payloads to port 8080 or whatever port Tomcat is listening to for HTTP connections to the webapp.


Appendix
========

Security setup for production
-----------------------------
You have to secure the environment of the application server and the server itself. Securing the environment of a server in general will not be addressed here, 
because it is a large subject for which you can find a lot of public documentation. You will learn about perimeter security, network and transport-level security (firewall, IDS/IPS...), OS security, 
application-level security (Web Application Firewall), etc.
For instance, the *NIST Guide to General Server Security* (SP 800-123) is a good start.

Server Security Setup
+++++++++++++++++++++
For more Tomcat-specific security guidelines, please read `Tomcat 8 Security considerations <https://tomcat.apache.org/tomcat-8.0-doc/security-howto.html>`_.

For security of communications (confidentiality, integrity, client/server authentication), it is also recommended to enable SSL/TLS with PKI certificates. 
The first step to set up this is to have your Certification Authority (PKI) issue a server certificate for your AuthzForce instance. 
You can also issue certificates for clients if you want to require client certificate authentication to access the AuthzForce server/API. 
If you don't have such a CA at hand, you can create your own (a basic one) with instructions given in the next section.

Certificate Authority Setup
+++++++++++++++++++++++++++
If you have a CA already, you can skip this section.
So this section is about creating a basic local Certificate Authority (CA) for internal use. This CA will be in charge of issuing certificates to the Authorization Server and clients, 
for authentication, integrity and confidentiality purposes. 
**This procedure requires JDK 1.7 or later.**
(For the sake of simplicity, we do not use a subordinate CA, although you should for production, see `keytool command example <http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html#genkeypairCmd>`_, 
use the ``pathlen`` parameter to restrict number of subordinate CA, ``pathlen=0`` means no subordinate.)

#. Generate the CA keypair and certificate on the platform where the Authorization Server is to be deployed (change the validity argument to your security requirements, example here is 365 days)::

    $ keytool -genkeypair -keystore taz-ca-keystore.jks -alias taz-ca \
      -dname "CN=My Organization CA, O=FIWARE" -keyalg RSA -keysize 2048 \
      -validity 365 -ext bc:c="ca:true,pathlen:0"
#. Export the CA certificate to PEM format for easier distribution to clients::

    $ keytool -keystore taz-ca-keystore.jks -alias taz-ca \
      -exportcert -rfc > taz-ca-cert.pem


Server SSL Certificate Setup
++++++++++++++++++++++++++++
For Tomcat 8, refer to the `Tomcat 8 SSL/TLS Configuration HOW-TO <https://tomcat.apache.org/tomcat-8.0-doc/ssl-howto.html>`_.


Web Application Secutity
++++++++++++++++++++++++

XML Security
************

The AuthzForce web application exposes a XML-based API. Therefore it is vulnerable to XML denial-of-service attacks. 
To mitigate these attacks, there are two solutions:

* **Authzforce native protection**: you can add the following `Environment entries <https://tomcat.apache.org/tomcat-7.0-doc/config/context.html#Environment_Entries>`_ 
  in Authzforce webapp context file ``/etc/tomcat8/Catalina/localhost/authzforce-ce.xml`` (if an entry is absent or its value is negative, the default value is used):

  .. code-block:: xml
   
     <Environment 
      name="org.apache.cxf.stax.maxChildElements"
      description="Maximum number of child elements in an input XML element. Default: 50000." 
      type="java.lang.Integer"
      value="1000" 
      override="false" />
    
     <Environment 
      name="org.apache.cxf.stax.maxElementDepth"
      description="Maximum depth of an element in input XML. Default: 100." 
      type="java.lang.Integer"
      value="100" 
      override="false" />
   
     <!--Following entries are not supported in Fast Infoset mode 
     (more info: https://issues.apache.org/jira/browse/CXF-6848) --> 
     <Environment 
      name="org.apache.cxf.stax.maxAttributeCount"
      description="Maximum number of attributes per element in input XML. Default: 500." 
      type="java.lang.Integer"
      value="100" 
      override="false" />
   
     <Environment 
      name="org.apache.cxf.stax.maxAttributeSize"
      description="Maximum size of a single attribute in input XML. Default: 65536 (= 64*1024)." 
      type="java.lang.Integer"
      value="1000" 
      override="false" />
    
     <Environment 
      name="org.apache.cxf.stax.maxTextLength"
      description="Maximum size of XML text node in input XML. Default: 134217728 (= 128*1024*1024)." 
      type="java.lang.Integer"
      value="1000" 
      override="false" />
    
  Restart Tomcat to apply changes.
* **Dedicated WAF**: for better mitigation, we recommend using a WAF (Web Application Firewall) with XML attack mitigation features in front of the Authzforce server. 

There are `commercial <http://www.dna.com.au/News/Vendor-News/Magic-Quadrant-for-Web-Application-Firewall-Report>`_ 
as well as `open source <https://www.peerlyst.com/posts/resource-a-list-of-open-source-web-application-firewalls-waf-s-s-delano>`_ WAFs available on the market.
However, beware that this solution is not compatible with Fast Infoset, unless the WAF itself supports Fast Infoset. 
Similarly, if you want to use TLS, then the WAF or some proxy in front of it must support TLS to be the TLS server endpoint.

Disabling unused features
*************************

You can disable all PAP features, i.e. make the REST API read-only by setting the ``enablePdpOnly`` `environment entry <https://tomcat.apache.org/tomcat-7.0-doc/config/context.html#Environment_Entries>`_ 
 to ``true`` in Authzforce webapp context file ``/etc/tomcat8/Catalina/localhost/authzforce-ce.xml`` (if an entry is absent or its value is negative, the default value is used):

  .. code-block:: xml
   
     <Environment name="org.ow2.authzforce.domains.enablePdpOnly" value="true" type="java.lang.Boolean" override="false" 
     description="Enable PDP only, i.e. disable all PAP (or other administration) features iff true" />

User and Role Management Setup
++++++++++++++++++++++++++++++
In production, access to the API must be restricted and explicitly authorized. To control which clients can do what on which resources, 
we need to have access to user identity and attributes and assign proper roles to them. These user and role management features are no longer supported by the AuthzForce server itself, 
but should be delegated to the Identity Management GE. 

Domain Role Assignment
++++++++++++++++++++++
In production, access to the API must be restricted and explicitly authorized. To control which clients can do what on what parts of API, 
we need to have access to user identity and attributes and assign proper roles to them. These user role assignment features are no longer supported by the AuthzForce server itself, 
but should be delegated to the Identity Management GE. 

Performance Tuning
------------------
For Tomcat and JVM tuning, we strongly recommend reading and applying - when relevant - the guidelines from the following links:

* `Performance tuning best practices for VMware Apache Tomcat <http://kb.vmware.com/kb/2013486>`_;
* `Tuning Tomcat Performance For Optimum Speed <https://www.mulesoft.com/tcat/tomcat-performance>`_;
* `How to optimize tomcat performance in production <http://www.genericarticles.com/mediawiki/index.php?title=How_to_optimize_tomcat_performance_in_production>`_;
* `Apache Tomcat Tuning Guide for REST/HTTP APIs <https://javamaster.wordpress.com/2013/03/13/apache-tomcat-tuning-guide/>`_.

Last but not least, consider tuning the OS, hardware (CPU, RAM...), network, using load-balancing, high-availability solutions, and so on.
