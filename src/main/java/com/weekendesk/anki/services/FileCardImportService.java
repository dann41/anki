package com.weekendesk.anki.services;

import com.weekendesk.anki.domain.Card;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileCardImportService implements CardImportService {

    private static final String FIELD_SEPARATOR = "\\|";

    private final CardService cardService;

    public FileCardImportService(CardService cardService) {
        this.cardService = cardService;
    }

    public long importFromResource(String resourceName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(resourceName))) {
            return reader.lines()
                    .skip(1)
                    .map(this::lineToCard)
                    .filter(this::isValidCard)
                    .mapToInt(card -> storeCard(card) ? 1 : 0)
                    .sum();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Card lineToCard(String s) {
        Card card = new Card();
        String[] parts = s.split(FIELD_SEPARATOR);
        if (parts.length == 2) {
            card.setQuestion(parts[0]);
            card.setAnswer(parts[1]);
        }
        return card;
    }

    private boolean isValidCard(Card card) {
        return card != null &&
                card.getQuestion() != null && !card.getQuestion().isEmpty() &&
                card.getAnswer() != null && !card.getAnswer().isEmpty();
    }

    private boolean storeCard(Card card) {
        return cardService.addCard(card);
    }

}
