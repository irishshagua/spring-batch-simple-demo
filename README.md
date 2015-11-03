# spring-batch-simple-demo

Execute the batch job with:

    mvn spring-boot:run
    
This gives a result set in the DB of:

ID | FIN_ADV | STATUS
---|---------|-------
1  | <DESIRED_NODE><RANDOM_SHITE>1st Node</RANDOM_SHITE><OTHER_STUFF>yadda yadda yadda</OTHER_STUFF></DESIRED_NODE> | NEW
2	 | <DESIRED_NODE><RANDOM_SHITE>2nd Node</RANDOM_SHITE><OTHER_STUFF>yadda yadda yadda</OTHER_STUFF></DESIRED_NODE> | NEW
3	 | <DESIRED_NODE><RANDOM_SHITE>3rd Node</RANDOM_SHITE><OTHER_STUFF>yadda yadda yadda</OTHER_STUFF></DESIRED_NODE>	| NEW

For an input file like:

```xml
<?xml version="1.0"?>
<ROOT_NODE>
  <DESIRED_NODE>
    <RANDOM_SHITE>1st Node</RANDOM_SHITE>
    <OTHER_STUFF>yadda yadda yadda</OTHER_STUFF>
  </DESIRED_NODE>
  <DESIRED_NODE>
    <RANDOM_SHITE>2nd Node</RANDOM_SHITE>
    <OTHER_STUFF>yadda yadda yadda</OTHER_STUFF>
  </DESIRED_NODE>
  <UNDESIRED_NODE>
    <RANDOM_SHITE>who cares</RANDOM_SHITE>
    <OTHER_STUFF>yadda yadda yadda</OTHER_STUFF>
  </UNDESIRED_NODE>
  <DESIRED_NODE>
    <RANDOM_SHITE>3rd Node</RANDOM_SHITE>
    <OTHER_STUFF>yadda yadda yadda</OTHER_STUFF>
  </DESIRED_NODE>
  <USELESS_CRAP>
    <RANDOM_SHITE>meaningless</RANDOM_SHITE>
    <OTHER_STUFF>yadda yadda yadda</OTHER_STUFF>
  </USELESS_CRAP>
  <ALSO_DONT_CARE>
    <RANDOM_SHITE>should never be read</RANDOM_SHITE>
    <OTHER_STUFF>yadda yadda yadda</OTHER_STUFF>
  </ALSO_DONT_CARE>
</ROOT_NODE>
```
