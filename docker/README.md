## Authorization PDP - AuthZForce Docker minimal image

[Authorization PDP - AuthZForce](http://catalogue.fiware.org/enablers/authorization-pdp-authzforce) is a Reference Implementation of the Authorization PDP Generic Enabler (formerly Access Control GE).

Find detailed information of this Generic enabler at [Fiware catalogue](http://catalogue.fiware.org/enablers/authorization-pdp-authzforce).

This image is intended to work together with [Identity Manager - Keyrock](http://catalogue.fiware.org/enablers/identity-management-keyrock) and [PEP Proxy Wilma](http://catalogue.fiware.org/enablers/pep-proxy-wilma) generic enabler; and also integrated in Bitergia's [Chanchan APP](https://github.com/Bitergia/fiware-chanchan).

## Image contents
- OpenJDK 7;
- Tomcat 7;
- Authzforce Server (version defined by Git/Docker tag attached to this Dockerfile).

## Usage

This image gives you a minimal installation for testing purposes. The [AuthZForce Installation and administration guide](http://authzforce-ce-fiware.readthedocs.org/en/release-5.3.0/InstallationAndAdministrationGuide.html) provides you a better approach for using it in a production environment.

This image, if used with the [Chanchan APP](https://github.com/Bitergia/fiware-chanchan), is fully provided for testing. [PEP Proxy Wilma](http://catalogue.fiware.org/enablers/pep-proxy-wilma) included in Chanchan APP is aware of the [Domain creation](http://authzforce-ce-fiware.readthedocs.org/en/release-5.3.0/InstallationAndAdministrationGuide.html#domain-creation). 

Still, you can always do it yourself. 

Create a container using `fiware/authzforce-ce-server` image by doing:

```
docker run -d --name <container-name> fiware/authzforce-ce-server:release-5.3.0
```

As stands in the [AuthZForce Installation and administration guide](http://authzforce-ce-fiware.readthedocs.org/en/release-5.3.0/InstallationAndAdministrationGuide.html#policy-domain-administration) you can:

* **Create a domain**

```
curl -s --request POST \
--header "Accept: application/xml" \
--header "Content-Type: application/xml;charset=UTF-8" \
--data '<?xml version="1.0" encoding="UTF-8"?><taz:domainProperties xmlns:taz="http://authzforce.github.io/rest-api-model/xmlns/authz/5" />' \
 http://<authzforce-container-ip>:8080/authzforce-ce/domains
```

* **Retrieve the domain ID**

```
curl -s --request GET http://<authzforce-container-ip>:8080/authzforce-ce/domains
```

* **Domain removal**

```
curl --verbose --request DELETE \
--header "Content-Type: application/xml;charset=UTF-8" \
--header "Accept: application/xml" \
http://<authzforce-container-ip>:8080/authzforce-ce/domains/<domain-id>
```

* **User and Role Management Setup && Domain Role Assignment**

These tasks are now delegated to the [Identity Manager - Keyrock](http://catalogue.fiware.org/enablers/identity-management-keyrock) enabler. Here you can find how to use the interface for that purpose: [How to manage AuthZForce in Fiware](https://www.fiware.org/devguides/handling-authorization-and-access-control-to-apis/how-to-manage-access-control-in-fiware/).

## User feedback

### Documentation

All the information regarding the Dockerfile is hosted publicly on [Github](https://github.com/authzforce/fiware/tree/master/docker).

### Issues

If you find any issue with this image, feel free to report at [Github issue tracking system](https://github.com/authzforce/fiware/issues).
