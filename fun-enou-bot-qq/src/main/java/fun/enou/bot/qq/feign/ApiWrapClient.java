package fun.enou.bot.qq.feign;

import org.springframework.cloud.openfeign.FeignClient;

import fun.enou.feign.generated.auto_client.ApiClient;

@FeignClient(value="SERVICE-ALPHA",path="/api", contextId = "api")
public interface ApiWrapClient extends ApiClient {
}
