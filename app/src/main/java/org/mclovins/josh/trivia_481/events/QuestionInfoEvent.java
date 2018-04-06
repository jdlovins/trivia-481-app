package org.mclovins.josh.trivia_481.events;

import org.mclovins.josh.trivia_481.events.lists.AnswerInfo;

import java.util.List;

/**
 * Created by Josh on 4/5/18.
 */

public class QuestionInfoEvent extends BaseEvent {

    public String question;
    public int pk;
    public List<AnswerInfo> answers;

    public QuestionInfoEvent(String question, int pk, List<AnswerInfo> info) {
        this.question = question;
        this.pk = pk;
        this.answers = info;
    }
}
