package com.singhdevhub.autosuggestion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Trie
{

    private Node node;

    private List<Trie> next;


}
