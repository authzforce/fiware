# Copyright (C) 2012-2016 Thales Services SAS.
#
# This file is part of AuthZForce CE.
#
# AuthZForce CE is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
# 
# AuthZForce CE is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public License
# along with AuthZForce CE.  If not, see <http://www.gnu.org/licenses/>.

# Best practices for writing Dockerfiles:
# https://docs.docker.com/engine/userguide/eng-image/dockerfile_best-practices/

# Tips to do an unattended installation on Debian/Ubuntu: 
# http://www.microhowto.info/howto/perform_an_unattended_installation_of_a_debian_package.html

FROM ubuntu:14.04
MAINTAINER Cyril Dangerville (Contact via http://catalogue.fiware.org/enablers/authorization-pdp-authzforce)

ENV DEBIAN_FRONTEND noninteractive

# Proxy configuration (if you are building from behind a proxy)
# Next release of docker 1.9.0 should allow you to configure these by passing build-time arguments
# More info: https://github.com/docker/docker/issues/14634

#ENV http_proxy 'http://user:password@proxy-host:proxy-port'
#ENV https_proxy 'http://user:password@proxy-host:proxy-port'
#ENV HTTP_PROXY 'http://user:password@proxy-host:proxy-port'
#ENV HTTPS_PROXY 'http://user:password@proxy-host:proxy-port'


# Download and install Authzforce Server (service starts automatically)
RUN apt-get update --assume-yes -qq && apt-get install --assume-yes -qq \
        debconf-utils \
        gdebi \
        curl \
        openjdk-7-jdk \
        tomcat7 \
 && rm -rf /var/lib/apt/lists/* \
 && curl --silent --remote-name --location http://repo1.maven.org/maven2/org/ow2/authzforce/authzforce-ce-server-dist/5.3.0/authzforce-ce-server-dist-5.3.0.deb \
 && bash -c "echo authzforce-ce-server	authzforce-ce-server/restartTomcat	boolean	false | debconf-set-selections" \
 && bash -c "echo authzforce-ce-server	authzforce-ce-server/keepSamples	boolean	true | debconf-set-selections" \
 && gdebi --quiet --non-interactive authzforce-ce-server-dist-5.3.0.deb \
 && rm -f authzforce-ce-server-dist-5.3.0.deb \
 && sed -i 's|^JAVA_OPTS\s*=.*$|JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -Djava.awt.headless=true -Xms1024m -Xmx1024m -XX:+UseConcMarkSweepGC -XX:MaxNewSize=384m -XX:MaxPermSize=128m -server"|' /etc/default/tomcat7
CMD service tomcat7 restart && tail -f /var/lib/tomcat7/logs/catalina.out


### Exposed ports
# - App server
EXPOSE 8080
