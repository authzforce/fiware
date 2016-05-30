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
 make clean singlehtml
 ```

For PDF output, if you do it for the first time, install the requirements (instructions for Ubuntu 14.04+):
 ```shell
 sudo aptitude install texlive-latex-recommended texlive-fonts-recommended texlive-latex-extra
 ```
Then run:

```shell
make clean latexpdf
```
