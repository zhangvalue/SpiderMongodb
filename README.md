简单的利用jsoup爬去http://www.pharmnet.com.cn/网站上的中医图谱的
疾病食疗法、治病常用酒、治病常用药茶、中药材词典、中医方剂词典、中医名称词典数据
技术路线：
JKD1.8
maven
数据库：MongoDB

最后的爬去的结果；
spider_diet 908条数据
spider_diet.json
疾病食疗数据
疾病名称 disease_name
食疗名称dietotherapy_name
配方 formula
制法 making_method
功效 effect
用法 usage
链接 dietotherapy_url;
文章爬去时间 create_time;
爬去的数据格式如下
{
    "_id" : ObjectId("5cda85c39fe501a1bee3ef80"),
    "disease_name" : "感冒",
    "dietotherapy_name" : "【姜丝鸭蛋汤】",
    "formula" : "生姜50克（去皮），鸭蛋2个，白酒20毫升",
    "making_method" : "生姜洗净去皮，切成丝，加水200毫升煮沸，鸭蛋去壳打散，倒入生姜汤中，稍搅，再加入白酒，煮沸即可",
    "effect" : "解表散寒",
    "usage" : "每日1次，吃蛋饮汤，顿服，可连服3日。",
    "dietotherapy_url" : "http://www.pharmnet.com.cn/tcm/jbsl/ganmao/index1.html",
    "create_time" : "2019-05-14 17:09:23"
}
治病常用酒数据
spider_wine
376条数据
spider_wine.json
疾病名称 disease_name
药酒名称 wine_name
配方 formula
链接 wine_url;
爬去时间 create_time;
数据格式：
{
    "_id" : ObjectId("5cdaa26c9fe501a32a1171f4"),
    "disease_name" : "感冒",
    "wine_name" : "葵花酒",
    "formula" : "向日葵籽和秋季采集的叶各100克，白酒300毫升。",
    "wine_url" : "http://www.pharmnet.com.cn/tcm/syyj/zhibing/ganmao/index1.html",
    "create_time" : "2019-05-14 19:11:40"
}
spider_tea
常用药酒数据
1340条数据
spider_tea.json
其中   科室名为儿科方和无茶治疗茶方  没有疾病名称
科室  department_name
疾病名称 disease_name
药茶名称 tea_name
配方 formula
功效 effect
链接 tea_url
爬去时间 create_time
数据格式如下：
    "_id" : ObjectId("5cdb6f469fe501ac67361f75"),
    "department_name" : "儿科方",
    "wine_name" : "藿香竹茹茶",
    "formula" : "藿香、竹茹各2钱，生姜1片。老茶叶少许。",
    "effect" : "治呕吐腹痛。",
    "tea_url" : "http://www.pharmnet.com.cn/tcm/ycdq/zhiliao/erke",
    "create_time" : "2019-05-15 09:45:42"
}

中药材词典数据
spider_medicinal
11275条数据
药材名称 medicinal_name
药材链接 medicinal_url
爬去时间 create_time
数据格式如下；
{
    "_id" : ObjectId("5cdb874a3f28bbadeee5ba81"),
    "medicinal_name" : "凹头苋",
    "medicinal_url" : "http://www.pharmnet.com.cn/tcm/knowledge/detail/103728.html",
    "create_time" : "2019-05-15 11:28:10"
}


药剂词典数据
数据一共2267
方剂名称 prescription_name
拼音 pinyin
处方 prescription_detail
性状 character
规格 specifica
制法 making_method
主治功能 functional
爬去时间create_time
储藏 store
用途用量 usage
摘录 excerpt
数据格式如下：
{
    "_id" : ObjectId("5cdbde332f2231b23228f9e9"),
    "prescription_name" : "鼻窦炎口服液",
    "prescription_detail" : "辛夷、荆芥、薄荷、桔梗、柴胡、苍耳子、白芷、川芎、黄芩、栀子、茯苓、川木通、黄芪、龙胆草",
    "character" : "为深棕黄色至深棕褐色的液体；气芳香，味苦。",
    "specification" : "每支装10ml",
    "making_method" : "上十四味，辛夷、荆芥、薄荷、柴胡用水蒸气蒸馏提取芳香水，蒸馏后的药渣与其余桔梗等十味加水煎煮三次，每次1小时，合并煎液，滤过，滤液浓缩至适量，静置，取上清液，滤过，滤液加入上述芳香水与适量防腐剂，混匀，加水至规定量，搅匀，滤过，灌封，灭菌，即得。",
    "functional" : "通利鼻窍。用于鼻塞不通，流黄稠涕；急、慢性鼻炎，副鼻窦炎等。 ",
    "create_time" : "2019-05-15 17:38:59",
    "store" : "密封，遮光，置阴凉处。",
    "usage" : "口服，一次10ml，一日3次，20日为一疗程。 ",
    "excerpt" : "《中国药典》"
}
