package com.singhdevhub.autosuggestion.service;

import com.singhdevhub.autosuggestion.model.Node;
import com.singhdevhub.autosuggestion.model.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class TrieService
{

    @Autowired
    private RedisService redisService;

    @Autowired
    private MongoDBService mongoDBService;

    public boolean createAndSaveTrie(String code, String userId){
        Trie trieFromRedisOrMongo = redisService.getDataFromRedis(userId);
        if(Objects.isNull(trieFromRedisOrMongo)){
            trieFromRedisOrMongo = mongoDBService.getData(userId);
        }
        List<String> words = getWords(code);
        Trie insertedTrie = insert(words, trieFromRedisOrMongo);
        if(Objects.nonNull(insertedTrie)){
            redisService.updateDataInRedis(userId, insertedTrie);
            mongoDBService.saveData(userId, insertedTrie);
            return true;
        }
        return false;
    }

    public List<String> getWords(String code){
        Pattern pattern = Pattern.compile(" ");
        String[] parts = pattern.split(code);
        return Arrays.stream(parts).toList();
    }

    public Pair<Trie, Integer> getTrieReference(List<String> words, Trie root){
        Trie retTrie = null;
        if(Objects.isNull(root) || Objects.isNull(root.getNext())){
            return Pair.of(new Trie(), -1);
        }
        List<Trie> trieList = root.getNext();
        int index = 0;
        for(String word: words){
            if(trieList.isEmpty()){
                break;
            }
            for(Trie trie: trieList){
                if(word.equals(trie.getNode().getWord())){
                    index++;
                    trie.getNode().setPriority(increasePriority(trie.getNode().getPriority()));
                    trieList = trie.getNext();
                    retTrie = trie;
                    break;
                }
            }
        }
        return Pair.of(retTrie, index);
    }

    public String find(List<String> words, Trie root){
        Pair<Trie, Integer> referenceTrieWithWordIndex = getTrieReference(words, root);
        Trie referenceTrie = referenceTrieWithWordIndex.getFirst();
        StringBuilder retVal = new StringBuilder();

        while(Objects.nonNull(referenceTrie) && Objects.nonNull(referenceTrie.getNode()) && Objects.nonNull(referenceTrie.getNext())){
            retVal.append(referenceTrie.getNode().getWord()).append(" ");
            referenceTrie = referenceTrie.getNext().getFirst();
        }
        return retVal.toString();
    }

    public Trie insert(List<String> words, Trie root){
        try{
            Trie refTrie;
            int startFromIndexOfWord = 0;
            if(Objects.isNull(root)){
                root = new Trie();
                refTrie = root;
            }else{
                Pair<Trie, Integer> trieWithWordIndex = getTrieReference(words, root);
                refTrie = trieWithWordIndex.getFirst();
                startFromIndexOfWord = trieWithWordIndex.getSecond();
            }

            refTrie.getNext().add(new Trie());
            if(Objects.isNull(refTrie.getNode())){
                refTrie.setNode(new Node("root", 1L));
            }
            Trie tempTrie = refTrie.getNext().getLast();

            for(String word: words.subList(startFromIndexOfWord, words.size())){
                tempTrie.setNode(new Node(word, 1L));
                List<Trie> nextTries = Optional.of(tempTrie.getNext()).orElse(new ArrayList<>());
                nextTries.add(new Trie());
                tempTrie = tempTrie.getNext().getLast();
            }
            return root;
        }catch (Exception ex){
            return null;
        }
    }

    private Long increasePriority(Long priority){
        return ++priority;
    }

    // Todo: implement delete function when Length of any leg > 10^4 and lower level list grows > 10^4


}
