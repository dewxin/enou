package fun.enou.alpha;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fun.enou.alpha.dto.dtodb.DtoDbArticle;
import fun.enou.alpha.dto.dtodb.DtoDbDictDef;
import fun.enou.alpha.dto.dtodb.DtoDbDictWord;
import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWord;
import fun.enou.alpha.mapper.*;
import fun.enou.alpha.service.UserWordService;
import fun.enou.core.msg.EnouMessageException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class EnouApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DictDefMapper dictDefMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private DictWordMapper dictWordMapper;

    @Autowired
    private UserWordMapper userWordMapper;

    @Autowired
    private UserWordService userWordService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }



    // @Test
    // public void jwtGenerate() {
    //     Date date = new Date();
    //     SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    //     JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT") // 设置header
    //             .setHeaderParam("alg", "HS256").setIssuedAt(date) // 设置签发时间
    //             .setExpiration(new Date(date.getTime() + 1000 * 60 * 60))
    //             .claim("userId",String.valueOf(12345) ) // 设置内容
    //             .setIssuer("lws")// 设置签发人
    //             .signWith(signatureAlgorithm, "eruoahg13"); // 签名，需要算法和key
    //     String jwt = builder.compact();
    //     System.out.println(jwt);
    // }

    // @Test
    // public void dictMapper() throws EnouMessageException {
    //     DtoDbDictWord dbDictWord = dictWordMapper.findBySpell("folk");
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     List<Integer> defIdList = new LinkedList<>();
    //     try {
    //         defIdList = objectMapper.readValue(dbDictWord.getDefId(), new TypeReference<List<Integer>>(){});
    //     } catch (JsonProcessingException e) {
    //     }
    //     List<DtoDbDictDef>  dictDefList = dictDefMapper.findAllById(defIdList);
    //     Assert.notEmpty(dictDefList, "dictDefList is empty");
    // }

    // @Test
    // public void articleMapper() {
    //     DtoDbArticle article = new DtoDbArticle("test","testContent");
    //     Long articleId = articleMapper.save(article);
    //     Assert.isTrue(articleId > 0);
    //     List<DtoDbArticle>  articleList = articleMapper.findAll(20,0);
    //     Assert.notEmpty(articleList, "article empty");
    //     List<DtoDbArticle> articleByTitleList =  articleMapper.findByTitle("test");
    //     Assert.notEmpty(articleByTitleList, "article by title list empty");
    // }

    // @Test
    // public void dictWordMapper() {
    //     DtoDbDictWord wordBySpell = dictWordMapper.findBySpell("folk");
    //     Assert.notNull(wordBySpell, "wordBySpell is null");
    //     DtoDbDictWord wordById =  dictWordMapper.findById(wordBySpell.getId());
    //     Assert.notNull(wordById, "wordById is null");
    //     Long count = dictWordMapper.count();
    //     Assert.isTrue(count > 0);
    // }

    // @Test
    // public void setUserWordMapper() {
    //     int count = userWordMapper.count(18);
    //     Assert.isTrue(count > 0);
    // }


}
