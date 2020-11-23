package fun.enou.bot.qq.feign;

import org.springframework.cloud.openfeign.FeignClient;

import fun.enou.feign.generated.auto_client.WordClient;


@FeignClient(value = "SERVICE-ALPHA", path="/word", contextId = "word") // todo 
public interface WordWrapClient extends WordClient {

}
