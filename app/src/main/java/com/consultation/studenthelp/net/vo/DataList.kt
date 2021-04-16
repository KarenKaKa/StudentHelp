package com.consultation.studenthelp.net.vo

import org.litepal.LitePal

class DataList {
    companion object {
        fun init() {
            if (LitePal.findFirst(Questionnaire::class.java) == null) {
                mutableListOf(
                        Questionnaire("心理健康教育调查表", "欢迎大家参加本次答题").apply {
                            questionList = mutableListOf(
                                    Question("你的性别", true).apply {
                                        answerList = mutableListOf(
                                                Answer("男", 1),
                                                Answer("女", 1)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("你认为自己是什么性格的人？", true).apply {
                                        answerList = mutableListOf(
                                                Answer("乐观开朗，积极向上", 3),
                                                Answer("随遇而安，处事淡漠", 2),
                                                Answer("急躁，易焦虑", 1)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("你有曾经或者现在被哪些情绪困扰？", true).apply {
                                        answerList = mutableListOf(
                                                Answer("自卑", 0),
                                                Answer("焦虑", 0),
                                                Answer("愤怒", 0),
                                                Answer("压抑", 0)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("你是否经常有这些焦虑？", true).apply {
                                        answerList = mutableListOf(
                                                Answer("总是", 0),
                                                Answer("经常", 1),
                                                Answer("偶尔", 2),
                                                Answer("基本没", 3)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("你在情绪不安时是否有过极端想法？", true).apply {
                                        answerList = mutableListOf(
                                                Answer("有", 0),
                                                Answer("偶尔有", 1),
                                                Answer("无", 2)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("你认为自己的情绪自我调节能力如何", true).apply {
                                        answerList = mutableListOf(
                                                Answer("很强", 4),
                                                Answer("不错", 3),
                                                Answer("一般", 2),
                                                Answer("差", 0)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("你是否会主动培养积极情绪？", true).apply {
                                        answerList = mutableListOf(
                                                Answer("会", 3),
                                                Answer("不会", 1)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("你觉得自己培养积极情绪的效果如何？", true).apply {
                                        answerList = mutableListOf(
                                                Answer("理想", 3),
                                                Answer("一般", 2),
                                                Answer("基本没用", 1)
                                        )
                                        LitePal.saveAll(answerList)
                                    }

                            )
                            LitePal.saveAll(questionList)
                        },
                        Questionnaire("抑郁自评量表（SDS）", "您好，这是一份抑郁情况调查表。请您仔细阅读每一道题的描述，并根据您最近1周的实际情况，考虑你出现题目所描述情绪的频率，并在所列的答案中选择一个最适合的选项。 ").apply {
                            questionList = mutableListOf(
                                    Question("我觉得闷闷不乐，情绪低沉", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 4),
                                                Answer("少部分时间", 3),
                                                Answer("相当多时间", 2),
                                                Answer("绝大部分或全部时间", 0)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我觉得一天之中早晨最好", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 2),
                                                Answer("少部分时间", 2),
                                                Answer("相当多时间", 2),
                                                Answer("绝大部分或全部时间", 2)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我一阵阵哭出来或觉得想哭", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 3),
                                                Answer("少部分时间", 2),
                                                Answer("相当多时间", 1),
                                                Answer("绝大部分或全部时间", 0)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我晚上睡眠不好", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 3),
                                                Answer("少部分时间", 2),
                                                Answer("相当多时间", 1),
                                                Answer("绝大部分或全部时间", 0)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我吃得跟平常一样多？", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 0),
                                                Answer("少部分时间", 1),
                                                Answer("相当多时间", 2),
                                                Answer("绝大部分或全部时间", 3)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我跟异性密切接触时和以往一样感到愉快", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 0),
                                                Answer("少部分时间", 1),
                                                Answer("相当多时间", 2),
                                                Answer("绝大部分或全部时间", 3)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我发现我的体重在下降", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 3),
                                                Answer("少部分时间", 2),
                                                Answer("相当多时间", 1),
                                                Answer("绝大部分或全部时间", 0)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我有便秘的苦恼", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 3),
                                                Answer("少部分时间", 2),
                                                Answer("相当多时间", 1),
                                                Answer("绝大部分或全部时间", 0)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我心跳比平常快", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 3),
                                                Answer("少部分时间", 2),
                                                Answer("相当多时间", 1),
                                                Answer("绝大部分或全部时间", 0)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我无缘无故地感到疲乏", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 3),
                                                Answer("少部分时间", 2),
                                                Answer("相当多时间", 1),
                                                Answer("绝大部分或全部时间", 0)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我的头脑跟平常一样清楚", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 0),
                                                Answer("少部分时间", 1),
                                                Answer("相当多时间", 2),
                                                Answer("绝大部分或全部时间", 3)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我觉得经常做的事并没有困难", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 0),
                                                Answer("少部分时间", 1),
                                                Answer("相当多时间", 2),
                                                Answer("绝大部分或全部时间", 3)
                                        )
                                        LitePal.saveAll(answerList)
                                    },

                                    Question("我感到不安，心情难以平静", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 3),
                                                Answer("少部分时间", 2),
                                                Answer("相当多时间", 1),
                                                Answer("绝大部分或全部时间", 0)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我对未来抱有希望", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 0),
                                                Answer("少部分时间", 1),
                                                Answer("相当多时间", 2),
                                                Answer("绝大部分或全部时间", 3)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我比平常更容易生气激动", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 3),
                                                Answer("少部分时间", 2),
                                                Answer("相当多时间", 1),
                                                Answer("绝大部分或全部时间", 0)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我觉得做出决定是很容易的", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 0),
                                                Answer("少部分时间", 1),
                                                Answer("相当多时间", 2),
                                                Answer("绝大部分或全部时间", 3)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我觉得自己是个有用的人，有人需要我", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 3),
                                                Answer("少部分时间", 2),
                                                Answer("相当多时间", 1),
                                                Answer("绝大部分或全部时间", 0)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我的生活过得很有意思", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 0),
                                                Answer("少部分时间", 2),
                                                Answer("相当多时间", 3),
                                                Answer("绝大部分或全部时间", 4)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("我认为如果我死了，别人会过得好些", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 4),
                                                Answer("少部分时间", 1),
                                                Answer("相当多时间", 0),
                                                Answer("绝大部分或全部时间", 0)
                                        )
                                        LitePal.saveAll(answerList)
                                    },
                                    Question("平常感兴趣的事情我仍然感兴趣", true).apply {
                                        answerList = mutableListOf(
                                                Answer("没有或很少时间", 1),
                                                Answer("少部分时间", 2),
                                                Answer("相当多时间", 3),
                                                Answer("绝大部分或全部时间", 4)
                                        )
                                        LitePal.saveAll(answerList)
                                    }
                            )
                            LitePal.saveAll(questionList)
                        }

                ).apply {
                    LitePal.saveAll(this)
                }
            }
        }
    }

}