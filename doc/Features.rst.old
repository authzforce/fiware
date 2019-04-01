Features
========

PDP (Policy Decision Point)
---------------------------

XACML Standard Compliance
~~~~~~~~~~~~~~~~~~~~~~~~~

AuthzForce Server implements the OASIS XACML 3.0 core specification and
various XACML 3.0 profiles.

XACML Core Specification
^^^^^^^^^^^^^^^^^^^^^^^^

AuthzForce Server supports all mandatory and several optional features
of `OASIS XACML v3.0 core
specification <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html>`__.

AuthzForce support status for each conformance table of the `XACML
Conformance
section <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#_Toc325047226>`__
is detailed in the next sections.

The following markers apply: - M: the features is mandatory in the
standard; - 0: the feature optional in the standard; - Y: the feature is
supported by AuthzForce Server; - N: the features is not supported by
AuthzForce Server.

Schema elements
'''''''''''''''

+-------------------------------------+-----+-----------+
| Element name                        | M/O | Supported |
+=====================================+=====+===========+
| xacml:Advice                        | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:AdviceExpression              | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:AdviceExpressions             | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:AllOf                         | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:AnyOf                         | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Apply                         | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:AssociatedAdvice              | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Attribute                     | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:AttributeAssignment           | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:AttributeAssignmentExpression | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:AttributeDesignator           | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Attributes                    | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:AttributeSelector             | O   | Y         |
+-------------------------------------+-----+-----------+
| xacml:AttributesReference           | O   | N         |
+-------------------------------------+-----+-----------+
| xacml:AttributeValue                | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:CombinerParameter             | O   | Y         |
+-------------------------------------+-----+-----------+
| xacml:CombinerParameters            | O   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Condition                     | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Content                       | O   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Decision                      | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Description                   | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Expression                    | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Function                      | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Match                         | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:MissingAttributeDetail        | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:MultiRequests                 | O   | N         |
+-------------------------------------+-----+-----------+
| xacml:Obligation                    | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:ObligationExpression          | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:ObligationExpressions         | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Obligations                   | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Policy                        | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:PolicyCombinerParameters      | O   | Y         |
+-------------------------------------+-----+-----------+
| xacml:PolicyDefaults                | O   | Y         |
+-------------------------------------+-----+-----------+
| xacml:PolicyIdentifierList          | O   | Y         |
+-------------------------------------+-----+-----------+
| xacml:PolicyIdReference             | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:PolicyIssuer                  | O   | Y         |
+-------------------------------------+-----+-----------+
| xacml:PolicySet                     | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:PolicySetDefaults             | O   | Y         |
+-------------------------------------+-----+-----------+
| xacml:PolicySetIdReference          | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Request                       | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:RequestDefaults               | O   | Y         |
+-------------------------------------+-----+-----------+
| xacml:RequestReference              | O   | N         |
+-------------------------------------+-----+-----------+
| xacml:Response                      | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Result                        | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Rule                          | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:RuleCombinerParameters        | O   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Status                        | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:StatusCode                    | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:StatusDetail                  | O   | Y         |
+-------------------------------------+-----+-----------+
| xacml:StatusMessage                 | O   | Y         |
+-------------------------------------+-----+-----------+
| xacml:Target                        | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:VariableDefinition            | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:VariableReference             | M   | Y         |
+-------------------------------------+-----+-----------+
| xacml:XPathVersion                  | O   | Y         |
+-------------------------------------+-----+-----------+

Algorithms
''''''''''

+---------------------------------------------------------------+-----+---------+
| Identifier                                                    | M/O | Support |
|                                                               |     | ed      |
+===============================================================+=====+=========+
| urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:deny-ov | M   | Y       |
| errides                                                       |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:deny- | M   | Y       |
| overrides                                                     |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:permit- | M   | Y       |
| overrides                                                     |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:permi | M   | Y       |
| t-overrides                                                   |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:first-a | M   | Y       |
| pplicable                                                     |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:first | M   | Y       |
| -applicable                                                   |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:only- | M   | Y       |
| one-applicable                                                |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:ordered | M   | Y       |
| -deny-overrides                                               |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:order | M   | Y       |
| ed-deny-overrides                                             |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:ordered | M   | Y       |
| -permit-overrides                                             |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:order | M   | Y       |
| ed-permit-overrides                                           |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:deny-un | M   | Y       |
| less-permit                                                   |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:deny- | M   | Y       |
| unless-permit                                                 |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:permit- | M   | Y       |
| unless-deny                                                   |     |         |
+---------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:permi | M   | Y       |
| t-unless-deny                                                 |     |         |
+---------------------------------------------------------------+-----+---------+

Note that the `algorithms planned for future
deprecation <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#_Toc325047257>`__
are no longer supported.

Status Codes
''''''''''''

+-------------------------------------------------------+-----+-----------+
| Status code                                           | M/O | Supported |
+=======================================================+=====+===========+
| urn:oasis:names:tc:xacml:1.0:status:missing-attribute | M   | Y         |
+-------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:1.0:status:ok                | M   | Y         |
+-------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:1.0:status:processing-error  | M   | Y         |
+-------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:1.0:status:syntax-error      | M   | Y         |
+-------------------------------------------------------+-----+-----------+

Attributes
''''''''''

+-----------------------------------------------------------+-----+-----------+
| Identifier                                                | M/O | Supported |
+===========================================================+=====+===========+
| urn:oasis:names:tc:xacml:1.0:environment:current-time     | M   | Y         |
+-----------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:1.0:environment:current-date     | M   | Y         |
+-----------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:1.0:environment:current-dateTime | M   | Y         |
+-----------------------------------------------------------+-----+-----------+

Data-types
''''''''''

+--------------------------------------------------------+-----+-----------+
| Identifier                                             | M/O | Supported |
+========================================================+=====+===========+
| http://www.w3.org/2001/XMLSchema#string                | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| http://www.w3.org/2001/XMLSchema#boolean               | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| http://www.w3.org/2001/XMLSchema#integer               | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| http://www.w3.org/2001/XMLSchema#double                | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| http://www.w3.org/2001/XMLSchema#time                  | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| http://www.w3.org/2001/XMLSchema#date                  | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| http://www.w3.org/2001/XMLSchema#dateTime              | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| http://www.w3.org/2001/XMLSchema#dayTimeDuration       | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| http://www.w3.org/2001/XMLSchema#yearMonthDuration     | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| http://www.w3.org/2001/XMLSchema#anyURI                | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| http://www.w3.org/2001/XMLSchema#hexBinary             | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| http://www.w3.org/2001/XMLSchema#base64Binary          | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:1.0:data-type:rfc822Name      | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:1.0:data-type:x500Name        | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:3.0:data-type:xpathExpression | O   | Y         |
+--------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:2.0:data-type:ipAddress       | M   | Y         |
+--------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:2.0:data-type:dnsName         | M   | Y         |
+--------------------------------------------------------+-----+-----------+

Functions
'''''''''

+--------------------------------------------------------------+-----+---------+
| Identifier                                                   | M/O | Support |
|                                                              |     | ed      |
+==============================================================+=====+=========+
| urn:oasis:names:tc:xacml:1.0:function:string-equal           | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:boolean-equal          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-equal          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-equal           | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-equal             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-equal             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-equal         | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dayTimeDuration-equal  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:yearMonthDuration-equa | M   | Y       |
| l                                                            |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-equal-ignore-ca | M   | Y       |
| se                                                           |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:anyURI-equal           | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:x500Name-equal         | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:rfc822Name-equal       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:hexBinary-equal        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:base64Binary-equal     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-add            | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-add             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-subtract       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-subtract        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-multiply       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-multiply        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-divide         | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-divide          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-mod            | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-abs            | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-abs             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:round                  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:floor                  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-normalize-space | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-normalize-to-lo | M   | Y       |
| wer-case                                                     |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-to-integer      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-to-double      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:or                     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:and                    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:n-of                   | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:not                    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-greater-than   | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-greater-than-o | M   | Y       |
| r-equal                                                      |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-less-than      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-less-than-or-e | M   | Y       |
| qual                                                         |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-greater-than    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-greater-than-or | M   | Y       |
| -equal                                                       |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-less-than       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-less-than-or-eq | M   | Y       |
| ual                                                          |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dateTime-add-dayTimeDu | M   | Y       |
| ration                                                       |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dateTime-add-yearMonth | M   | Y       |
| Duration                                                     |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dateTime-subtract-dayT | M   | Y       |
| imeDuration                                                  |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dateTime-subtract-year | M   | Y       |
| MonthDuration                                                |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:date-add-yearMonthDura | M   | Y       |
| tion                                                         |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:date-subtract-yearMont | M   | Y       |
| hDuration                                                    |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-greater-than    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-greater-than-or | M   | Y       |
| -equal                                                       |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-less-than       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-less-than-or-eq | M   | Y       |
| ual                                                          |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-greater-than      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-greater-than-or-e | M   | Y       |
| qual                                                         |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-less-than         | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-less-than-or-equa | M   | Y       |
| l                                                            |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:2.0:function:time-in-range          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than- | M   | Y       |
| or-equal                                                     |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or- | M   | Y       |
| equal                                                        |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-greater-than      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-greater-than-or-e | M   | Y       |
| qual                                                         |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-less-than         | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-less-than-or-equa | M   | Y       |
| l                                                            |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-one-and-only    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-bag-size        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-is-in           | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-bag             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:boolean-one-and-only   | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:boolean-bag-size       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:boolean-is-in          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:boolean-bag            | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-one-and-only   | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-bag-size       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-is-in          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-bag            | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-one-and-only    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-bag-size        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-is-in           | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-bag             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-one-and-only      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-bag-size          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-is-in             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-bag               | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-one-and-only      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-bag-size          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-is-in             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-bag               | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-bag-size      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-is-in         | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-bag           | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:anyURI-one-and-only    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:anyURI-bag-size        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:anyURI-is-in           | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:anyURI-bag             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:hexBinary-one-and-only | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:hexBinary-bag-size     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:hexBinary-is-in        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:hexBinary-bag          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:base64Binary-one-and-o | M   | Y       |
| nly                                                          |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:base64Binary-bag-size  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:base64Binary-is-in     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:base64Binary-bag       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dayTimeDuration-one-an | M   | Y       |
| d-only                                                       |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dayTimeDuration-bag-si | M   | Y       |
| ze                                                           |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dayTimeDuration-is-in  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dayTimeDuration-bag    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:yearMonthDuration-one- | M   | Y       |
| and-only                                                     |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:yearMonthDuration-bag- | M   | Y       |
| size                                                         |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:yearMonthDuration-is-i | M   | Y       |
| n                                                            |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:yearMonthDuration-bag  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:x500Name-one-and-only  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:x500Name-bag-size      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:x500Name-is-in         | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:x500Name-bag           | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:rfc822Name-one-and-onl | M   | Y       |
| y                                                            |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:rfc822Name-bag-size    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:rfc822Name-is-in       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:rfc822Name-bag         | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:2.0:function:ipAddress-one-and-only | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:2.0:function:ipAddress-bag-size     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:2.0:function:ipAddress-bag          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:2.0:function:dnsName-one-and-only   | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:2.0:function:dnsName-bag-size       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:2.0:function:dnsName-bag            | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:2.0:function:string-concatenate     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:boolean-from-string    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-from-boolean    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:integer-from-string    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-from-integer    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:double-from-string     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-from-double     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:time-from-string       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-from-time       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:date-from-string       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-from-date       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dateTime-from-string   | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-from-dateTime   | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:anyURI-from-string     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-from-anyURI     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dayTimeDuration-from-s | M   | Y       |
| tring                                                        |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-from-dayTimeDur | M   | Y       |
| ation                                                        |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:yearMonthDuration-from | M   | Y       |
| -string                                                      |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-from-yearMonthD | M   | Y       |
| uration                                                      |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:x500Name-from-string   | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-from-x500Name   | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:rfc822Name-from-string | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-from-rfc822Name | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:ipAddress-from-string  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-from-ipAddress  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dnsName-from-string    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-from-dnsName    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-starts-with     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:anyURI-starts-with     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-ends-with       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:anyURI-ends-with       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-contains        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:anyURI-contains        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:string-substring       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:anyURI-substring       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:any-of                 | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:all-of                 | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:any-of-any             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:all-of-any             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:any-of-all             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:all-of-all             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:map                    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:x500Name-match         | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:rfc822Name-match       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-regexp-match    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:2.0:function:anyURI-regexp-match    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:2.0:function:ipAddress-regexp-match | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:2.0:function:dnsName-regexp-match   | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:2.0:function:rfc822Name-regexp-matc | M   | Y       |
| h                                                            |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:2.0:function:x500Name-regexp-match  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:xpath-node-count       | O   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:xpath-node-equal       | O   | N       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:xpath-node-match       | O   | N       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-intersection    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-me | M   | Y       |
| mber-of                                                      |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-union           | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-subset          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:string-set-equals      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:boolean-intersection   | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:boolean-at-least-one-m | M   | Y       |
| ember-of                                                     |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:boolean-union          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:boolean-subset         | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:boolean-set-equals     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-intersection   | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-at-least-one-m | M   | Y       |
| ember-of                                                     |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-union          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-subset         | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:integer-set-equals     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-intersection    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-at-least-one-me | M   | Y       |
| mber-of                                                      |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-union           | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-subset          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:double-set-equals      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-intersection      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-at-least-one-memb | M   | Y       |
| er-of                                                        |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-union             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-subset            | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:time-set-equals        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-intersection      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-at-least-one-memb | M   | Y       |
| er-of                                                        |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-union             | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-subset            | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:date-set-equals        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-intersection  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-at-least-one- | M   | Y       |
| member-of                                                    |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-union         | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-subset        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:dateTime-set-equals    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:anyURI-intersection    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:anyURI-at-least-one-me | M   | Y       |
| mber-of                                                      |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:anyURI-union           | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:anyURI-subset          | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:anyURI-set-equals      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:hexBinary-intersection | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:hexBinary-at-least-one | M   | Y       |
| -member-of                                                   |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:hexBinary-union        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:hexBinary-subset       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:hexBinary-set-equals   | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:base64Binary-intersect | M   | Y       |
| ion                                                          |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:base64Binary-at-least- | M   | Y       |
| one-member-of                                                |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:base64Binary-union     | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:base64Binary-subset    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:base64Binary-set-equal | M   | Y       |
| s                                                            |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dayTimeDuration-inters | M   | Y       |
| ection                                                       |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dayTimeDuration-at-lea | M   | Y       |
| st-one-member-of                                             |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dayTimeDuration-union  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dayTimeDuration-subset | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:dayTimeDuration-set-eq | M   | Y       |
| uals                                                         |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:yearMonthDuration-inte | M   | Y       |
| rsection                                                     |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:yearMonthDuration-at-l | M   | Y       |
| east-one-member-of                                           |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:yearMonthDuration-unio | M   | Y       |
| n                                                            |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:yearMonthDuration-subs | M   | Y       |
| et                                                           |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:yearMonthDuration-set- | M   | Y       |
| equals                                                       |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:x500Name-intersection  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:x500Name-at-least-one- | M   | Y       |
| member-of                                                    |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:x500Name-union         | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:x500Name-subset        | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:x500Name-set-equals    | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:rfc822Name-intersectio | M   | Y       |
| n                                                            |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:rfc822Name-at-least-on | M   | Y       |
| e-member-of                                                  |     |         |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:rfc822Name-union       | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:rfc822Name-subset      | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:1.0:function:rfc822Name-set-equals  | M   | Y       |
+--------------------------------------------------------------+-----+---------+
| urn:oasis:names:tc:xacml:3.0:function:access-permitted       | O   | N       |
+--------------------------------------------------------------+-----+---------+

XACML RBAC Profile
^^^^^^^^^^^^^^^^^^

AuthzForce supports `XACML v3.0 Core and Hierarchical Role Based Access
Control (RBAC) Profile Version
1.0 <http://docs.oasis-open.org/xacml/3.0/rbac/v1.0/xacml-3.0-rbac-v1.0.html>`__,
except section `2.5 HasPrivilegesOfRole Policies and
Requests <http://docs.oasis-open.org/xacml/3.0/rbac/v1.0/cs02/xacml-3.0-rbac-v1.0-cs02.html#_Toc396296374>`__.

XACML Multiple Decision Profile
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

AuthzForce partially supports `XACML v3.0 Multiple Decision Profile
Version
1.0 <http://docs.oasis-open.org/xacml/3.0/multiple/v1.0/cs02/xacml-3.0-multiple-v1.0-cs02.html#_Toc388943334>`__
, in particular section 2.3 Repeated Attribute Categories.

Conformance table according to `section
7 <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-multiple-v1-spec-cd-03-en.html#_Toc260837896>`__,
with AuthzForce support status:

+----------------------------------------------------------------+-----------+
| Identifier                                                     | Supported |
+================================================================+===========+
| urn:oasis:names:tc:xacml:3.0:profile:multiple:scope            | N         |
+----------------------------------------------------------------+-----------+
| urn:oasis:names:tc:xacml:3.0:profile:multiple:xpath-expression | N         |
+----------------------------------------------------------------+-----------+
| urn:oasis:names:tc:xacml:3.0:profile:multiple:repeated-attribu | Y         |
| te-categories                                                  |           |
+----------------------------------------------------------------+-----------+
| urn:oasis:names:tc:xacml:3.0:profile:multiple:reference        | N         |
+----------------------------------------------------------------+-----------+
| urn:oasis:names:tc:xacml:3.0:profile:multiple:combined-decisio | Y         |
| n                                                              | (experime |
|                                                                | ntal)     |
+----------------------------------------------------------------+-----------+

XACML Data Loss Prevention / Network Access Control Profile
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

AuthzForce provides experimental support for `XACML Data Loss Prevention
/ Network Access Control (DLP/NAC) Profile Version
1.0 <http://docs.oasis-open.org/xacml/xacml-3.0-dlp-nac/v1.0/xacml-3.0-dlp-nac-v1.0.html>`__.
Conformance table according to `section
5 <http://docs.oasis-open.org/xacml/xacml-3.0-dlp-nac/v1.0/cs01/xacml-3.0-dlp-nac-v1.0-cs01.html#_Toc413165291>`__,
with AuthzForce support status:

+----------------------------------------------------------------+-----+-----------+
| Identifier                                                     | M/O | Supported |
+================================================================+=====+===========+
| urn:oasis:names:tc:xacml:3.0:data-type:ipAddress-value         | M   | N         |
+----------------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:3.0:data-type:ipAddress-pattern       | M   | N         |
+----------------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:3.0:function:ipAddress-match          | M   | N         |
+----------------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:3.0:function:ipAddress-endpoint-match | M   | N         |
+----------------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:3.0:function:ipAddress-value-equal    | M   | N         |
+----------------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:3.0:data-type:dnsName-value           | M   | Y         |
+----------------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:3.0:data-type:dnsName-pattern         | M   | N         |
+----------------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:3.0:function:dnsName-match            | M   | N         |
+----------------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:3.0:function:dnsName-endpoint-match   | M   | N         |
+----------------------------------------------------------------+-----+-----------+
| urn:oasis:names:tc:xacml:3.0:function:dnsName-value-equal      | M   | Y         |
+----------------------------------------------------------------+-----+-----------+

XACML Additional Combining Algorithms Profile
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

AuthzForce provides experimental support for `XACML 3.0 Additional
Combining Algorithms Profile Version
1.0 <http://docs.oasis-open.org/xacml/xacml-3.0-combalgs/v1.0/xacml-3.0-combalgs-v1.0.html>`__,
i.e. ``on-permit-apply-second`` policy combining algorithm.

XACML REST Profile
^^^^^^^^^^^^^^^^^^
AuthzForce supports `REST Profile of XACML v3.0 Version 1.0 <http://docs.oasis-open.org/xacml/xacml-rest/v1.0/xacml-rest-v1.0.html>`__.

XACML JSON Profile
^^^^^^^^^^^^^^^^^^
AuthzForce supports `JSON Profile of XACML 3.0 <http://docs.oasis-open.org/xacml/xacml-json-http/v1.0/xacml-json-http-v1.0.html>`__.

Safety & Security
~~~~~~~~~~~~~~~~~

-  Detection of circular XACML policy references (PolicySetIdReference);
-  Control of the **maximum XACML PolicySetIdReference depth**;
-  Control of the **maximum XACML VariableReference depth**;

Performance
~~~~~~~~~~~

-  Optional **strict multivalued attribute parsing**: if enabled,
   multivalued attributes must be formed by grouping all
   ``AttributeValue`` elements in the same Attribute element (instead of
   duplicate Attribute elements); this does not fully comply with `XACML
   3.0 Core specification of Multivalued attributes
   (§7.3.3) <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#_Toc325047176>`__,
   but it usually performs better than the default mode since it
   simplifies the parsing of attribute values in the request;
-  Optional **strict attribute Issuer matching**: if enabled,
   ``AttributeDesignators`` without Issuer only match request Attributes
   without Issuer (and same AttributeId, Category...); this option is
   not fully compliant with XACML 3.0, §5.29, in the case that the
   Issuer is indeed not present on a AttributeDesignator; but it is the
   recommended option when all AttributeDesignators have an Issuer (the
   XACML 3.0 specification (5.29) says: *If the Issuer is not present in
   the attribute designator, then the matching of the attribute to the
   named attribute SHALL be governed by AttributeId and DataType
   attributes alone.*);

Extensibility Points
~~~~~~~~~~~~~~~~~~~~

-  **Attribute Datatypes**: you may extend the PDP engine with custom
   XACML attribute datatypes;
-  **Functions**: you may extend the PDP engine with custom XACML
   functions;
-  **Combining Algorithms**: you may extend the PDP engine with custom
   XACML policy/rule combining algorithms;
-  **Attribute Providers aka PIPs** (Policy Information Points): you may plug custom attribute providers into
   the PDP engine to allow it to retrieve attributes from other
   attribute sources (e.g. remote service) than the input XACML Request
   during evaluation;
-  **Request Preprocessor**: you may customize the processing of XACML
   Requests before evaluation by the PDP core engine (e.g. used for
   implementing `XACML v3.0 Multiple Decision Profile Version 1.0 -
   Repeated attribute
   categories <http://docs.oasis-open.org/xacml/3.0/multiple/v1.0/cs02/xacml-3.0-multiple-v1.0-cs02.html#_Toc388943334>`__);
-  **Result Postprocessor**: you may customize the processing of XACML Results
   after evaluation by the PDP engine (e.g. used for implementing `XACML
   v3.0 Multiple Decision Profile Version 1.0 - Requests for a combined
   decision <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-multiple-v1-spec-cd-03-en.html#_Toc260837890>`__);

PAP (Policy Administration Point)
---------------------------------

-  Policy management: create/read/update/delete multiple policies and
   references from one to another (via PolicySetIdReference)
-  Policy versioning: create/read/delete multiple versions per policy.
-  Configurable root policy ID/version: top-level policy enforced by the
   PDP may be any managed policy (if no version defined in
   configuration, the latest available is selected)
-  Configurable maximum number of policies;
-  Configurable maximum number of versions per policy.
-  Optional policy version rolling (when the maximum of versions per
   policy has been reached, oldest versions are automatically removed to
   make place).

REST API
--------

-  Defined in standard `Web Application Description Language and XML
   schema <https://github.com/authzforce/rest-api-model/tree/develop/src/main/resources>`__
   so that you can automatically generate client code.
-  Provides access to all PAP/PDP features mentioned in previous
   sections.
-  Multi-tenant: allows to have multiple domains/tenants, each with its
   own PAP/PDP, in particular its own policy repository.
-  Conformance with `REST Profile of XACML v3.0 Version 1.0 <http://docs.oasis-open.org/xacml/xacml-rest/v1.0/xacml-rest-v1.0.html>`__
- Supported data formats, aka content types: 

  - ``application/xml``: XML based on API schema;
  - ``application/fastinfoset``: `Fast Infoset <http://www.itu.int/en/ITU-T/asn1/Pages/Fast-Infoset.aspx>`__ based on API's XML schema;
  - ``application/json``: JSON based on API's XML schema with a generic XML-to-JSON mapping convention
  - ``application/xacml+xml``: XACML content only, as defined by `RFC 7061 <https://tools.ietf.org/html/rfc7061>`__
  - ``application/xacml+json``: JSON format for XACML Request/Response on PDP only, as defined by `XACML v3.0 - JSON Profile Version 1.0 <http://docs.oasis-open.org/xacml/xacml-json-http/v1.0/xacml-json-http-v1.0.html>`__

High availability and load-balancing
------------------------------------

-  Integration with file synchronization tools (e.g.
   `csync2 <http://oss.linbit.com/csync2/>`__) or distributed
   filesystems (e.g. NFS and CIFS) to build clusters of AuthzForce
   Servers.
   
   
Limitations
-----------
The following optional features from `XACML v3.0 Core standard <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html>`__ are not supported:

- Elements ``AttributesReferences``, ``MultiRequests`` and ``RequestReference``;
- Functions ``urn:oasis:names:tc:xacml:3.0:function:xpath-node-equal``, ``urn:oasis:names:tc:xacml:3.0:function:xpath-node-match`` and ``urn:oasis:names:tc:xacml:3.0:function:access-permitted``;
- `Algorithms planned for future deprecation <http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#_Toc325047257>`__.
