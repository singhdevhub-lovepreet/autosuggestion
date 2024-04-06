package com.singhdevhub.autosuggestion.commons;

import com.singhdevhub.autosuggestion.model.Trie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrieDocument {

    @Id
    private String userId;


    private Trie trie;
}