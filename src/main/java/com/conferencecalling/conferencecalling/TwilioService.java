package com.conferencecalling.conferencecalling;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.http.HttpMethod;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.conference.Participant;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Conference;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

@Service
public class TwilioService {

        // Twilio credentials
        public static final String ACCOUNT_SID =
                "AC425e2ff2bb8064e458ab3f9b3f133076";
        public static final String AUTH_TOKEN =
                "2419cce35157c8144a0e4a1db2e01b85";

    @PostConstruct
    private void init() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

        public Call makeCall() throws URISyntaxException {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String from = "+16159331037";
            String to = "+12694479045";
            return Call.creator(new PhoneNumber(to), new PhoneNumber(from), new URI("http://demo.twilio.com/docs/voice.xml")).create();
        }

        public Participant addParticipant(String id) {
            String from = "+16159331037";
            String to = "+12694479045";
            if(id != null) {
                return Participant.creator(
                        id,
                        new com.twilio.type.PhoneNumber(from),
                        new com.twilio.type.PhoneNumber(to))
                        .create();
            }
            else{
                return null;
            }
        }

        public String generateRandom4DigitAndSend() {
            String randomFourDigit = String.format("%04d", new Random().nextInt(10000));
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String from = "+16159331037";
            String to = "+12694479045";
            Message.creator(new PhoneNumber(to), new PhoneNumber(from), randomFourDigit).create();
            return randomFourDigit;
        }



    public String getJoinResponse() {
        String message = "You are about to join the Conference. Please enter the passcode to join...";
        Say sayMessage = new Say.Builder(message).build();
               Gather gather = new Gather.Builder()
                .action("/api/conference/connect")
                .method(HttpMethod.POST)
                .say(sayMessage)
                .build();

        VoiceResponse voiceResponse = new VoiceResponse.Builder().gather(gather).build();

        try {
            return voiceResponse.toXml();
        } catch (TwiMLException e) {
            System.out.println("Twilio's response building error");
            return "Twilio's response building error";
        }
    }

    public void createConference() throws URISyntaxException {
        String from = "+16159331037";
        String to = "+12694479045";
        Call.creator(new PhoneNumber(to), new PhoneNumber(from), new URI("http://www.mocky.io/v2/584ac0f81000002b14fb0205")).create();
    }


    public String joinConferenceCall(String request, String conferenceCode){
            //lets get their passcode
        String passcode = null;
            if(!request.equals(conferenceCode)) {
                String[] output = request.split("&Digits=");
                String[] outputAgain = output[1].split("&");
                passcode = outputAgain[0];
            }
            else{
                passcode = conferenceCode;
            }

            Say sayMessage = null;
            Dial dial = null;

            System.out.println("made it here");

            if(passcode.equals(conferenceCode)) {
                String defaultMessage = "You have joined the conference.";
                sayMessage = new Say.Builder(defaultMessage).build();
                Conference conference = new Conference.Builder("letItGoSimple")
                        .waitUrl("http://twimlets.com/holdmusic?Bucket=com.twilio.music.ambient")
                        .build();

                System.out.println("made it here2");
                dial = new Dial.Builder().conference(conference).build();

            }
            else {
                String defaultMessage = "You Have entered the wrong conference code...goodbye";
                sayMessage = new Say.Builder(defaultMessage).build();
                dial = new Dial.Builder().build();
                System.out.println("made it here3");
            }

            VoiceResponse voiceResponse = new VoiceResponse.Builder().say(sayMessage).dial(dial).build();
            try {
                return voiceResponse.toXml();
            } catch (TwiMLException e) {
                System.out.println("Twilio's response building error");
                return "Twilio's response building error";
            }
        }
}


