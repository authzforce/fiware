==================================================
AuthZForce - Installation and Administration Guide
==================================================


This guide provides the procedure to install the AuthZForce server, including system requirements and troubleshooting instructions. 

System Requirements
===================

* CPU frequency: 2.6 GHz min
* CPU architecture: i686/x86_64
* RAM: 4GB min
* Disk space: 10 GB min
* Operating System: Ubuntu 14.04 LTS 
* Java environment (automatically installed with the Ubuntu package): 
    * JDK 7; 
    * Tomcat 7.x.

Installation
============

Minimal
-------

#. Download the binary (Ubuntu package with ``.deb`` extension) release of AuthZForce from `the Github project releases page <https://github.com/authzforce/server/releases/download/release-4.4.0/authzforce-ce-server_4.4.0_all.deb>`_. You get a file called ``authzforce-ce-server_4.4.0_all.deb``.
#. Copy this file to the host where you want to install the software.
#. On the host, from the directory where you copied this file, run the following commands:
    | ``$ sudo aptitude install gdebi curl``
    | ``$ sudo gdebi authzforce-ce-server_4.4.0_all.deb``
#. At the end, you will see a message giving optional instructions to go through. Please follow them as necessary.

Note that Tomcat default configuration may specify a very low value for the Java Xmx flag, causing the authzforce webapp startup to fail. In that case, make sure Tomcat with Xmx at 1Go or more (2 Go recommended). For example, for ubuntu 12.04, Tomcat default Xmx used to be 128m. You can fix it as follows:
| ``$ sudo sed -i "s/-Xmx128m/-Xmx1024m/" /etc/default/tomcat``
| ``$ sudo service tomcat7 restart``

Advanced
--------

The previous section gave you minimal installation steps to get started testing the features of the GE API. This may be enough for testing purposes, but barely for production. If you are targeting a production environment, you have to carry out extra installation and configuration steps to address non-functional aspects: security (including availability), performance, etc. 
The Appendix_ also gives some recommendations on what you should do.

Administration
==============

Tomcat
------

For configuring and managing Tomcat, please refer to the `official user guide <http://tomcat.apache.org/tomcat-7.0-doc/index.html>`_.

Authzforce webapp
-----------------

The Authzforce webapp configuration directory is located here: ``/opt/authzforce-ce-server/conf``. 

In particular, the file ``logback.xml`` configures the logging for the webapp (independently from Tomcat). By default, Authzforce-specific logs go to ``/var/log/tomcat7/authzforce-ce/error.log``.

Restart Tomcat to apply any configuration change:
 ``$ sudo service tomcat7 restart``

Policy Domain Administration
----------------------------

The Concept of Policy Domain
++++++++++++++++++++++++++++
                  
The application is multi-tenant, i.e. it allows users or organizations to work on authorization policies in complete isolation from each other. In this document, we use the term *domain* instead of *tenant*. In this context, a policy domain consists of:

* Various metadata about the domain: ID assigned by the Authzforce API, external ID (assigned by the provisioning client), description, reference to the (root) active policy in the domain;
* A policy repository;
* Attribute Providers configuration: attribute providers provide attributes that the PEP does NOT directly provide in the XACML <Request>. For example, an attribute provider may get attribute values from an external database. 

The reasons for creating different domains: 

* Users or organizations do not want others to access their data, or even be impacted by others working on the same application.
* The same user or organization may want to work on different domains for different use cases; e.g. work with one policyset for production environment, another for testing, another for a specific use case project, etc.

Domain Creation
+++++++++++++++

You create a domain by doing a HTTP POST request with XML payload to URL: ``http://${SERVER_NAME}:${PORT}/authzforce-ce/domains``. Replace ``${SERVER_NAME}`` and ``${PORT}`` with your server hostname and port for HTTP. You can do it with ``curl`` tool::

 $ curl --verbose --trace-ascii - --request POST \ 
 --header "Content-Type: application/xml;charset=UTF-8" \
 --data '<?xml version="1.0" encoding="UTF-8"?><taz:domainProperties xmlns:taz="http://authzforce.github.io/rest-api-model/xmlns/authz/4"> <name>MyDomain</name><description>This is my domain.</description></taz:domainProperties>' \
 --header "Accept: application/xml" http://${SERVER_NAME}:${PORT}/authzforce-ce/domains
 ...
 > POST /authzforce-ce/domains HTTP/1.1
 > User-Agent: curl/7.22.0 (x86_64-pc-linux-gnu) libcurl/7.22.0 OpenSSL/1.0.1 zlib/1.2.3.4 libidn/1.23 librtmp/2.3
 > Host: az.testbed.fi-ware.eu
 > Content-Type: application/xml;charset=UTF-8
 > Accept: application/xml
 > Content-Length: 227
 >
 ...
 < HTTP/1.1 200 OK
 < Server: Authorization System
 < Date: Mon, 04 Aug 2014 13:00:12 GMT
 < Content-Type: application/xml
 < Transfer-Encoding: chunked
 <
 <?xml version="1.0" encoding="UTF-8" standalone="yes"?><link xmlns="http://www.w3.org/2005/Atom" rel="item" href="h_D23LsDEeWFwqVFFMDLTQ" title="h_D23LsDEeWFwqVFFMDLTQ"/>

**WARNING**: Mind the leading and trailing single quotes for the ``--data`` argument. Do not use double quotes instead of these single quotes, otherwise curl will remove the double quotes in the XML payload itself, and send invalid XML which will be rejected by the server. The ``--trace-ascii -`` argument (the last dash here means *stdout*) is indeed a way to check the actual request body sent by ``curl``. So use it only if you need to dump the outgoing (and incoming) data, in particular the request body, on *stdout*.  

The ``href`` value in the response above gives you the domain ID (in the form of a UUID), that you will now use for assigning user roles on the domain.

Domain Removal
++++++++++++++

You remove a domain by doing a HTTP DELETE request with XML payload to URL: 
 ``http://${SERVER_NAME}:${PORT}/authzforce-ce/domains/{domain_ID}``. 

For example with ``curl`` tool::

 $ curl --verbose --request DELETE --header "Content-Type: application/xml;charset=UTF-8" \
     --header "Accept: application/xml" http://${SERVER_NAME}:${PORT}/authzforce-ce/domains/h_D23LsDEeWFwqVFFMDLTQ

Policy administration is part of the Authorization Server API, addressed more extensively in the :ref:`programmerGuide`.

Sanity check procedures
=======================
The Sanity Check Procedures are the steps that a System Administrator will take to verify that the installation is ready to be tested. This is therefore a preliminary set of tests to ensure that obvious or basic malfunctioning is fixed before proceeding to unit tests, integration tests and user validation.

End to End testing
------------------
To check the proper deployment and operation of the Authorization Server, perform the following steps:

#. Get the list of policy administration domains by doing the following HTTP request, replacing ``${host}`` with the server hostname, and ``${port}`` with the HTTP port of the server, for example with ``curl`` tool::

    $ curl --verbose --show-error --write-out '\n' --request GET http://${host}:${port}/authzforce-ce/domains
#. Check the response which should have the following headers and body (there may be more headers which do not require checking here)::

    Status Code: 200 OK
    Content-Type: application/xml
    
    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <ns2:resources xmlns:ns2="http://authzforce.github.io/rest-api-model/xmlns/authz/4">
    ... list of links to policy domains omitted here... 
    </ns2:resources>

You can check the exact body format in the representation element of response code 200 for method ``getDomains``, and all other API resources and operations in general, in the WADL (Web Application Description Language) document available at the following URL::
 
    http://${host}:${port}/authzforce-ce/?_wadl

List of Running Processes
-------------------------
* One or more ``java`` processes for Tomcat.

Network interfaces Up & Open
----------------------------
* TCP 22;
* TCP 8080.

The port 8080 can be replaced by any other available port by any other port Tomcat is listening to for HTTP connections to the webapp.

Databases
---------
None.

Diagnosis Procedures
====================
#. Perform the test described in `End to End testing`_.
#. If you get a Connection Refused/error, check whether Tomcat is started::

    $ sudo service tomcat7 status
#. If status stopped, start Tomcat::

    $ sudo service tomcat7
#. If Tomcat fails to start, check for any Tomcat high-level error in Tomcat log directory: ``/var/log/tomcat7``
#. If Tomcat is successfully started (no error in server logs), perform the test described in `End to End testing`_ again.
#. If you still get a Connection Refused/error, check whether Tomcat is not listening on a different port:
    ``$ sudo netstat -lataupen|grep java``
#. If you still get a connection refused/error, especially if you are connecting remotely, check whether you are able to connect locally, then check the network link, i.e. whether any network filtering is in place on the host or on the access network, or other network issue: network interface status, DNS/IP adress resolution, routing, etc.
#. If you get an error ``404 Not Found``, make sure the webapp is deployed and enabled in Tomcat. Check for any webapp deployment error in file: 
    ``/var/log/tomcat7/authzforce-ce/error.log``.


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
The resource consumption strongly depends on the number of concurrent clients and requests per client, the number of policy domains (a.k.a. tenants in this context) managed by the Authorization Server, and the complexity of the policies defined by administrators of each domain.

The memory consumption shall remain under 80% of allocated RAM. See `System Requirements`_ for the minimum required RAM.

The CPU usage shall remain  under 80% of allocated CPU. See `System Requirements`_ for the minimum required CPU.

As for disk usage, at any time, there should be 1GB free space left on the disk.

I/O flows
---------
* HTTPS flows with possibly large XML payloads to port 8080;
* HTTP flow to port 8080.

The port 8080 can be replaced by any other port Tomcat is listening to for HTTP connections to the webapp.

Appendix
========

Security setup for production
-----------------------------
You have to secure the environment of the application server and the server itself. Securing the environment of a server in general will not be addressed here because it is a large subject for which you can find a lot of public documentation. You will learn about perimeter security, network and transport-level security (firewall, IDS/IPS...), OS security, application-level security (Web Application Firewall), etc.
For instance, the ''NIST Guide to General Server Security'' (SP 800-123) is a good start.

Server Security Setup
+++++++++++++++++++++
For more Tomcat-specific security guidelines, please read `Tomcat 7 Security considerations <https://tomcat.apache.org/tomcat-7.0-doc/security-howto.html>`_.

For security of communications (confidentiality, integrity, client/server authentication), it is also recommended to enable SSL/TLS with PKI certificates. The first step to set up this is to have your Certification Authority (PKI) issue a server certificate for your AuthZForce instance. You can also issue certificates for clients if you want to require client certificate authentication to access the AuthZForce server/API. If you don't have such a CA at hand, you can create your own (a basic one) with instructions given in the next section.

Certificate Authority Setup
+++++++++++++++++++++++++++
If you have a CA already, you can skip this section.
So this section is about creating a local Certificate Authority (CA) for issuing certificates of the Authorization Server and clients, for authentication, integrity and confidentiality purposes. **This procedure requires using a JDK 1.7 or later.**
(For the sake of simplicity, we do not use a subordinate CA, although you should for production, see `keytool command example <http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html#genkeypairCmd>`_, use the ``pathlen`` parameter to restrict number of subordinate CA, ``pathlen=0`` means no subordinate.)

#. Generate the CA keypair and certificate on the platform where the Authorization Server is to be deployed (change the validity argument to your security requirements, example here is 365 days)::

    $ keytool -genkeypair -keystore taz-ca-keystore.jks -alias taz-ca -dname "CN=Thales AuthzForce CA, O=FIWARE" \
        -keyalg RSA -keysize 2048 -validity 365 -ext bc:c="ca:true,pathlen:0"
#. Export the CA certificate to PEM format for easier distribution to clients::

    $ keytool -keystore taz-ca-keystore.jks -alias taz-ca -exportcert -rfc > taz-ca-cert.pem


Server SSL Certificate Setup
++++++++++++++++++++++++++++
For Tomcat 7, refer to the `Tomcat 7 SSL/TLS Configuration HOW-TO <https://tomcat.apache.org/tomcat-7.0-doc/ssl-howto.html>`_.

User and Role Management Setup
++++++++++++++++++++++++++++++
In production, access to the API must be restricted and explicitly authorized. To control which clients can do what on what parts of API, we need to have access to user identity and attributes and assign proper roles to them. These user and role management features are no longer supported by the AuthZForce server itself, but should be delegated to the Identity Management GE. 

Domain Role Assignment
++++++++++++++++++++++
In production, access to the API must be restricted and explicitly authorized. To control which clients can do what on what parts of API, we need to have access to user identity and attributes and assign proper roles to them. These user role assignment features are no longer supported by the AuthZForce server itself, but should be delegated to the Identity Management GE. 

Performance Tuning
------------------
For Tomcat and JVM tuning, we strongly recommend reading and applying - when relevant - the guidelines from the following links:

* `Performance tuning best practices for VMware Apache Tomcat <http://kb.vmware.com/kb/2013486>`_;
* `How to optimize tomcat performance in production <http://www.genericarticles.com/mediawiki/index.php?title=How_to_optimize_tomcat_performance_in_production>`_;
* `Apache Tomcat Tuning Guide for REST/HTTP APIs <https://javamaster.wordpress.com/2013/03/13/apache-tomcat-tuning-guide/>`_.

Last but not least, consider tuning the OS, hardware, network, using load-balancing, high-availability solutions, and so on.
