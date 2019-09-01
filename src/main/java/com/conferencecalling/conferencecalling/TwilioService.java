package com.conferencecalling.conferencecalling;

import com.twilio.Twilio;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Conference;
import java.util.Map;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.util.Map.Entry;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

@Service
public class TwilioService {

        public static final String CONFERENCE_CODE = "2548";
        // Twilio credentials
        public static final String ACCOUNT_SID =
                "AC425e2ff2bb8064e458ab3f9b3f133076";
        public static final String AUTH_TOKEN =
                "2419cce35157c8144a0e4a1db2e01b85";

        public void makeCall() throws URISyntaxException {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String from = "+16159331037";
            String to = "+12694479045";
            Call call = Call.creator(new PhoneNumber(to), new
                            PhoneNumber(from),
                    new URI("http://demo.twilio.com/docs/voice.xml")).create();
            System.out.println(call.getSid());
        }

        public void generateRandom4DigitAndSend() {
            String randomFourDigit = String.format("%04d", new Random().nextInt(10000));
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String from = "+16159331037";
            //String to = "+447825625772";
            String to = "+12694479045";

            Message message = Message.creator(new PhoneNumber(to),
                    new PhoneNumber(from),
                    randomFourDigit).create();
            System.out.println(message.getSid());
        }

        //Make a call to 16159331037
    public String makeSomethingHappen() {
        Say say = new Say.Builder(
                "Hello from your pals at Twilio! Have fun.")
                .build();
        VoiceResponse voiceResponse = new VoiceResponse.Builder()
                .say(say)
                .build();
        return voiceResponse.toXml();
    }

    public String getJoinResponse() {
        String message =
                "You are about to join the Conference. Please enter the passcode to join...";

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


    public String joinConferenceCall(String request){
            Boolean muted = false;
            Boolean moderator = false;
            //lets get the passcode
            String[] output = request.split("&Digits=");
            String[] outputAgain = output[1].split("&");
            String passcode = outputAgain[0];

            Say sayMessage = null;
            Dial dial = null;

            if(passcode.equals(CONFERENCE_CODE)) {
                String defaultMessage = "You have joined the conference.";
                sayMessage = new Say.Builder(defaultMessage).build();

                Conference conference = new Conference.Builder("RapidResponseRoom")
                        .waitUrl("http://twimlets.com/holdmusic?Bucket=com.twilio.music.ambient")
                        .build();

                dial = new Dial.Builder().conference(conference).build();
            }
            else{
                String defaultMessage = "You Have entered the wrong conference code...goodbye";
                sayMessage = new Say.Builder(defaultMessage).build();
                dial = new Dial.Builder().build();
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


