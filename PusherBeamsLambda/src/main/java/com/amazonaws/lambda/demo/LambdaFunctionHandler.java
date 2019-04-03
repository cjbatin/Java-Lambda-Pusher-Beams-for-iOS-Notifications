package com.amazonaws.lambda.demo;

import java.util.List;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pusher.pushnotifications.PushNotifications;

public class LambdaFunctionHandler implements RequestHandler<RequestClass, String> {

    @Override
    public String handleRequest(RequestClass request, Context context) {
        String instanceId = "0fb4a756-8780-4425-b393-3c7855470d99";
        String secretKey = "7A57DBC7C435D04039C301492C05F75F9AD65835D5DCF41F50FC901F68AA93C2";

        PushNotifications beamsClient = new PushNotifications(instanceId, secretKey);
        List<String> interests = Arrays.asList("hello");

        Map<String, Map> publishRequest = new HashMap();
        Map<String, String> alertMessage= new HashMap();
        alertMessage.put("title", request.title);
        alertMessage.put("body", request.message);
        
        Map<String, Map> alert = new HashMap();
        alert.put("alert", alertMessage);
        
        
        Map<String, Map> aps = new HashMap();
        aps.put("aps", alert);
        publishRequest.put("apns", aps);

        try {
			beamsClient.publishToInterests(interests, publishRequest);
			return "Push sent!";
		} catch (IOException e) {
			e.printStackTrace();
			return "Push failed!";
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "Push failed!";
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return "Push failed!";
		}
    }
}
