package com.company;

import java.util.List;

public class Row {
    private List<String> wordsPerRow;

    public Row(List<String> wordsPerRow) {
        this.wordsPerRow = wordsPerRow;
    }

    public List<String> getWordsPerRow() {
        return wordsPerRow;
    }

    public void setWordsPerRow(List<String> wordsPerRow) {
        this.wordsPerRow = wordsPerRow;
    }
}
