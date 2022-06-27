package com.filmreview.dto;

import com.filmreview.domain.EvaluationType;

public class EvaluationCommentDTO { //TODO: ver se faz sentido mantar.. até o momento não ta sendo usada

    private ReviewerDTO sender;

    private EvaluationType type;

    public static EvaluationCommentDTO of(ReviewerDTO sender, EvaluationType type){
        var dto = new EvaluationCommentDTO();
        dto.sender = sender;
        dto.type = type;

        return dto;
    }

    public ReviewerDTO getSender() {
        return sender;
    }

    public EvaluationType getType() {
        return type;
    }
}
