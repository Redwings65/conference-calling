package com.conferencecalling.conferencecalling;

import org.springframework.beans.factory.annotation.Autowired;
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
    String test() throws IOException, URISyntaxException {
        twilioService.generateRandom4DigitAndSend();
        return "text was made";
    }

    @GetMapping(path="/call") public @ResponseBody
    String makeCall() throws IOException, URISyntaxException {
        twilioService.makeCall();
        return "call was made";
    }

    @PostMapping(path="/conference") public @ResponseBody
    String takeCall() throws IOException, URISyntaxException {
        String response = twilioService.makeSomethingHappen();
        return response;
    }
//////////////////////////////////////////////////////
    //STEP 2

}