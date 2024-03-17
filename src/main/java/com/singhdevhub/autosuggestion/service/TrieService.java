package com.singhdevhub.autosuggestion.service;

import com.singhdevhub.autosuggestion.model.Node;
import com.singhdevhub.autosuggestion.model.Trie;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class TrieService
{

    @Autowired
    private RedisService redisService;

    @Autowired
    private MongoDBService mongoDBService;

    public boolean createAndSaveTrie(String code, String userId){
        Trie trieFromRedis = new Trie(); // we will get Trie from redis
        Pattern pattern = Pattern.compile("\\s+", Pattern.DOTALL);
        String[] parts = pattern.split(code);
        if(insert(Arrays.stream(parts).toList(), trieFromRedis)){
            redisService.updateDataInRedis(userId, trieFromRedis);
            mongoDBService.saveData(userId, trieFromRedis);
            return true;
        }
        return false;
    }

    public Trie getTrieReference(List<String> words, Trie root){
        Trie retTrie = null;
        List<Trie> trieList = root.getNext();
        for(String word: words){
            if(trieList.isEmpty()){
                break;
            }
            for(Trie trie: trieList){
                if(word.equals(trie.getNode().getWord())){
                    trie.getNode().setPriority(increasePriority(trie.getNode().getPriority()));
                    trieList = trie.getNext();
                    retTrie = trie;
                    break;
                }
            }
        }
        return retTrie;
    }

    public String find(List<String> words, Trie root){
        Trie referenceTrie = getTrieReference(words, root);
        StringBuilder retVal = new StringBuilder();
        for(Trie trie: referenceTrie.getNext()){
            if(Objects.isNull(trie) || Objects.isNull(trie.getNext())){
                return referenceTrie.toString();
            }
            retVal.append(trie.getNode().getWord());
            referenceTrie = trie;
        }
        return retVal.toString();
    }

    public boolean insert(List<String> words, Trie root){
        try{
            Trie refTrie = getTrieReference(words, root);
            refTrie.setNext(List.of(new Trie()));
            Trie tempTrie = refTrie.getNext().get(0);

            for(String word: words){
                tempTrie.setNode(new Node(word, 1L));
                tempTrie.setNext(List.of(new Trie()));
                tempTrie = tempTrie.getNext().get(0);
            }
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    private Long increasePriority(Long priority){
        return ++priority;
    }

    // Todo: implement delete function when Length of any leg > 10^4 and lower level list grows > 10^4


}
