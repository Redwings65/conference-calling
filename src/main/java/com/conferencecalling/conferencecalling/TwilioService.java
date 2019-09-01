package com.conferencecalling.conferencecalling;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

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
}
