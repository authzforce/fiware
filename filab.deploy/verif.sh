#!/bin/bash -ex

# The script is aborted if any command fails. If it is OK that a comand fails,
# use ./mycomand || true

# log into the VM and check the service
# use debian user for Debian7, ubuntu por Ubuntu12.04/Ubuntu14.04, centos for Centos6/Centos7
ssh ubuntu@$IP sudo /usr/sbin/service tomcat7 status | grep --quiet "running" 

# Check that AuthzForce webapp is up and running
# Wait for 20 sec max and check every 5 sec
max_iterations=3
iterations=0
until [[ "`curl --silent --show-error --connect-timeout 5 --max-time 10 --request GET http://$IP:8080/authzforce/domains | grep 'resources'`" != "" || $iterations > $max_iterations ]];
do
  echo --- waiting 5 more seconds for Authzforce app to start
  sleep 5
  let "iterations=iterations+1"
done

if [ $iterations -gt $max_iterations ]
then
        echo "AuthzForce startup seems to have failed as it is unreachable at this url 'http://$IP:8080/authzforce/domains', please check /var/log/tomcat7/authzforce/error.log"
else
        echo AuthZForce is ready!
fi
