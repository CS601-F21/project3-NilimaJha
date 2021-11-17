# Test Documentation

## Chat Application System Test :- ##

1. testStatusCodeForGETOnSlackbot1()
	* test of status code received from doGet on slackbot with correct path.
	* url :- "http://localhost:9090/slackbot"

2. testToValidateResponseBodyFromGETOnSlackBot1()
	* test to validate the response body of doGet on slackbot with correct path.
	* url :- "http://localhost:9090/slackbot"

3. testStatusCodeForGETOnSlackbot2()
	* test of status code received from doGet on slackbot with incorrect path.
	* url :- "http://localhost:9090/"

4. testToValidateResponseBodyFromGETOnSlackBot2()
	* test to validate the response body of doGet on slackbot with incorrect path.
	* url :- "http://localhost:9090/"

5. testStatusCodeForGETOnSlackbot3()
	* test of status code received from doGet on slackbot with incorrect path.
	* url :- "http://localhost:9090/slackbot?asin=abc"

6. testToValidateResponseBodyFromGETOnSlackBot3()
	* test to validate the response body of doGet on slackbot with incorrect path.
   	* url :- "http://localhost:9090/slackbot?asin=abc"

7. testStatusCodeForGETOnSlackbot4()
	* test of status code received from doGet on slackbot with path in upperCase.
	* url :- "http://localhost:9090/SLACKBOT"

8. testToValidateResponseBodyFromGETOnSlackBot4()
   	* test to validate the response body of doGet on slackbot with path in upperCase.
   	* url :- "http://localhost:9090/SLACKBOT"

9. testStatusCodeForPOSTOnSlackbot1()
	* test of status code received from doPost on slackbot with incorrect path.
	* url :- "http://localhost:9090/slackboat"
	* request body :- "message=INCORRECT PATH"

10. testToValidateResponseBodyFromPOSTOnSlack1()
	* test to validate the response body of doPost on slackbot with incorrect path.
	* url :- "http://localhost:9090/slackboat"
	* request body :- "message=INCORRECT PATH"
    
11. testStatusCodeOfSlackBotForMethodNotAllowed()
	* test of status code received from doDelete on slackbot with correct path.
	* url :- "http://localhost:9090/slackbot"

12. testToValidateResponseBodyForMethodNotAllowed()
	* test to validate the response body of doDelete on slackbot with correct path.
	* url :- "http://localhost:9090/slackbot"

13. testStatusCodeForPOSTOnSlackbot2()
	* test of status code received from doPost on slackbot with incorrect format of request payload.
	* url :- "http://localhost:9090/slackbot"
	* request body :- "message = INCORRECT=REQUEST1"

14. testToValidateResponseBodyFromPOSTOnSlack2()
	* test to validate the response body of doPost on slackbot with incorrect format of request payload.
   	* url :- "http://localhost:9090/slackbot"
   	* request body :- "message = INCORRECT=REQUEST1"

15. testStatusCodeForPOSTOnSlackbot3()
	* test of status code received from doPost on slackbot with incorrect format of request payload.
   	* there is a space before key of the message response.
   	* url :- "http://localhost:9090/slackbot"
   	* request body :- " message=INCORRECT=REQUEST2"

16. testToValidateResponseBodyFromPOSTOnSlack3()
	* test to validate the response body of doPost on slackbot with incorrect format of request payload.
   	* there is a space before key of the message response.
   	* url :- "http://localhost:9090/slackbot"
   	* request body :- " message=INCORRECT=REQUEST2");
        
17. testStatusCodeForPOSTOnSlackbot4()
	* test of status code received from doPost on slackbot with incorrect format of request payload, in request payload in place of = tilde ~ symbol is used
 	* url :- "http://localhost:9090/slackbot"
 	* request body :- " message-tilda-signINCORRECT=REQUEST3"

18. testToValidateResponseBodyFromPOSTOnSlack4()
	* test to validate the response body of doPost on slackbot with incorrect format of request payload, in request payload in place of = tilde ~ symbol is used
   	* url :- "http://localhost:9090/slackbot"
   	* request body :- " message-tilda-signINCORRECT=REQUEST3"

19. testStatusCodeForPOSTOnSlackbot5()
   	* test of status code received from doPost on slackbot with incorrect key in request payload.
   	* url :- "http://localhost:9090/slackbot"
   	* request body :- "mess=INCORRECT=REQUEST4"

20. testToValidateResponseBodyFromPOSTOnSlack5()
	* test to validate the response body of doPost on slackbot with incorrect key in request payload.
	* url :- "http://localhost:9090/slackbot"
   	* request body :- "mess=INCORRECT=REQUEST4"

21. testSlackBotPOST()
   	* test to check status code and to validate the response body of doPost on slackbot with correct method, path, and payload.
   	* url :- "http://localhost:9090/slackbot"
   	* request body :- "message=Message from ChatAppSystemTest."



## Chat Application Unit Tests :- ##

1. testInputArgumentIsValid1()
	* unit test for inputArgumentIsValid() method of class ChatApplication, well-formed input argument.
   	* input argument :-  -input Chat_Application_config.json

2. testInputArgumentIsValid2()
	* unit test for inputArgumentIsValid() method of class ChatApplication, no space between -input and config file name.
   	* input argument :-  -inputChat_Application_config.json

3. testInputArgumentIsValid3()
	* unit test for inputArgumentIsValid() method of class ChatApplication. missing config file extension.
   	* input argument :- -input Chat_Application_config.

4. testInputArgumentIsValid4()
   	* unit test for inputArgumentIsValid() method of class ChatApplication. wrong config file type.
   	* input argument :- -input Chat_Application_config.jon


5. testInputArgumentIsValid5()
	* unit test for inputArgumentIsValid() method of class ChatApplication. incorrect flag in input argument.
   	* input argument :- -iput Chat_Application_config.json

6. testGetExtension1()
	* unit test for getExtension() method of class ChatApplication. file name with extension.
   	* file name :- Chat_Application_config.json

7. testGetExtension2()
	* unit test for getExtension() method of class ChatApplication. file name with some extension.
   	* file name :- Chat_Application_config.jso

8. testGetExtension3()
	* unit test for getExtension() method of class ChatApplication. file name with no extension.
   	* file name :- Chat_Application_config.

9. testGetExtension4()
	* unit test for getExtension() method of class ChatApplication.
	* file name with no extension.
	* file name :- Chat_Application_configjson
    
10. testGetExtension5()
	* unit test for getExtension() method of class ChatApplication.
    * file name with only extension.
	* file name :- .json

11. testIsValidOfSearchAppConfigData1()
	* unit test for isValid() method of class ChatAppConfigData.
	* at first creating a configdata object by reading from config file.
	* then calling isValid method of the configData class on that object.
	
12. testIsValidOfSearchAppConfigData2()
	* unit test for isValid() method of class ChatAppConfigData.
    * at first creating a configdata object with missing data and then calling isValid method.



## Search Application Unit Tests :- ##

1. testInputArgumentIsValid1()
	* unit test for inputArgumentIsValid() method of class SearchApplication.
    * well-formed input argument.
    * input argument :- -input Search_Application_config.json

2. testInputArgumentIsValid2()
	* unit test for inputArgumentIsValid() method of class SearchApplication.
    * no space between -input and config file name.
    * input argument :- -inputSearch_Application_config.json

3. testInputArgumentIsValid3()
	* unit test for inputArgumentIsValid() method of class SearchApplication.
    * missing config file extension.
    * input argument :- -input Search_Application_config.

4. testInputArgumentIsValid4()
	* unit test for inputArgumentIsValid() method of class SearchApplication.
    * wrong config file type.
    * input argument :- -input Search_Application_config.jon

5. testInputArgumentIsValid5()
	* unit test for inputArgumentIsValid() method of class SearchApplication.
    * incorrect flag in input argument.
    * input argument :- -inut Search_Application_config.json

6. testGetExtension1()
	* unit test for getExtension() method of class SearchApplication.
    * file name with extension.
    * file name :- Search_Application_config.json

7. testGetExtension2()
	* unit test for getExtension() method of class SearchApplication.
    * file name with some extension.
    * file name :- Search_Application_config.jso

8. testGetExtension3()
	* unit test for getExtension() method of class SearchApplication.
    * file name with no extension.
    * file name :- Search_Application_config.

9. testGetExtension4()
	* unit test for getExtension() method of class SearchApplication.
    * file name with no extension.
    * file name :- Search_Application_configjson

10. testGetExtension5()
	* unit test for getExtension() method of class SearchApplication.
    * file name with only extension.
    * file name :- .json

11. testgetQaFile()
	* unit test that tests getQaFile() method of SearchAppConfigData class.
    
12. testgetReviewFile()
	* unit test that tests getReviewFile() method of SearchAppConfigData class. 
    
13. testReadInputConfigFile()
	* unit test that tests getPortNo() method of SearchAppConfigData class.
    * at first creating a configdata object with all the correct information then calling isValid method.

14. testIsValidOfSearchAppConfigData1()
	* unit test for isValid() method of class SearchAppConfigData. 
	* at first creating a configdata object by reading from config file.
	* then calling isValid method of the configData class on that object.

15. testIsValidOfSearchAppConfigData2(
	* unit test for isValid() method of class SearchAppConfigData.    
	* SearchAppConfigData object does not contain port number.
    
16. testIsValidOfSearchAppConfigData3()
	* unit test for isValid() method of class SearchAppConfigData.
    * SearchAppConfigData object has wrong port number.



## Search Application System Test :-##

1. testResponseStatusCodeFromFindAsinGET1()
	* test of status code of the response received from doGet on search application with correct path.
	* url :- http://localhost:8080/find

2. testResponseStatusCodeFromFindAsinGET3()
	* test of status code of the response received from doGet by on search application with incorrect path.
	* url :- http://localhost:8080/search  

3. testResponseBodyFromFindAsinGET3()
	* to validate the response body from doGet by on search application with incorrect path.
	* url :- http://localhost:8080/search  

4. testResponseStatusCodeFromFindAsinPOST1()
	* test to validate the response body of doGet on search application with incorrect path.
	* url :- http://localhost:8080/finder
	* Request body :- "asin=120401325X"  

5. testResponseBodyFromFindAsinPOST1()
	* to validate the response body from doPost on search application with incorrect path.
	* url :- http://localhost:8080/finder 
	* Request body :- "asin=120401325X"  

6. testResponseStatusCodeFromFindAsinDELETE()
	* test of status code received from doDelete on search application with correct path.
	* url :- http://localhost:8080/find  

7. testResponseBodyFromFindAsinDELETE()
	* to validate the response body from doDelete on search application with correct path.
	* url :- http://localhost:8080/find  

8. testResponseStatusCodeFromFindAsinPOST2()
	* test of status code received from doPost on search application with correct path
    * and incorrect key of the request payload.
	* url :- http://localhost:8080/find
	* Request body :- "asinNumber=120401325X"   

9. testResponseBodyFromFindAsinPOST2()
	* to validate the response body from doPost on search application with correct path and incorrect key of the request payload.
	* url :- http://localhost:8080/find 
	* Request body :- "asinNumber=120401325X"  

10. testResponseStatusCodeFromFindAsinPOST3()
	* test of status code received from doPost on search application with correct path and incorrect request payload.
	* url :- http://localhost:8080/find
	* Request body :- "asin = 120401325X"  

11. testResponseBodyFromFindAsinPOST3()
	* to validate the response body from doPost on search application with correct path and incorrect request payload.
	* url :- http://localhost:8080/find
	* Request body :- "asin = 120401325X"    

12. testResponseStatusCodeFromFindAsinPOST4()
	* test of status code received from doPost on search application with correct path and incorrect key of the request payload.
	* url :- http://localhost:8080/find
	* Request body :- " asin=120401325X"   

13. testResponseBodyFromFindAsinPOST4()
	* test to validate the response body from doPost on search application with correct path and incorrect key of the request payload.
	* url :- http://localhost:8080/find 
	* Request body :- " asin=120401325X"   
 
14. testResponseStatusCodeFromFindAsinPOST5()
	* test of status code received from doPost on search application with correct path and incorrect request payload format.
    * in place of "=" "-" is used in between key and value of request payload.
	* url :- http://localhost:8080/find 
	* Request body :- "asin-120401325X"  

15. testResponseBodyFromFindAsinPOST5()
	* to validate the response body from doPost on search application with correct path and incorrect request payload format.
	* url :- http://localhost:8080/find 
	* Request body :- "asin-120401325X" 

16. testResponseStatusCodeFromFindAsinPOST6()
	* test of status code received from doPost on search application with correct path and well-formed payload.
	* url :- http://localhost:8080/find 
	* Request body :- "asin=120401325X" 

17. testResponseBodyFromFindAsinPOST6()
	* to validate the response body from doPost on search application with correct path and well-formed payload.
	* url :- http://localhost:8080/find 
	* Request body :- "asin=120401325X" 

18. testResponseStatusCodeFromFindAsinPOST7()
	* test of status code received from doPost on search application with correct path and well-formed payload but invalid asin to test InvertedIndex.
	* url :- http://localhost:8080/find 
	* Request body :- "asin=120401325$X" 

19. testResponseBodyFromFindAsinPOST7()
	* test to validate the response body from doPost on search application with correct path and well-formed payload but invalid asin to test InvertedIndex.
	* url :- http://localhost:8080/find 
	* Request body :- "asin=120401325$X"  

20. testResponseStatusCodeFromReviewSearchGET1()
	* test of status code of the response received from doGet on search application with correct path.
	* url :- http://localhost:8080/reviewsearch  

21. testResponseBodyFromReviewSearchGET1()
	* test to validate the response body received from doGet on search application with correct path.
	* url :- http://localhost:8080/reviewsearch  

22. testResponseStatusCodeFromReviewSearchGET2()
	* test of status code of the response received from doGet on search application with incorrect path.
	* url :- http://localhost:8080/  

23. testResponseBodyFromReviewSearchGET2()
	* test to validate the response body received from doGet on search application with incorrect path.
	* url :- http://localhost:8080/  

24. testResponseStatusCodeFromReviewSearchGET3()
	* test of status code of the response received from doGet on search application with incorrect path.
	* url :- http://localhost:8080/reviewsearch?query=computer 

25. testResponseBodyFromReviewSearchGET3()
	* test to validate the response body received from doGet on search application with incorrect path.
	* url :- http://localhost:8080/reviewsearch?query=computer  

26. testResponseStatusCodeFromReviewSearchPOST1()
	* test of status code of the response received from doGet on search application with incorrect path.
	* url :- http://localhost:8080/reviewsearcher 
	* Request body :-  "query=120401325X" 

27. testResponseBodyFromReviewSearchPOST1()
	* test to validate the response body received from doGet on search application with incorrect path.
	* url :- http://localhost:8080/reviewsearcher 
	* Request body :-  "query=120401325X"  

28. testResponseStatusCodeFromReviewSearchDELETE()
	* test of status code of the response received from doDelete on search application with correct path.
	* url :- http://localhost:8080/reviewsearch  

29. testResponseBodyFromReviewSearchDELETE()
	* test to validate the response body received from doDelete on search application with correct path.
	* url :- http://localhost:8080/reviewsearch  

30. testResponseStatusCodeFromReviewSearchPOST2()
	* test of status code of the response received from doPost on search application with correct path but incorrect request payload key.
	* url :- http://localhost:8080/reviewsearch 
	* Request body :-  "review=computer" 

31. testResponseBodyFromReviewSearchPOST2()
	* test to validate the response body received from doPost on search application with correct path but incorrect request payload key.
	* url :- http://localhost:8080/reviewsearch
	* Request body :-  "review=computer" 

32. testResponseStatusCodeFromReviewSearchPOST3()
	* test of status code of the response received from doPost on search application with correct path but incorrect request payload format.
	* url :- http://localhost:8080/reviewsearch  
	* Request body :- "query-computer"

33. testResponseBodyFromReviewSearchPOST3()
	* test to validate the response body received from doPost on search application with correct path but incorrect request payload format.
	* url :- http://localhost:8080/reviewsearch 
	* Request body :- "query-computer"

34. testResponseStatusCodeFromReviewSearchPOST4()
	* test of status code of the response received from doPost on search application with correct path but incorrect request payload format.
	* url :- http://localhost:8080/reviewsearch  
	* Request body :- "query = computer"

35. testResponseBodyFromReviewSearchPOST4()
	* test to validate the response body received from doPost on search application with correct path but incorrect request payload format.
	* url :- http://localhost:8080/reviewsearch 
	* Request body :- "query = computer" 

36. testResponseStatusCodeFromReviewSearchPOST5()
	* test of status code of the response received from doPost on search application with correct path but incorrect request payload key.
	* url :- http://localhost:8080/reviewsearch 
	* Request body :-  " query=computer"  

37. testResponseStatusCodeFromFindAsinGET1()
	* test to validate the response body received from doPost on search application with correct path but incorrect request payload key.
    * test to validate the response body of doGet on search application with incorrect path.
	* url :- http://localhost:8080/reviewsearch 
	* Request body :-  " query=computer" 

38. testResponseStatusCodeFromReviewSearchPOST6()
	* test of status code of the response received from doPost on search application with correct path and correct request payload.
	* url :- http://localhost:8080/reviewsearch 
	* Request body :-  "query=computer"

39. testResponseBodyFromReviewSearchPOST6()
	* test to validate the response body received from doPost on search application with correct path and correct request payload.
	* url :- http://localhost:8080/reviewsearch
	* Request body :-  "query=computer"

    
