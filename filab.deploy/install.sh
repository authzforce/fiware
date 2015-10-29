#!/bin/bash -ex
# As a guideline to do an unattended installation, see http://www.microhowto.info/howto/perform_an_unattended_installation_of_a_debian_package.html
# The script is aborted if any command fails. If it is OK that a comand fails,
# use ./mycomand || true

export DEBIAN_FRONTEND=noninteractive
sudo apt-get update -q
sudo aptitude install gdebi curl
curl -O -L http://authzforce.github.io/fiware/dist/authzforce_latest_all.deb
sudo gdebi authzforce_latest_all.deb
