package com.consultation.studenthelp.net.vo;

import org.litepal.LitePal;

import java.util.List;

/**
 * 题库初始化
 */
public class DataListProvider {
    public static void init() {
        if (LitePal.findFirst(Questionnaire.class) == null) {
            List<Questionnaire> questionnaires = List.of(new Questionnaire("心理健康教育调查表", "欢迎大家参加本次答题", "15分以上及格",
                            List.of(new Question("你的性别", false, List.of(new Answer("男", 1), new Answer("女", 1))),
                                    new Question("你认为自己是什么性格的人？", false, List.of(new Answer("乐观开朗，积极向上", 3), new Answer("随遇而安，处事淡漠", 2), new Answer("急躁，易焦虑", 1))),
                                    new Question("你有曾经或者现在被哪些情绪困扰？", true, List.of(new Answer("自卑", 0), new Answer("焦虑", 0), new Answer("愤怒", 0), new Answer("压抑", 0))),
                                    new Question("你是否经常有这些焦虑？", false, List.of(new Answer("总是", 0), new Answer("经常", 1), new Answer("偶尔", 2), new Answer("基本没", 3))),
                                    new Question("你在情绪不安时是否有过极端想法？", false, List.of(new Answer("有", 0), new Answer("偶尔有", 1), new Answer("无", 2))),
                                    new Question("你认为自己的情绪自我调节能力如何", false, List.of(new Answer("很强", 4), new Answer("不错", 3), new Answer("一般", 2), new Answer("差", 0))),
                                    new Question("你是否会主动培养积极情绪?", false, List.of(new Answer("会", 3), new Answer("不会", 1))),
                                    new Question("你觉得自己培养积极情绪的效果如何", false, List.of(new Answer("理想", 3), new Answer("一般", 2), new Answer("基本没用", 1)))
                            )),
                    new Questionnaire("抑郁自评量表（SDS）", "您好，这是一份抑郁情况调查表。请您仔细阅读每一道题的描述，并根据您最近1周的实际情况，考虑你出现题目所描述情绪的频率，并在所列的答案中选择一个最适合的选项。 ", "40分以上及格",
                            List.of(new Question("我觉得闷闷不乐，情绪低沉", false, List.of(new Answer("没有或很少时间", 4), new Answer("少部分时间", 3), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 0))),
                                    new Question("我觉得一天之中早晨最好", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我一阵阵哭出来或觉得想哭", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我晚上睡眠不好", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我吃得跟平常一样多?", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我跟异性密切接触时和以往一样感到愉快", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我发现我的体重在下降", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我有便秘的苦恼", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我心跳比平常快", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我无缘无故地感到疲乏", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我的头脑跟平常一样清楚", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我觉得经常做的事并没有困难", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我感到不安，心情难以平静", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我对未来抱有希望", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我比平常更容易生气激动", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我觉得做出决定是很容易的", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我觉得自己是个有用的人，有人需要我", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我的生活过得很有意思", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("我认为如果我死了，别人会过得好些", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))),
                                    new Question("平常感兴趣的事情我仍然感兴趣", false, List.of(new Answer("没有或很少时间", 2), new Answer("少部分时间", 2), new Answer("相当多时间", 2), new Answer("绝大部分或全部时间", 2))))));

            for (Questionnaire questionnaire : questionnaires) {
                for (Question question : questionnaire.getQuestionList()) {
                    LitePal.saveAll(question.getAnswerList());
                }
                LitePal.saveAll(questionnaire.getQuestionList());
            }
            LitePal.saveAll(questionnaires);
        }
    }
}
