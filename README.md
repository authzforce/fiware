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
1. Update the [Dockerfile](docker/Dockerfile) with the package version.
1. Update FILAB deployment scripts: [install.sh](filab.deploy/install.sh), [verif.sh](filab.deploy/verif.sh); especially the package version to match the new AuthzForce server version.
1. Update the [changelog](CHANGELOG.md) with the new version.
1. After committing and pushing the changes to Github, create a release there with same tag name as the matching release of the AuthzForce server.
1. Create a Docker tag in [the automated builds of authzforce-ce-server's Docker repository](https://hub.docker.com/r/fiware/authzforce-ce-server/~/settings/automated-builds/) with the new release tag as *Name*, save changes and trigger the build.

# Hotfix
If you need to fix things on a release, create a hotfix release by adding/incrementing a letter as suffix to the AuthzForce server version, e.g. `1.2.3a` would be the first documentation fix for AuthzForce version `1.2.3` (the tag would be `release-1.2.3a`), `1.2.3b` would be the second hotfix, etc.
