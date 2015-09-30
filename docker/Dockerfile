# Copyright (C) 2015 Bitergia
# GPLv3 License

FROM tomcat:7.0
MAINTAINER Cyril Dangerville (Contact via http://catalogue.fiware.org/enablers/authorization-pdp-authzforce)

ENV DEBIAN_FRONTEND noninteractive

# Proxy configuration (if you are building from behind a proxy)
# Next release of docker 1.9.0 should allow you to configure these by passing build-time arguments
# More info: https://github.com/docker/docker/issues/14634

#ENV http_proxy 'http://user:password@proxy-host:proxy-port'
#ENV https_proxy 'http://user:password@proxy-host:proxy-port'
#ENV HTTP_PROXY 'http://user:password@proxy-host:proxy-port'
#ENV HTTPS_PROXY 'http://user:password@proxy-host:proxy-port'


# download and install Authzforce (service starts Automatically)

RUN curl -O -L http://catalogue.fiware.org/sites/default/files/storage/enablers/authzforce_4.2.0-fiware_all.deb && \
    dpkg --extract authzforce_4.2.0-fiware_all.deb /root/authzforce/ && \
    mv /root/authzforce/etc/tomcat7/Catalina /usr/local/tomcat/conf/ && \
    mv /root/authzforce/opt/* /opt/ && \
    rm -rf /opt/authzforce/data/domains/* && \
    rm -rf /root/authzforce && \
    rm -f authzforce_4.2.0-fiware_all.deb

### Exposed ports
# - App server
EXPOSE 8080
