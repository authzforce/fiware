#!/bin/bash -ex
# As a guideline to do an unattended installation, see http://www.microhowto.info/howto/perform_an_unattended_installation_of_a_debian_package.html
# The script is aborted if any command fails. If it is OK that a command fails,
# use ./mycommand || true

export PACKAGE_VERSION=11.0.0

export DEBIAN_FRONTEND=noninteractive
sudo -E apt-get update --assume-yes -qq
sudo -E apt-get install --assume-yes -qq curl debconf-utils
sudo curl --silent --remote-name --location https://repo1.maven.org/maven2/org/ow2/authzforce/authzforce-ce-server-dist/${PACKAGE_VERSION}/authzforce-ce-server-dist-${PACKAGE_VERSION}.deb 
# Prevent Tomcat restart before change to JAVA_OPTS applied later
sudo bash -c "echo authzforce-ce-server	authzforce-ce-server/restartTomcat	boolean	false | debconf-set-selections"
sudo bash -c "echo authzforce-ce-server	authzforce-ce-server/keepSamples	boolean	true | debconf-set-selections"
sudo -E apt-get install --assume-yes ./authzforce-ce-server-dist-${PACKAGE_VERSION}.deb

# FIX issue with Tomcat (very) slow startup on Linux KVM since kernel 3.13:
# https://ask.openstack.org/en/question/51617/launching-apache-tomcat-inside-vm-takes-up-to-47-minutes/
# https://bugs.launchpad.net/ubuntu/+source/tomcat7/+bug/1269073 - Long-term fix not yet available
# WORKAROUND used here: see stackoverflow discussion linked from https://ask.openstack.org/en/question/51617/launching-apache-tomcat-inside-vm-takes-up-to-47-minutes/ 
sudo sed -i 's|^JAVA_OPTS\s*=.*$|JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -Djava.awt.headless=true -Xms1024m -Xmx1024m -XX:+UseConcMarkSweepGC -XX:MaxNewSize=384m -XX:MaxPermSize=128m -server"|' /etc/default/tomcat
sudo systemctl restart tomcat9
