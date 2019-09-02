package com.conferencecalling.conferencecalling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.net.URISyntaxException;

@Controller
@RequestMapping(path="/api")
public class ConferenceCallingApiController {

    @Autowired
    TwilioService twilioService;

    //default four digit code
    public static String fourDigitCode = "1234";

    //STEP 1
    @GetMapping(path = "/text")
    public @ResponseBody
    String sendtext() {
        twilioService.generateRandom4DigitAndSend();
        return "text was made";
    }

    @GetMapping(path = "/call")
    public @ResponseBody
    String makeCall() throws URISyntaxException {
        twilioService.makeCall();
        return "call was made";
    }

    @GetMapping(path = "/step1")
    public @ResponseBody
    String runStep1Combined() throws URISyntaxException {
        twilioService.generateRandom4DigitAndSend();
        twilioService.makeCall();
        return "finished step 1";
    }

    //Join a conference and join a conference by passcode which by default is 1234
    @PostMapping(path = "/conference", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    String askQuestionsAboutConference() {
        return twilioService.getJoinResponse();
    }

    @PostMapping(path = "/conference/connect", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    String connectToConference(@RequestBody String request) {
        return twilioService.joinConferenceCall(request, fourDigitCode);
    }

    //step3
    @GetMapping(path = "/step3")
    public @ResponseBody
    String runStep3() throws URISyntaxException {
        fourDigitCode = twilioService.generateRandom4DigitAndSend();
        twilioService.createConference();
        return "finished running step3";
    }

    //step3
    @GetMapping(path = "/step4")
    public @ResponseBody
    String runStep4() throws URISyntaxException {
        fourDigitCode = twilioService.generateRandom4DigitAndSend();
        twilioService.createConference();
        return "finshed running step4";
    }

    @PostMapping(path = "/getxml", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    String getCallToConfXML() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<Response>" +
                "<Dial timeLimit = \"300\">" +
                "<Conference maxParticipants = \"5\">testconference</Conference>" +
                "</Dial>" +
                "</Response>";
        return xml;
    }
}