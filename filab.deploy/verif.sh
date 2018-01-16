#!/bin/bash -ex

# The script is aborted if any command fails. If it is OK that a comand fails,
# use ./mycomand || true

# log into the VM and check the service
# use debian user for Debian7, ubuntu for Ubuntu12.04/Ubuntu14.04, centos for Centos6/Centos7

# Check that AuthzForce webapp is up and running
# Wait for 20 sec max and check every 5 sec
max_iterations=5
iterations=0
until [[ "`curl --silent --show-error --connect-timeout 5 --max-time 10 --request GET http://$IP:8080/authzforce-ce/domains | grep 'resources'`" != "" || $iterations -gt $max_iterations ]];
do
  echo --- waiting 5 more seconds for AuthzForce app to start
  sleep 5
  let "iterations=iterations+1"
  echo "Status check $iterations/$max_iterations"
done

if [[ $iterations -gt $max_iterations ]]
then
        echo "AuthzForce startup seems to have failed, OR the Tomcat server was stopped, OR network filtering measures are blocking the HTTP request, as it is unreachable at URL 'http://$IP:8080/authzforce-ce/domains', please check /var/log/tomcat7/authzforce-ce/error.log"
else
        echo AuthzForce is ready!
fi
