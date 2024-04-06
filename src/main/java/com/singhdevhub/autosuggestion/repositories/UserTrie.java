package com.singhdevhub.autosuggestion.repositories;

import com.singhdevhub.autosuggestion.commons.TrieDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTrie extends MongoRepository<TrieDocument, String>
{

}
