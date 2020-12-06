package fun.enou.bot.qq.feign;

import org.springframework.cloud.openfeign.FeignClient;

import fun.enou.feign.generated.auto_client.AlphaClient;

@FeignClient(value="SERVICE-ALPHA", contextId = "feign")
public interface AlphaFeignClient extends AlphaClient {
}
