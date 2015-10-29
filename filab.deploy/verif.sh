#!/bin/bash -ex

# The script is aborted if any command fails. If it is OK that a comand fails,
# use ./mycomand || true

# log into the VM and check the service
# use debian user for Debian7, ubuntu por Ubuntu12.04/Ubuntu14.04, centos for Centos6/Centos7
ssh ubuntu@$IP sudo /usr/sbin/service tomcat7 status |grep -q "running" 

# connect to webserver 
curl --silent --request GET http://$IP:8080/authzforce/domains > /dev/null