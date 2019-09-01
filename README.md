# conference-calling

Task - Conference Calling Microservice
Introduction
In this task you will build a microservice for a conference calling system using Twilio.
This challenge will be used to assess your ability to quickly grasp and implement new APIs.
Coding style and grammar will also be evaluated.
You may use either Java, Kotlin or Node.js (in TypeScript).
You may use any external libraries or code which is openly available online. Your solution
should use tools that you can reasonably expect us to have or have easy access to.
Challenge description
There are four steps. Please set up a private Git repository. Commit each step to a different
branch (first step to branch step1, second to step2, etc).
We will only review code in the last branch so only code in the last step has to look
pretty.
Read the whole task document before starting any work.

Step 1 - Simple call and text
1) Generate a random 4 digit code
2) Send that code from SERVER_PHONE via a text message to CLIENT1_PHONE
3) Call CLIENT2_PHONE
Step 2 - Conference call
1) Generate a random 4 digit code
2) Send that code from SERVER_PHONE via text message to CLIENT1_PHONE
3) Call CLIENT2_PHONE
4) If someone (CLIENT1_PHONE) calls SERVER_PHONE, put them in a conference call
with CLIENT2_PHONE
Step 3 - Secure conference call
1) Generate a random 4 digit code
2) Send that code from SERVER_PHONE via text message to CLIENT1_PHONE

3) Call CLIENT2_PHONE
4) If someone (CLIENT1_PHONE) calls SERVER_PHONE and enters correct 4 digit code,
put them in a conference call with CLIENT2_PHONE
Step 4 - Make it scale by implementing as many of the following:
● Allow initiating conference calls via HTTP requests.
● The number of clients should not be limited to 2 as in the steps before.
● Allow multiple conference calls to be initiated and managed at the same time.
● If all people hang up or the call has lasted more than 5 minutes - destroy the conference.
How to test if it works
For testing dial-in in Step 3 you can set both CLIENT2_PHONE and CLIENT1_PHONE to your
personal phone number and not answer the incoming call so you could make an outgoing call.
You will be able to test if you can join the conference call although you will be the only
participant. You can also test with a friend’s phone.
Some helpful information
You should have received login details or an invitation to join a project called “AmberBox
Recruitment” on Twilio, a platform that allows sending text messages and making calls.
For you convenience, the authentication details are given below:
ACCOUNT_SID = "AC425e2ff2bb8064e458ab3f9b3f133076"
AUTH_TOKEN = "2419cce35157c8144a0e4a1db2e01b85"
Text messages can only be sent from a number that has been bought on Twilio. Please log in to
Twilio to see a list of available numbers. USE ONLY A NUMBER THAT HAS NOT BEEN
CONFIGURED YET. Non-US numbers might not be allowed to send texts outside of their
country.
Calls can be placed from any number that has been verified by Twilio (even your personal one,
if you verify it).

When you want to initiate a call, you have to make a HTTP request to Twilio saying that you
want to make a call and then Twilio will make a HTTP request to your server asking how to
make that call.
Therefore you will need to build a HTTP server application. You will also need a public IP
address in order for Twilio to be able to reach your server (try ngrok and Localtunnel for that).
The architecture should look like this:

CLIENT

(use curl, postman or similar to simulate a client)

|
▼
SERVER
(you need to build this)

▲
|

(HTTP over ngrok or localtunnel)

|

▼
TWILIO

You only need to build a server application. You do not need to build a client application. We
recommend using Postman or curl for simulating a client application.
