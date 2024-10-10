package com.example.travelplannerservice.service;

import dev.langchain4j.data.message.*;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Service
public class LangchainService {

    private final OpenAiChatModel chatModel;

    public LangchainService(@Value("${OPEN_API_KEY}") String openaiApiKey) {
        this.chatModel = OpenAiChatModel.builder()
                .apiKey(openaiApiKey)
                .build();
    }

    public String generateItinerary(String destination,
                                    List<String> preferences,
                                    String travel_start_dates,
                                    String travel_end_dates,
                                    String travel_companions,
                                    String accommodation_type) {
        String preferencesStr = String.join(", ", preferences);
        String prompt = String.format("Create a detailed travel itinerary for a trip to %s from %s to %s, focusing on the following preferences: %s. "
                        + "The trip will include the following companions: %s. The preferred accommodation type is: %s. "
                        + "Please include daily plans, recommended points of interest, transportation suggestions, and travel tips.",
                destination, travel_start_dates, travel_end_dates, preferencesStr, travel_companions, accommodation_type);


        Response<AiMessage> aiMessage = chatModel.generate(Arrays.asList(
                new SystemMessage("You are a travel assistant."),
                new UserMessage(prompt)
        ));
        return aiMessage.content().text();
    }
}
