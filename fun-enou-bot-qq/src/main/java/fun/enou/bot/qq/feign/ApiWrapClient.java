package fun.enou.bot.qq.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fun.enou.feign.generated.auto_client.ApiClient;
import fun.enou.feign.generated.auto_client.DtoWebWord;

@FeignClient(value="SERVICE-ALPHA",path="/api", contextId = "api")
public interface ApiWrapClient extends ApiClient {
}
