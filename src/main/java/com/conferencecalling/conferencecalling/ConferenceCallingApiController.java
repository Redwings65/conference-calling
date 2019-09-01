package com.conferencecalling.conferencecalling;

import com.twilio.http.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URISyntaxException;

@Controller
@RequestMapping(path="/api")
public class ConferenceCallingApiController{

    @Autowired
    TwilioService twilioService;

    //STEP 1
    @GetMapping(path="/text") public @ResponseBody
    String sendtext() {
        twilioService.generateRandom4DigitAndSend();
        return "text was made";
    }

    @GetMapping(path="/call") public @ResponseBody
    String makeCall() throws URISyntaxException {
        twilioService.makeCall();
        return "call was made";
    }

    @GetMapping(path="/step1") public @ResponseBody
    String runStep1Combined() throws URISyntaxException {
        twilioService.generateRandom4DigitAndSend();
        twilioService.makeCall();
        return "finished step 1";
    }

//////////////////////////////////////////////////////
    //STEP 2

    //Join a conference and join a conference by passcode which is 2548.
    @PostMapping(path="/conference", produces = MediaType.APPLICATION_XML_VALUE) public @ResponseBody
    String takeCall() throws IOException, URISyntaxException {
        String response = twilioService.getJoinResponse();
        System.out.println(response);
        return response;
    }

    @PostMapping(path="/conference/connect", produces = MediaType.APPLICATION_XML_VALUE) public @ResponseBody
    String connectToConference(@RequestBody String request){
        String response = twilioService.joinConferenceCall(request);
        System.out.println(response);
        return response;
    }

}