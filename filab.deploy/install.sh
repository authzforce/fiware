#!/bin/bash -ex
# As a guideline to do an unattended installation, see http://www.microhowto.info/howto/perform_an_unattended_installation_of_a_debian_package.html
# The script is aborted if any command fails. If it is OK that a comand fails,
# use ./mycomand || true

export DEBIAN_FRONTEND=noninteractive
sudo apt-get --assume-yes -qq update
sudo apt-get --assume-yes -qq install gdebi curl
curl --silent --remote-name --location http://authzforce.github.io/fiware/dist/authzforce_latest_all.deb > /dev/null
sudo gdebi --quiet --non-interactive authzforce_latest_all.deb
