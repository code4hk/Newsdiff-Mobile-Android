package hk.code4.newsdiffhk.Model;

import android.os.Build;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import hk.code4.newsdiffhk.BuildConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by allen517 on 19/6/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.KITKAT)
public class TestNews {

    String json = "{\"news\": [{\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/china/20150427/53677179\", \"created_at\": {\"$date\": 1430097175245}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1430102755325}, \"_id\": {\"$oid\": \"553d8d171f6dc369447c4727\"}, \"changes\": 0.9195266272189349, \"title\": \"\\u5916\\u7c4d\\u7537\\u722c\\u91ce\\u9577\\u57ce\\u8569\\u5931\\u8def \\u88ab\\u56f02\\u65e5\\u7f3a\\u7ce7\\u6c34\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430755415026}}, {\"publisher\": \"apple\", \"title\": \"\\u3010\\u82f1\\u8d85\\u901f\\u905e\\u3011\\u963f\\u53e4\\u8def\\u795e\\u5361\\u58eb\\u54e5 \\u795e\\u5c04\\u624b\\u699c\\u9b25\\u5f97\\u6fc0\", \"created_at\": {\"$date\": 1429460817176}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1429536055814}, \"_id\": {\"$oid\": \"5533d7515a35dad7ccf05475\"}, \"changes\": 0.879746835443038, \"url\": \"http://hk.apple.nextmedia.com/realtime/sports/20150420/53651216\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430755547315}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/sports/20150424/53669844\", \"created_at\": {\"$date\": 1429887365111}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1429944101735}, \"_id\": {\"$oid\": \"553a59851f6dc369447c38df\"}, \"changes\": 0.8163265306122449, \"title\": \"\\u3010\\u6230\\u679c\\u901f\\u905e\\u3011\\u8db3\\u667a\\u5f69\\u7403\\u8cfd\\u6230\\u679c\\uff08\\u661f\\u671f\\u4e941-38\\uff09\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430757099371}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/sports/20150425/53672157\", \"created_at\": {\"$date\": 1429956306318}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1430095237906}, \"_id\": {\"$oid\": \"553b66d21f6dc369447c3d4a\"}, \"changes\": 0.8156028368794326, \"title\": \"\\u3010\\u6230\\u679c\\u901f\\u905e\\u3011\\u8db3\\u667a\\u5f69\\u7403\\u8cfd\\u6230\\u679c\\uff08\\u661f\\u671f\\u516d1-66\\uff09\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430758987062}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/breaking/20150424/53667142\", \"created_at\": {\"$date\": 1429849181539}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1429855423848}, \"_id\": {\"$oid\": \"5539c45d1f6dc369447c337b\"}, \"changes\": 0.7974137931034483, \"title\": \"\\u300c\\u731c\\u731c\\u6211\\u662f\\u8ab0\\u300d\\u5443200\\u842c \\u7591\\u72af\\u6700\\u7d30\\u5f9715\\u6b72\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430756909713}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/sports/20150430/53689044\", \"created_at\": {\"$date\": 1430380207268}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1430407453544}, \"_id\": {\"$oid\": \"5541deaf1f6dc36ab9a46fb5\"}, \"changes\": 0.7949479940564637, \"title\": \"\\u3010\\u4e52\\u4e53\\u4e16\\u9326\\u8cfd\\u3011\\u5510\\u9d6c\\u6253\\u5165\\u7537\\u55ae16\\u5f37\\n\\u9ec3\\u675c\\u914d4\\u5f37\\u6b62\\u6b65\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430762328042}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/sports/20150430/53688332\", \"created_at\": {\"$date\": 1430358565453}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1430404508868}, \"_id\": {\"$oid\": \"55418a251f6dc36ab9a46d29\"}, \"changes\": 0.7793427230046949, \"title\": \"\\u3010\\u6709\\u7247\\u3011\\u5bf8\\u738b\\uff1a\\u8eca\\u4ed4\\u8868\\u73fe\\u5187\\u5f97\\u9802\\uff01\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430753072511}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/news/20150422/53659715\", \"created_at\": {\"$date\": 1429672265994}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1429712748337}, \"_id\": {\"$oid\": \"553711491f6dc369447c201b\"}, \"changes\": 0.7777777777777778, \"title\": \"\\u3010\\u61f6\\u4eba\\u5305\\u3011\\u6797\\u912d\\u6708\\u5a25\\u7acb\\u6cd5\\u6703\\u5ba3\\u8b80\\u653f\\u6539\\u65b9\\u6848\\u91cd\\u9ede\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430755839554}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/sports/20150429/53685122\", \"created_at\": {\"$date\": 1430290265773}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1430325320659}, \"_id\": {\"$oid\": \"55407f591f6dc36ab9a465db\"}, \"changes\": 0.7606837606837606, \"title\": \"\\u3010\\u4e52\\u4e53\\u4e16\\u9326\\u8cfd\\u3011\\u6e2f\\u968a\\u6df7\\u96d9\\u51654\\u5f37\\n\\u80af\\u5b9a\\u6709\\u724c\\u651e\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430754462079}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/sports/20150504/53701036\", \"created_at\": {\"$date\": 1430677294980}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1430737193599}, \"_id\": {\"$oid\": \"5546672e1f6dc30e2bf94c9a\"}, \"changes\": 0.7546983184965381, \"title\": \"\\u3010\\u82f1\\u8d85\\u901f\\u905e\\u3011\\u5927\\u52c7\\u69cd\\u624b\\u722d\\u7b2c2 \\u4f5c\\u5ba2\\u52e2\\u6253\\u300c\\u864e\\u300d\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430759930951}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/sports/20150502/53697249\", \"created_at\": {\"$date\": 1430573008829}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1430650901542}, \"_id\": {\"$oid\": \"5544cfd01f6dc36ab9a47ed1\"}, \"changes\": 0.754356846473029, \"title\": \"\\u3010\\u6230\\u679c\\u901f\\u905e\\u3011\\u8db3\\u667a\\u5f69\\u7403\\u8cfd\\u6230\\u679c\\uff08\\u661f\\u671f\\u516d1-80\\uff09\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430751832059}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/sports/20150503/53699867\", \"created_at\": {\"$date\": 1430652630492}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1430661954696}, \"_id\": {\"$oid\": \"554606d61f6dc36ab9a48396\"}, \"changes\": 0.7447619047619047, \"title\": \"\\u3010\\u4e52\\u4e53\\u4e16\\u9326\\u8cfd\\u3011\\u99ac\\u9f8d\\u7834\\u5bbf\\u547d\\u8d0f\\u7537\\u55ae\\n\\u4e2d\\u570b\\u6a6b\\u6383\\u4e94\\u91d1\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430757102163}}, {\"publisher\": \"tvb\", \"url\": \"http://news.tvb.com/local/55399c566db28c793a000002/\", \"created_at\": {\"$date\": 1429842065171}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1429845931589}, \"_id\": {\"$oid\": \"5539a8911f6dc369447c3277\"}, \"changes\": 0.7381818181818182, \"title\": \"\\u8881\\u570b\\u5f37\\uff1a\\u666e\\u9078\\u554f\\u984c\\u6309\\u57fa\\u672c\\u6cd5\\u3001\\u4eba\\u5927\\u6c7a\\u5b9a\\u8655\\u7406\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430752750193}}, {\"publisher\": \"tvb\", \"url\": \"http://news.tvb.com/local/553737256db28c2444000001/\", \"created_at\": {\"$date\": 1429684745514}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1429690318038}, \"_id\": {\"$oid\": \"553742091f6dc369447c2263\"}, \"changes\": 0.7303370786516854, \"title\": \"\\u5efa\\u5236\\u6d3e\\uff1a\\u652f\\u6301\\u653f\\u6539\\u65b9\\u6848 \\u7a31\\u8b70\\u54e1\\u61c9\\u5c0a\\u91cd\\u6c11\\u610f\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430752228820}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/sports/20150503/53696433\", \"created_at\": {\"$date\": 1430591008618}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1430674429104}, \"_id\": {\"$oid\": \"554516201f6dc36ab9a48018\"}, \"changes\": 0.7289494787489976, \"title\": \"\\u3010\\u82f1\\u8d85\\u901f\\u905e\\u3011\\u85cd\\u6230\\u58eb\\u8d0f\\u806f\\u8cfd\\u81ba\\u96d9\\u51a0\\u738b\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430756832847}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/international/20150422/53659782\", \"created_at\": {\"$date\": 1429674512127}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1429680225989}, \"_id\": {\"$oid\": \"55371a101f6dc369447c209b\"}, \"changes\": 0.7105943152454781, \"title\": \"\\u5c0f\\u578b\\u7121\\u4eba\\u6a5f\\u589c\\u65e5\\u63c6\\u5e9c\\n\\u88dd\\u6709\\u767c\\u7130\\u7b52\\u6db2\\u9ad4\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430758468023}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/breaking/20150423/53666286\", \"created_at\": {\"$date\": 1429800949526}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1429806312601}, \"_id\": {\"$oid\": \"553907f51f6dc369447c3025\"}, \"changes\": 0.7040358744394619, \"title\": \"\\u6771\\u5eca5\\u8eca\\u4e32\\u71d2 5\\u4eba\\u53d7\\u50b7\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430757558820}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/china/20150429/53684947\", \"created_at\": {\"$date\": 1430292032009}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1430314632267}, \"_id\": {\"$oid\": \"554086401f6dc36ab9a46638\"}, \"changes\": 0.6917293233082706, \"title\": \"\\u3010\\u5c3c\\u570b\\u5730\\u9707\\u3011\\u903e2,700\\u4e2d\\u570b\\u516c\\u6c11\\u8fd4\\u570b\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430753871680}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/news/20150427/53677853\", \"created_at\": {\"$date\": 1430116869816}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1430147834300}, \"_id\": {\"$oid\": \"553dda051f6dc369447c49db\"}, \"changes\": 0.688622754491018, \"title\": \"\\u65b0\\u754c\\u95dc\\u6ce8\\u5927\\u806f\\u76df\\u4fc3\\u89e3\\u6563\\u5dba\\u5927\\u5b78\\u751f\\u6703\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430752284815}}, {\"publisher\": \"apple\", \"url\": \"http://hk.apple.nextmedia.com/realtime/international/20150425/53672821\", \"created_at\": {\"$date\": 1429959194436}, \"comments_no\": 0, \"updated_at\": {\"$date\": 1430006232904}, \"_id\": {\"$oid\": \"553b721a1f6dc369447c3d8a\"}, \"changes\": 0.6683417085427136, \"title\": \"\\u3010\\u5c3c\\u570b\\u5730\\u9707\\u3011\\u6b7b\\u4ea1\\u6578\\u5b57\\u589e\\u903e900\\u4eba\\n\\u5c3c\\u6cca\\u723e\\u9918\\u9707\\u4e0d\\u65b7\", \"lang\": \"zh_Hant\", \"last_check\": {\"$date\": 1430757683299}}], \"meta\": {\"count\": 20, \"total_count\": 1647, \"next\": \"/api/news?page=2&sort_by=changes&order=desc&lang=all\"}}";

    /*
    {
        news: [20]
        0:  {
            publisher: "apple"
            url: "http://hk.apple.nextmedia.com/realtime/china/20150427/53677179"
            created_at: {
                $date: 1430097175245
            }-
            comments_no: 0
            updated_at: {
                $date: 1430102755325
            }-
            _id: {
                $oid: "553d8d171f6dc369447c4727"
            }-
            changes: 0.9195266272189349
            title: "外籍男爬野長城蕩失路 被困2日缺糧水"
            lang: "zh_Hant"
            last_check: {
                $date: 1430755415026
            }-
        },
        meta: {
            count: 20
            total_count: 1647
            next: "/api/news?page=2&sort_by=changes&order=desc&lang=all"
        }-
    }
     */
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGson() throws Exception {
        Gson gson = new Gson();
        News lcs = gson.fromJson(json, News.class);

        assertEquals(20, lcs.getMeta().getCount());
        assertEquals("553d8d171f6dc369447c4727", lcs.getNews().get(0).getId());
        assertEquals("apple", lcs.getNews().get(0).getPublisher());
        assertTrue(1430102755325L == lcs.getNews().get(0).getUpdatedAtLong());
    }
}
