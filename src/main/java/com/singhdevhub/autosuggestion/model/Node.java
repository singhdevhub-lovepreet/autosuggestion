package com.singhdevhub.autosuggestion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Node
{
    private String word;

    private Long priority;

}
