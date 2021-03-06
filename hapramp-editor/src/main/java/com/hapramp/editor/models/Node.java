package com.hapramp.editor.models;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public EditorType type;
    public ArrayList<String> content;
    public List<EditorTextStyle> contentStyles;

    @Override
    public String toString() {
        return "Node{" +
                "type=" + type +
                ", content=" + content +
                ", contentStyles=" + contentStyles +
                '}';
    }
}
