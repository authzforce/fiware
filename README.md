# fiware
This project is part of [FIWARE](https://www.fiware.org). More info on the [FIWARE catalogue](http://catalogue.fiware.org/enablers/authorization-pdp-authzforce).

This project provides the source of various FIWARE-specific documents and packaging related to AuthZForce Server. AuthZForce Server is FIWARE Authorization PDP GEri (Generic Enabler Reference Implementation):
- A technical description of the FIWARE Authorization PDP Generic Enabler (GE) that AuthZForce implements, i.e. WADL and XML schemas;
- The source of the GEri's documentation: Installation and Administration guide, User and Programmer Guide, etc. The generated documentation is available for each AuthZForce Server release as standalone HTML/PDF from the [Server release page](https://github.com/authzforce/server/releases), or online on [readthedocs.org](http://readthedocs.org/projects/authzforce-ce-fiware/versions/);
- The source of the GEri's Docker image published in FIWARE Docker hub.

The actual source code of the GEri is in [server component's repository](https://github.com/authzforce/server).

# How to generate the documentation
Checkout this git repository, go to the `doc` directory of the local copy, then
for HTML output, run: 

 ```shell
 make clean html
 ```

For PDF output, if you do it for the first time, install the requirements (instructions for Ubuntu 14.04+):
 ```shell
 sudo aptitude install texlive-latex-recommended texlive-fonts-recommended texlive-latex-extra
 ```
Then run:

```shell
make clean latexpdf
```

# Release

In order to make a release for a new AuthzForce server version, do as follows:

1. Update `version` and `release` variables in [Sphinx configuration](doc/conf.py), to match the new AuthzForce server version.
1. Update manuals: [Installation and Administration Guide](doc/InstallationAndAdministrationGuide.rst) and [User and Programmer's Guide](doc/UserAndProgrammersGuide.rst); especially version numbers when they occur.
1. Update the [Dockerfile](docker/Dockerfile) with the new link to the .deb package on Maven central.
1. Update FILAB deployment scripts: [install.sh](filab.deploy/install.sh), [verif.sh](filab.deploy/verif.sh); especially the package version to match the new AuthzForce server version.
1. Update the [changelog](CHANGELOG.md) with the new version.
1. Commit and push all changes
1. Test the Dockerfile by triggering Docker build with tag name *latest-unstable* (from git branch *master*) on [authzforce-ce-server's Docker repository](https://hub.docker.com/r/fiware/authzforce-ce-server/) (*Build Settings*). Check the result in *Build Details*.
1. After Docker build is OK, test the readthedocs.org HTML/PDF output by building the version *latest* from the [readthedocs.org repository](https://readthedocs.org/projects/authzforce-ce-fiware/builds/).
1. After the readthedocs.org build is OK, create a release on Github with same tag name as the matching release of the AuthzForce [server](https://github.com/authzforce/server/releases/latest), e.g. `release-1.2.3`.
1. Create a Docker tag in [build settings of authzforce-ce-server's Docker repository](https://hub.docker.com/r/fiware/authzforce-ce-server/) with the new release tag as *Name*, e.g. `release-1.2.3`, save changes and trigger the build.
1. Trigger readthedocs.org to fetch the new release tag by updating something in the [authzforce-ce-fiware project Settings in Admin menu](https://readthedocs.org/dashboard/authzforce-ce-fiware/edit/) such as the *Description*. 
1. In the *Versions* sub-menu of *Admin* on this readthedocs.org repository, you should now see the new tag/version. Check the *Active* checkbox next to it and click *Submit*.
1. In the *Builds* menu on the readthedocs.org repository, select the new version and click the *Build version:* button. The build is triggered and added to the queue. Wait until the status changes to *Passed*. 


# Hotfix
If you need to fix things on a release, create a hotfix release by adding/incrementing a letter as suffix to the AuthzForce server version, e.g. `1.2.3a` would be the first documentation fix for AuthzForce version `1.2.3` (the tag would be `release-1.2.3a`), `1.2.3b` would be the second hotfix, etc.
