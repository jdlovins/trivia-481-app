package org.mclovins.josh.trivia_481.events;

/**
 * Created by Josh on 4/5/18.
 */

public class SendAnswerEvent extends BaseEvent {
    public int questionPK;
    public int answerPK;

    public SendAnswerEvent(int question, int answer) {
        this.type = EventType.SEND_ANSWER;
        this.questionPK = question;
        this.answerPK = answer;
    }
}
